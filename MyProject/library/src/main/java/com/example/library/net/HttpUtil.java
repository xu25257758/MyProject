package com.example.library.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by xuzhiqiang on 2017/10/11.
 */

public class HttpUtil {
    public static final int ERROR_NO_INTERNET = 1;//无网络
    public static final int ERROR_REQUEST_FAILURE = 2;//请求失败
    public static final int ERROR_REQUEST_NO_BODY = 3;//请求参数为空
    public static final int ERROR_RESPONSE_EXCEPTION = 4;//响应异常
    public static final int ERROR_RESPONSE_NOT_CONTENT = 5;//响应内容为空
    public static final int ERROR_RESPONSE_HANDLE = 6;//响应处理失败
    public static final int ERROR_DOWNLOAD = 7;//下载失败

    private static ConnectivityManager mConnectivityManager;

    public static ConnectivityManager getConnectivityManager(Context context) {
        if (mConnectivityManager == null) {
            mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        return mConnectivityManager;
    }

    public static boolean isConnected(Context context) {
        final NetworkInfo networkinfo = getConnectivityManager(context).getActiveNetworkInfo();
        if (networkinfo != null) {
            return networkinfo.isConnected();
        }
        return false;
    }

    public static byte[] gzip(String data) {
        ByteArrayOutputStream arr = null;
        try {
            arr = new ByteArrayOutputStream();
            OutputStream zipper = new GZIPOutputStream(arr);
            zipper.write(data.getBytes("UTF-8"));
            zipper.close();
            return arr.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                arr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
