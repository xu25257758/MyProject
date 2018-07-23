package com.example.myproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.library.ui.activity.BaseActivity;
import com.example.myproject.R;

import butterknife.OnClick;

public class MainActivity extends BaseActivity{

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.main_surfaceview, R.id.main_jni,R.id.main_prdownloader})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_surfaceview:
                startActivity(SurfaceViewActivity.class);
                break;
            case R.id.main_jni:
                startActivity(JniActivity.class);
                break;
            case R.id.main_prdownloader:
                startActivity(PrDownloaderActivity.class);
                break;
        }
    }
}
