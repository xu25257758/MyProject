package com.example.myproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.library.net.HttpClient;
import com.example.library.net.RequestParams;
import com.example.library.net.ResponseCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestParams.HOST = "http://192.168.1.190:8080/";
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
                    return "MainActivity";
                }
            });
        }
    }
}
