package com.example.library.net;


import com.example.library.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by xuzhiqiang on 2017/10/11.
 */

public class RequestParams {
    public static String HOST;
    private String mUrl;//请求的url
    private Map<String, String> params;//请求参数
    private Map<String, String> uploadPaths;//上传的多文件路径
    private String urlPath;
    private String method;//请求方法
    private String downloadPath;//下载路径

    public RequestParams(String urlPath) {
        this.urlPath = urlPath;
        method = "POST";//默认为post请求
        params = new HashMap<>();
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    //添加请求属性
    public void addParam(String key, String value) {
        try {
            if (!StringUtil.isEmpty(key) && !StringUtil.isEmpty(value)) {
                params.put(key, URLEncoder.encode(value, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void addUploadPath(String key,String filePath){
        if (uploadPaths == null){
            uploadPaths = new HashMap<>();
        }
        if (!StringUtil.isEmpty(key) && !StringUtil.isEmpty(filePath)){
            uploadPaths.put(key,filePath);
        }
    }

    public String getUrl() {
        if (StringUtil.isEmpty(mUrl)) {
            buildUrl();
        }
        return mUrl;
    }

    private void buildUrl() {
        if (method.equals("GET")) {
            mUrl = HOST + urlPath + "?" + buildParams();
        } else if (method.equals("POST")) {
            mUrl = HOST + urlPath;
        } else {
            mUrl = HOST + urlPath;
        }
    }

    public boolean hasData(){
        return params == null || params.isEmpty();
    }

    private String buildParams() {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry :
                params.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    //使用form表单提交数据
    public RequestBody getFormBody(){
        if (params == null || params.isEmpty()) {
            return null;
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry :
                params.entrySet()) {
            builder.add(entry.getKey(),entry.getValue());
        }
        return builder.build();
    }

    //使用json提交数据
    public RequestBody getJsonBody(){
        if (params == null || params.isEmpty()) {
            return null;
        }
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String> entry :
                params.entrySet()) {
            try {
                jsonObject.put(entry.getKey(),entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return RequestBody.create(mediaType,jsonObject.toString());
    }

    public byte[] getParamData(){
        String param = buildParams();
        if (StringUtil.isEmpty(param)){
            return null;
        }else {
            return param.getBytes();
        }
    }

    public String getUrlPath() {
        return urlPath;
    }
}
