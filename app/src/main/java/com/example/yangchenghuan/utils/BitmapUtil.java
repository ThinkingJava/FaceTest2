package com.example.yangchenghuan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 杨城欢 on 2016/9/10.
 */
public class BitmapUtil {
    public final static String TAG="BitmapUtil";
    /** 保存方法 */
    public static void saveBitmap(Context context, Bitmap bm, String picName) {
        Log.e(TAG, "保存图片");
        File f = new File( context.getCacheDir()+"/facename/", picName);
        if (!f.exists()) {
            f.mkdir();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public static void saveBitmapToFile(Bitmap bitmap, String path)
           {
        BufferedOutputStream os = null;
//        String path = context.getCacheDir()+"/facename/"+picname;
        try {
            File file = new File(path);
            // String _filePath_file.replace(File.separatorChar +
            // file.getName(), "");
            int end = path.lastIndexOf(File.separator);
            String _filePath = path.substring(0, end);
            File filePath = new File(_filePath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            file.createNewFile();
            os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }
    }

//    public String getSDPath(){
//        File sdDir = null;
//        boolean sdCardExist = Environment.getExternalStorageState()
//                .equals(Android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
//        if (sdCardExist) {
//            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
//        }
//        return sdDir.toString();
//    }
}
