package com.example.myproject.presenter;

import android.content.Context;
import android.util.Log;

import com.example.library.net.HttpClient;
import com.example.library.net.RequestParams;
import com.example.library.net.ResponseCallback;
import com.example.myproject.iview.IMainView;

/**
 * Created by xuzhiqiang on 2017/10/18.
 */

public class MainPresenter {

    private IMainView mView;
    public MainPresenter(IMainView mView){
        this.mView = mView;
    }

    public void testOkhttp(){
        RequestParams params = new RequestParams("SecondWebProject/firstServlet");
        params.addParam("usename","usename");
        params.addParam("password","password");
        for (int i = 0; i < 1; i++) {
            HttpClient.getInstance().reuqest(params, new ResponseCallback<String>() {
                @Override
                public void onSuccess(String response) {
                    Log.e("onSuccess",response);
                }

                @Override
                public void onFailure(int code, String msg) {
                    Log.e("onFailure",msg);
                }

                @Override
                public String getInvoker() {
                    return mView.getClass().getSimpleName();
                }
            });
        }
    }

    public void title(Context context){
        mView.showLoadingDialog();
    }
}
