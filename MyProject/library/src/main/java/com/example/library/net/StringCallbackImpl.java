package com.example.library.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.library.util.LogUtil;
import com.example.library.util.Utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by xuzhiqiang on 2017/9/26.
 */

public class StringCallbackImpl implements Callback {

    public static final int RESPONSE_SUCCESS = 1;
    public static final int RESPONSE_FAILURE = 2;

    private ResponseCallback mCallback;
    private String tag;
    private RequestParams mParams;

    public StringCallbackImpl(ResponseCallback callback, RequestParams params) {
        this.mCallback = callback;
        this.mParams = params;
        this.tag = params.getUrlPath();
    }

    @Override
    public void onFailure(Call call, IOException e) {
        HttpClient.getInstance().removeCall(mCallback.getInvoker());
        LogUtil.e(tag,Utils.convertThrowToString(e));
        tellTaskFailed(HttpUtil.ERROR_REQUEST_FAILURE, "请求失败");
    }

    @Override
    public void onResponse(Call call, Response response) {
        HttpClient.getInstance().removeCall(mCallback.getInvoker());
        LogUtil.i(tag, "response : " + response);
        try {
            if (response.isSuccessful()) {
                String cookie = response.header("Set-Cookie");
                if (!TextUtils.isEmpty(cookie)) {
                    HttpClient.getInstance().setCookie(cookie);
                }
                String result = response.body().string();//string()只能调用一次
                LogUtil.i(tag, "responseBodyString : " + result);
                tellTaskSuccess(result);
            } else {
                tellTaskFailed(response.code(), "请求失败!");
            }
        } catch (Exception e) {
            tellTaskFailed(HttpUtil.ERROR_RESPONSE_EXCEPTION, "响应异常");
        } finally {
            response.body().close();
        }
    }

    private void tellTaskFailed(final int code, final String info) {
        LogUtil.e(tag, info);
        Utils.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFailure(code, info);
            }
        });
    }

    private void tellTaskSuccess(final String result) {
        if (TextUtils.isEmpty(result)) {
            tellTaskFailed(HttpUtil.ERROR_RESPONSE_NOT_CONTENT, "响应内容为空");
            return;
        }

        Utils.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Object resultObj = JSON.parseObject(result, mCallback.getType());
                    mCallback.onSuccess(resultObj);
                } catch (Exception e) {
                    LogUtil.e(tag, Utils.convertThrowToString(e));
                    tellTaskFailed(HttpUtil.ERROR_RESPONSE_HANDLE, "响应处理失败");
                }
            }
        });

    }
}
