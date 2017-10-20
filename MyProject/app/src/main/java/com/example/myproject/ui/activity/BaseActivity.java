package com.example.myproject.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myproject.MyApplication;
import com.example.myproject.iview.IBaseView;
import com.example.myproject.util.DialogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xuzhiqiang on 2017/10/18.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        unbinder = ButterKnife.bind(this);
        MyApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        MyApplication.getInstance().removeActivity(this);
    }

    public abstract int getContentViewId();

    @Override
    public void showLoadingDialog() {
        DialogUtil.showLoadingDialog(this);
    }

    @Override
    public void hideLoadingDialog() {
        DialogUtil.hideLoadingDialog();
    }
}
