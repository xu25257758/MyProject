package com.example.library.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by xuzhiqiang on 2017/10/11.
 */

public class Utils {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void post(Runnable task){
        mHandler.post(task);
    }
}
