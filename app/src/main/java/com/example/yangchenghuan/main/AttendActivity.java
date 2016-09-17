package com.example.yangchenghuan.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.yangchenghuan.adapter.AttendAdapter;
import com.example.yangchenghuan.db.DatabaseHandler;
import com.example.yangchenghuan.entity.Attend;

import java.util.ArrayList;
import java.util.List;

public class AttendActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AttendAdapter mAdapter;
    private List<Attend> mAttend;
    private DatabaseHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend);

        initDatas();
        //得到控件
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview_horizontal);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new AttendAdapter(this, mAttend);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initDatas()
    {
        dbHandler=new DatabaseHandler(AttendActivity.this);
/*        mDatas = new ArrayList<Integer>(Arrays.asList(R.drawable.a,
                R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e,
                R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.l));*/
        mAttend = new ArrayList<Attend>();
        mAttend=dbHandler.getALllAttend();  //获取所以数据
        Log.d("---------------",mAttend.toString());
    }
}
