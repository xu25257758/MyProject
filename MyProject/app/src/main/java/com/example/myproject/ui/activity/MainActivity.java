package com.example.myproject.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.example.myproject.R;
import com.example.myproject.imp.IMainView;
import com.example.myproject.presenter.MainPresenter;
import com.example.myproject.util.DialogUtil;

import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IMainView {

    private MainPresenter mPresenter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenter(this);
    }

    @OnClick({R.id.main_btn, R.id.main_btn1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn:
                DialogUtil.showPermissionDialog();
                break;
            case R.id.main_btn1:
                mPresenter.title(this);
                break;
        }
    }
}
