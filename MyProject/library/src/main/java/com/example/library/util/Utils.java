package com.example.library.util;

import android.os.Handler;
import android.os.Looper;

import com.example.library.MyApplication;

/**
 * Created by xuzhiqiang on 2017/10/11.
 */

public class Utils {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void post(Runnable task){
        mHandler.post(task);
    }

    public static String getString(int stringId){
        return MyApplication.getInstance().getResources().getString(stringId);
    }

    public static String getColor(int colorId){
        return MyApplication.getInstance().getResources().getString(colorId);
    }

    public static String getInteger(int integerId){
        return MyApplication.getInstance().getResources().getString(integerId);
    }
}
