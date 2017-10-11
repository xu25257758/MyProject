package com.example.library.util;

import android.util.Log;

/**
 * Created by xuzhiqiang on 2017/10/11.
 */

public class LogUtil {
    private static boolean debug = true;

    public static void setDebug(boolean debug1){
        debug = debug1;
    }

    public static void e(String tag, String msg){
        if (debug){
            Log.e(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if (debug){
            Log.i(tag, msg);
        }
    }
}
