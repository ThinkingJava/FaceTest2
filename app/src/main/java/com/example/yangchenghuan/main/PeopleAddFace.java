package com.example.yangchenghuan.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yangchenghuan.db.DatabaseHandler;
import com.example.yangchenghuan.entity.Grade;
import com.example.yangchenghuan.utils.CustomUtil;
import com.example.yangchenghuan.utils.Logs;
import com.example.yangchenghuan.utils.SnackBarUtils;
import com.example.yangchenghuan.utils.StringUtils;
import com.example.yangchenghuan.utils.ToastUtils;
import com.techshino.eyekeysdk.api.CheckAPI;
import com.techshino.eyekeysdk.conn.Constant;
import com.techshino.eyekeysdk.entity.CrowdCreate;
import com.techshino.eyekeysdk.entity.PeopleDelete;
import com.techshino.eyekeysdk.entity.PeopleGet;
import android.support.design.widget.CoordinatorLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleAddFace extends BaseAppcompatActivity {

    private static final String TAG = PeopleAddFace.class.getSimpleName();

    private static final String OFFCIAL_WEB_URL = "http://www.eyekey.com";

    private static final int ACTION_VERIFY = 1;
    private static final int ACTION_REGISTER = 2;

    private int mAction;
    private boolean isCreateCrowd=false;

    private int mCameraId = 1;
    private String crowdId="";
    private String mName;
    private String mClass;
    private Spinner spinner;

    DatabaseHandler db;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.toolbarTitle)
    TextView mTitleText;

    @Bind(R.id.container)
    CoordinatorLayout mContainer;

    @Bind(R.id.nameEdit)
    EditText mNameEdit;

//    @Bind(R.id.classEdit)
//    EditText mClassEdit;

    ProgressDialog mProgressDialog;
    private ArrayAdapter<String> arr_adapter;
    @Override
    public void initData() {
        mProgressDialog = new ProgressDialog(this);
        mTitleText.setText(R.string.app_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        db=new DatabaseHandler(PeopleAddFace.this);
        spinner = (Spinner) findViewById(R.id.classEdit);
        //适配器
        final List<String> data=getALLGrade();
        arr_adapter= new ArrayAdapter<String>(PeopleAddFace.this, android.R.layout.simple_spinner_item, data);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arr_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mClass=data.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mClass=data.get(position);
//            }
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_peopleaddface;
    }

    @OnTextChanged(callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED, value = R.id.nameEdit)
    void etNameEdit(Editable editable) {
        mName = editable.toString();
    }

//    @OnTextChanged(callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED, value = R.id.classEdit)
//    void etclassEdit(Editable editable) {
//        new  AlertDialog.Builder(PeopleAddFace.this)
//                .setTitle("请选择" )
//                .setIcon(android.R.drawable.ic_dialog_info)
//                .setSingleChoiceItems(getALLGrade(),  0 ,
//                        new  DialogInterface.OnClickListener() {
//                            public   void  onClick(DialogInterface dialog,  int  which) {
//                                dialog.dismiss();
//                            }
//                        }
//                )
//                .setNegativeButton("取消" ,  null )
//                .show();
//        mClass = editable.toString();
//    }


    /**
     * 验证
     */
    @OnClick(R.id.takeImg)
    void verifyIconClick() {
        mAction = ACTION_VERIFY;
        peopleGet();
    }

    /**
     * 注册
     */
    @OnClick(R.id.registerIcon)
    void registerIconClick() {
        mAction = ACTION_REGISTER;
        peopleGet();
    }

    /**
     * 重置
     */
    @OnClick(R.id.resetIcon)
    void resetIconClick() {
        if (StringUtils.isBlank(mName)) {
            ToastUtils.show(this, R.string.text_name_not_empty);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(CustomUtil.getString(this, R.string.text_delete_confirm))
                .setPositiveButton(R.string.text_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        peopleDelete();
                    }
                })
                .setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * 官网
     */
    @OnClick(R.id.addIcon)
    void addGradeClick() {
//        Uri uri = Uri.parse(OFFCIAL_WEB_URL);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
         final EditText editText=new EditText(this);

        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                createCrowd(editText.getText().toString());
                if (isCreateCrowd) {
                Grade grade=new Grade();
                grade.setName( editText.getText().toString());
                grade.setCrowdid(crowdId);
                db.addGrade(grade);
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();


    }

    /**
     * 切换摄像头
     */
    @OnClick(R.id.changeCamera)
    void changeCameraClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (mCameraId == 0) {
            builder.setMessage(R.string.text_is_change_front_preview);
        } else {
            builder.setMessage(R.string.text_is_change_back_preview);
        }
        builder
                .setPositiveButton(R.string.text_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeCamera();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void peopleDelete() {
        mProgressDialog.setMessage(CustomUtil.getString(PeopleAddFace.this, R.string.text_deleting));
        mProgressDialog.show();
        Call<PeopleDelete> call = CheckAPI.peopleDelete(mName);
        call.enqueue(new Callback<PeopleDelete>() {

            public void onFinish() {
                mProgressDialog.dismiss();
            }

            @Override
            public void onResponse(Call<PeopleDelete> call, Response<PeopleDelete> response) {
                onFinish();
                handleDelete(response.body());
            }

            @Override
            public void onFailure(Call<PeopleDelete> call, Throwable t) {
                onFinish();
                ToastUtils.show(PeopleAddFace.this, R.string.toast_network_error);
            }
        });
    }

    private void peopleGet() {
        if (StringUtils.isBlank(mName)) {
            ToastUtils.show(this, R.string.text_name_not_empty);
            return;
        }

        mProgressDialog.setMessage(CustomUtil.getString(PeopleAddFace.this, R.string.text_test));
        mProgressDialog.show();
        Call<PeopleGet> call = CheckAPI.peopleGet(mName);
        call.enqueue(new Callback<PeopleGet>() {

            public void onFinish() {
                mProgressDialog.dismiss();
            }

            @Override
            public void onResponse(Call<PeopleGet> call, Response<PeopleGet> response) {
                onFinish();
                handleCheckData(response.body());
            }

            @Override
            public void onFailure(Call<PeopleGet> call, Throwable t) {
                onFinish();
                ToastUtils.show(PeopleAddFace.this, R.string.toast_network_error);
            }
        });
    }

    private void handleCheckData(PeopleGet data) {
        Logs.d(TAG, data.getCrowd_count());

        if (mAction == ACTION_VERIFY) {
            handleVerify(data);
        } else {
            handleRegister(data);
        }
    }

    private void handleRegister(PeopleGet data) {
        if (data == null) {
            SnackBarUtils.showError(mTitleText, R.string.toast_enroll_failed);
            return;
        }
        if (data.getRes_code() != null && Constant.RES_CODE_1025.equals(data.getRes_code())) {
            startCaptureActivity(false);
        } else if (StringUtils.isEquals(data.getRes_code(), Constant.RES_CODE_0000) && data.getFace_count() > 0) {
            SnackBarUtils.show(mTitleText, R.string.text_user_exist);
        } else if (StringUtils.isEquals(data.getRes_code(), Constant.RES_CODE_0000) && data.getFace_count() == 0) {
            startCaptureActivity(true);
        } else {
            SnackBarUtils.showError(mTitleText, R.string.toast_enroll_failed);
        }
    }

    private void handleVerify(PeopleGet data) {
        if (data == null) {
            SnackBarUtils.showError(mTitleText, R.string.toast_verify_failed);
            return;
        }
        if (data.getRes_code() != null && Constant.RES_CODE_1025.equals(data.getRes_code())) {
            SnackBarUtils.show(mTitleText, R.string.text_user_unregister);
        } else if (StringUtils.isEquals(data.getRes_code(), Constant.RES_CODE_0000) && data.getFace_count() > 0) {
            Intent intent = new Intent(this, VerifyActivity.class);
            intent.putExtra(VerifyActivity.ARG_CAMERA_ID, mCameraId);
            intent.putExtra(VerifyActivity.ARG_NAME, mName);
            intent.putExtra(VerifyActivity.ARG_CLASS,mClass);
            startActivity(intent);
        } else if (StringUtils.isEquals(data.getRes_code(), Constant.RES_CODE_0000) && data.getFace_count() == 0) {
            SnackBarUtils.show(mTitleText, R.string.text_user_unregister);
        } else {
            SnackBarUtils.showError(mTitleText, R.string.toast_verify_failed);
        }
    }

    private void startCaptureActivity(boolean created) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(RegisterActivity.ARG_CAMERA_ID, mCameraId);
        intent.putExtra(RegisterActivity.ARG_NAME, mName);
        intent.putExtra(RegisterActivity.ARG_CLASS,mClass);
        intent.putExtra(RegisterActivity.ARG_IS_CREATED, created);
        startActivity(intent);
    }

    private void handleDelete(PeopleDelete data) {
        if (data == null) {
            SnackBarUtils.showError(mContainer, R.string.toast_delete_failed);
            return;
        }
        if (data.getSuccess()) {
            SnackBarUtils.show(mContainer, R.string.toast_delete_success);
        } else {
            if (StringUtils.isEquals(data.getRes_code(), Constant.RES_CODE_1025)) {
                SnackBarUtils.showError(mContainer, R.string.toast_delete_failed_no_user);
            } else {
                SnackBarUtils.showError(mContainer, R.string.toast_delete_failed);
            }
        }
    }

    private void changeCamera() {
        if (mCameraId == 0) {
            mCameraId = 1;
        } else {
            mCameraId = 0;
        }
    }

    private void createCrowd(String crowdName){
        Call<CrowdCreate> call=CheckAPI.crowdCreate(crowdName,null,null);
        call.enqueue(new Callback<CrowdCreate>() {
            @Override
            public void onResponse(Call<CrowdCreate> call, Response<CrowdCreate> response) {
                handleCrowdCreate(response.body());
            }

            @Override
            public void onFailure(Call<CrowdCreate> call, Throwable t) {
                SnackBarUtils.show(mContainer, R.string.toast_network_error);
            }
        });
    }

    private void handleCrowdCreate(CrowdCreate data){
        if(StringUtils.isEquals(data.getRes_code(),Constant.RES_CODE_0000)){
            SnackBarUtils.show(mContainer, R.string.toast_crowd_success);
            crowdId= data.getCrowd_id();
            isCreateCrowd=true;
        }else if(StringUtils.isEquals(data.getRes_code(),Constant.RES_CODE_1037)){
            SnackBarUtils.show(mContainer,data.getMessage());
            isCreateCrowd=false;
        }else{
            isCreateCrowd=false;
            SnackBarUtils.show(mContainer,data.getMessage());
        }
    }

//    private void addGrade(Grade grade){
//        db.addGrade(grade);
//    }
    private List<String> getALLGrade(){
   //   List<Grade> gradeList = db.getALllGrade();
        List<String> item=new ArrayList<>();
        if(0==0){
           item.add("mobile1");
        }else{
//            for(Grade grade:gradeList){
//                item.add(grade.getName());
//            }
        }
        return item;
    }
}

