package com.example.myproject.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.library.ui.activity.BaseActivity;
import com.example.myproject.R;

import butterknife.BindView;

public class JniActivity extends BaseActivity {
    @BindView(R.id.jni_show)
    TextView show;

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_jni;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        show.setText(stringFromJNI());
    }

    public native String stringFromJNI();

}
