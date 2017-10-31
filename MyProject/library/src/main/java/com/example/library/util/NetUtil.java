package com.example.library.util;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;

/**
 * Created by xuzhiqiang on 2017/10/30.
 */

public class NetUtil {
    public static String userAgent;
    //获得系统的User-Agent
    public static String getUserAgent(Context context) {
        if (userAgent != null){
            return userAgent;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        userAgent = sb.toString();
        return userAgent;
    }
}
