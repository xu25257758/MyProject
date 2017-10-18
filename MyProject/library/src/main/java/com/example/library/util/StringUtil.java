package com.example.library.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by xuzhiqiang on 2017/10/18.
 */

public class StringUtil {
    public static boolean isEmpty(String text){
        if (text == null || text.length() == 0 || text.equalsIgnoreCase("null")){
            return true;
        }
        return false;
    }

    //将Exception中内容转换为String
    public static String convertThrowToString(Throwable ex){
        StringWriter wr = new StringWriter();
        PrintWriter err = new PrintWriter(wr);
        ex.printStackTrace(err);
        return ex.toString();
    }
}
