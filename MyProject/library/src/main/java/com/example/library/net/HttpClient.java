package com.example.library.net;


import android.os.Environment;
import android.text.TextUtils;

import com.example.library.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xuzhiqiang on 2017/9/26.
 */

public class HttpClient {

    private static final String TAG = "HttpClient";
    private static final long DEFAULT_WRITE_TIMEOUT = 60;
    private static final long DEFAULT_READ_TIMEOUT = 60;
    private static final long DEFAULT_CONN_TIMEOUT = 30;
    private static HttpClient mHttpClient;
    private static byte[] lock = new byte[0];
    private ConcurrentHashMap<String, List<Call>> mCalls;//网络请求记录call，用于取消
    private String mCookie;
    private boolean download;
    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder().
            writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS).
            readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS).
            connectTimeout(DEFAULT_CONN_TIMEOUT, TimeUnit.SECONDS)
            .build();

    private HttpClient() {
        mCalls = new ConcurrentHashMap<>();
    }

    public static HttpClient getInstance() {
        if (mHttpClient == null) {
            synchronized (lock) {
                if (mHttpClient == null) {
                    mHttpClient = new HttpClient();
                }
            }
        }
        return mHttpClient;
    }

    public void reuqest(RequestParams params,ResponseCallback callback){
        if (params.getMethod().equals("POST")){
            post(params,callback);
        }
    }

    public void setCookie(String cookie) {
        this.mCookie = cookie;
    }

    //post请求
    public void post(RequestParams params, ResponseCallback callBack){
        String tag = params.getUrlPath();
        String url = params.getUrl();
        LogUtil.i(TAG,"complete request url is : "+url);
        if (params.hasData()) {
            callBack.onFailure(HttpUtil.ERROR_REQUEST_NO_BODY, "请求参数为空");
            return;
        }
        //创建请求参数
        RequestBody requestBody = params.getFormBody();
        if (requestBody == null){
            callBack.onFailure(HttpUtil.ERROR_REQUEST_NO_BODY,"请求参数为空");
        }
        //创建请求
        Request req = buildPostRequest(url, requestBody, tag);
        //异步加载
        executeAsync(req, callBack, params);
    }

    //下载文件
    public void downloadFileAsync(String url, final String saveFilePath, final String fileName, final ResponseCallback callback) {
        download = true;
        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(DEFAULT_CONN_TIMEOUT, TimeUnit.SECONDS)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(HttpUtil.ERROR_DOWNLOAD, "下载失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = isExistDir(saveFilePath);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath, fileName);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1 && download) {
                        fos.write(buf, 0, len);
                        sum += len;
                        // 下载中
                        callback.onFileDownload(total, sum);
                    }
                    fos.flush();
                    callback.onSuccess(file.getCanonicalPath());
                } catch (Exception e) {
                    callback.onFailure(HttpUtil.ERROR_DOWNLOAD, "下载失败");
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    //取消下载
    public void cancelDownload() {
        download = false;
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    private void addCall(Call call, ResponseCallback callBack) {
        String key = callBack.getInvoker();
        List<Call> calls = mCalls.get(key);
        if (calls == null){
            calls = new ArrayList<>();
            mCalls.put(key, calls);
        }
        calls.add(call);
    }

    public void removeCall(String key) {
        mCalls.remove(key);
    }

    public void cancelRequestByInvoker(String name) {
        LogUtil.i("cancel task for:" ,name);
        if (name == null) {
            return;
        }
        List<Call> calls = mCalls.get(name);
        if (mCalls == null || mCalls.size() == 0){
            return;
        }
        for (int i = 0; i < calls.size(); i++) {
            Call call = calls.get(i);
            call.cancel();
        }
    }

    /**
     * 异步请求
     *
     * @param request
     */
    public void executeAsync(Request request, ResponseCallback callback, RequestParams params) {

        Call call = mOkHttpClient.newCall(request);
        addCall(call, callback);
        StringCallbackImpl stringCallback = new StringCallbackImpl(callback, params);
        if (request == null) {
            if (callback != null) {
                stringCallback.onFailure(null, new IOException("请求内容为空"));
            }
        }
        call.enqueue(stringCallback);
    }

    private Request buildPostRequest(String url, RequestBody requestBody, String tag) {
        Request.Builder builder = new Request.Builder()
                .post(requestBody)
                .url(url)
                .tag(tag);
        builder.addHeader("Accept-Encoding", "gzip").
                addHeader("Accept-Charset", "utf-8").
                addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").
                addHeader("Connection", "Keep-Alive").
                addHeader("User-Agent", "Sunny/1.0");
        if (!TextUtils.isEmpty(mCookie)) {
            builder.addHeader("Cookie", mCookie);
        }
        if (!TextUtils.isEmpty(mCookie)) {
            builder.addHeader("Cookie", mCookie);
        }
        return builder.build();
    }
}
