package com.example.yangchenghuan.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.yangchenghuan.db.DatabaseHandler;
import com.example.yangchenghuan.utils.SnackBarUtils;
import com.example.yangchenghuan.utils.StringUtils;
import com.techshino.eyekeysdk.conn.Constant;
import com.techshino.eyekeysdk.entity.PeopleGet;

import butterknife.Bind;
import butterknife.OnClick;

public class ClassActivity extends BaseAppcompatActivity {

    private int mCameraId = 1;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.toolbarTitle)
    TextView mTitleText;
    /**
     * 验证
     */
    @OnClick(R.id.takeImg)
    void verifyIconClick() {
        peopleIdentify();
    }

    ProgressDialog mProgressDialog;


    @Override
    public void initData() {
        mProgressDialog = new ProgressDialog(this);
        mTitleText.setText(R.string.app_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_peoplesearch;
    }

    private void peopleIdentify() {
        Intent intent = new Intent(this,IdentifyActivity.class);
        intent.putExtra(VerifyActivity.ARG_CAMERA_ID, mCameraId);
        startActivity(intent);
    }

/*    private void handleIdentify(PeopleGet data) {
        if (data == null) {
            SnackBarUtils.showError(mTitleText, R.string.toast_verify_failed);
            return;
        }
        if (data.getRes_code() != null && Constant.RES_CODE_1025.equals(data.getRes_code())) {
            SnackBarUtils.show(mTitleText, R.string.text_user_unregister);
        } else if (StringUtils.isEquals(data.getRes_code(), Constant.RES_CODE_0000) && data.getFace_count() > 0) {
            Intent intent = new Intent(this, VerifyActivity.class);
            intent.putExtra(VerifyActivity.ARG_CAMERA_ID, mCameraId);
            //          intent.putExtra(VerifyActivity.ARG_NAME, mName);
            startActivity(intent);
        } else if (StringUtils.isEquals(data.getRes_code(), Constant.RES_CODE_0000) && data.getFace_count() == 0) {
            SnackBarUtils.show(mTitleText, R.string.text_user_unregister);
        } else {
            SnackBarUtils.showError(mTitleText, R.string.toast_verify_failed);
        }
    }*/


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
        builder.setPositiveButton(R.string.text_confirm, new DialogInterface.OnClickListener() {
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

    private void changeCamera() {
        if (mCameraId == 0) {
            mCameraId = 1;
        } else {
            mCameraId = 0;
        }
    }
}
