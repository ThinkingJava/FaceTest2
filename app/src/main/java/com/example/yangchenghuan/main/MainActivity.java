package com.example.yangchenghuan.main;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yangchenghuan.view.DraggableGridViewPager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DraggableGridViewPager mDraggableGridViewPager;
    private ArrayAdapter<String> mAdapter;

//    @Bind(R.id.toolbar)
//    Toolbar mToolbar;

//    @Bind(R.id.toolbarTitle)
//    TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setData();

    }

//    @Override
//    public void initData() {
//   //     mTitleText.setText(R.string.app_title);
//      //  setSupportActionBar(mToolbar);
//   //     getSupportActionBar().setTitle("");
//        mDraggableGridViewPager = (DraggableGridViewPager) findViewById(R.id.draggable_grid_view_pager);
//         setData();
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_main;
//    }

    private void setData() {

        mDraggableGridViewPager = (DraggableGridViewPager) findViewById(R.id.draggable_grid_view_pager);
        String[] test_title = new String[]{this.getString(R.string.string1), this.getString(R.string.string2),
                this.getString(R.string.string3), this.getString(R.string.string4), this.getString(R.string.string5),
                this.getString(R.string.string6),
                this.getString(R.string.string7), this.getString(R.string.string8)};
        mAdapter = new ArrayAdapter<String>(this, 0) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final String text = getItem(position);
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.list_layout_mian, null);
                }
                ((TextView) convertView).setText(text);
                convertView.setBackgroundColor(Color.rgb(100 + position * 10, 50 + position * 20, 150 + position * 2));
                return convertView;
            }

        };
        for (int i = 0; i < test_title.length; i++) {
            mAdapter.add(test_title[i]);  //添加textview
        }
        mDraggableGridViewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();    //刷新一次
        mDraggableGridViewPager.setOnPageChangeListener(new DraggableGridViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.v(TAG, "onPageScrolled position=" + position + ", positionOffset=" + positionOffset
                        + ", positionOffsetPixels=" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "onPageSelected position=" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged state=" + state);
            }
        });

        mDraggableGridViewPager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView textView = (TextView) view;
                Intent intent;

                switch (textView.getText().toString()) {

                    case "添加人脸":
                        intent = new Intent(MainActivity.this, PeopleAddFace.class);
                        startActivity(intent);
                        break;
                    case "匹配人脸":
                        intent = new Intent(MainActivity.this, PeopleSearchFace.class);
                        startActivity(intent);
                        break;
                    case "班级考勤":
                        intent = new Intent(MainActivity.this, ClassActivity.class);
                        startActivity(intent);
                        break;
                    case "考勤信息":
                        intent = new Intent(MainActivity.this, AttendActivity.class);
                        startActivity(intent);
                        break;
//                    case "成绩查询":
//                        intent = new Intent(MainActivity.this, MessageActivity.class);
//                        startActivity(intent);
//
//                        break;
//                    case "奖惩情况":
//                        intent = new Intent(MainActivity.this, RewardpunishActivity.class);
//                        startActivity(intent);
//
//                        break;
//                    case "开设课程":
//                        intent = new Intent(MainActivity.this, OffercoursesActivity.class);
//                        startActivity(intent);
//
//                        break;
//                    case "晚归违规":
//                        intent = new Intent(MainActivity.this, LaterActivity.class);
//                        startActivity(intent);
//                        break;
                }

            }
        });
        mDraggableGridViewPager.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //    showToast(((TextView) view).getText().toString() + " long clicked!!!");
                return true;
            }
        });

        mDraggableGridViewPager.setOnRearrangeListener(new DraggableGridViewPager.OnRearrangeListener() {
            @Override
            public void onRearrange(int oldIndex, int newIndex) {
                Log.d(TAG, "OnRearrangeListener.onRearrange from=" + oldIndex + ", to=" + newIndex);
                String item = mAdapter.getItem(oldIndex);
                mAdapter.setNotifyOnChange(false);
                mAdapter.remove(item);
                mAdapter.insert(item, newIndex);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
