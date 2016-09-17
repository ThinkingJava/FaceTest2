package com.example.yangchenghuan.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yangchenghuan.entity.Attend;
import com.example.yangchenghuan.main.R;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by 杨城欢 on 2016/9/10.
 */
public class AttendAdapter extends
        RecyclerView.Adapter<AttendAdapter.ViewHolder>
{

    private LayoutInflater mInflater;
    private List<Attend> mAttend;

    public AttendAdapter(Context context, List<Attend> mAttend)
    {
        mInflater = LayoutInflater.from(context);
        this.mAttend = mAttend;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        ImageView mImg;
        TextView mTxt;
        TextView mClass;
        TextView mTime;
    }

    @Override
    public int getItemCount()
    {
        return mAttend.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.layout_attend_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.ivBook);
        viewHolder.mTxt = (TextView)view.findViewById(R.id.tvTitle);

        viewHolder.mTime = (TextView)view.findViewById(R.id.tvDesc);

        viewHolder.mClass = (TextView)view.findViewById(R.id.tvClass);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
//        viewHolder.mImg.setImageResource(Integer.valueOf(mDatas.get(i).get(0)));
        Bitmap bitmap=convertToBitmap(mAttend.get(i).getImagepath(),480,640);
        viewHolder.mImg.setImageBitmap(bitmap);
        viewHolder.mTxt.setText(mAttend.get(i).getName());
        viewHolder.mTime.setText(mAttend.get(i).getCreatetime());
        viewHolder.mClass.setText(mAttend.get(i).getGrade());
    }

    public Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int)scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    }
