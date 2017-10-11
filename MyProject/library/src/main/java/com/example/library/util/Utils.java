package com.example.library.util;

import android.os.Handler;
import android.os.Looper;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by xuzhiqiang on 2017/10/11.
 */

public class Utils {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void post(Runnable task){
        mHandler.post(task);
    }

    public static String convertThrowToString(Throwable ex){
        StringWriter wr = new StringWriter();
        PrintWriter err = new PrintWriter(wr);
        ex.printStackTrace(err);
        return ex.toString();
    }

    public static boolean isEmpty(String text){
        if (text == null || text.length() == 0 || text.equals("null")){
            return true;
        }
        return false;
    }
}
