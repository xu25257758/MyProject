package com.example.library.net;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by xuzhiqiang on 2017/10/11.
 */

public abstract class ResponseCallback<T> {
    private Type mType;

    public ResponseCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    private Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        Type[] params = ((ParameterizedType) superclass).getActualTypeArguments();
        return params[0];
    }

    public Type getType() {
        return mType;
    }

    public abstract void onSuccess(T response);

    public abstract void onFailure(int code, String msg);

    public abstract String getInvoker();

    //下载
    public void onFileDownload(long total, long current) {
    }

    //上传
    public void onFileUpload(long total, long current) {
    }
}
