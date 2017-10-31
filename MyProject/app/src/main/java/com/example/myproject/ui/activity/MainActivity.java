package com.example.myproject.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.library.ui.activity.BaseActivity;
import com.example.library.ui.activity.SecondActivity;
import com.example.library.util.IntentUtil;
import com.example.library.util.NetUtil;
import com.example.library.util.Utils;
import com.example.myproject.R;
import com.example.myproject.iview.IMainView;
import com.example.myproject.presenter.MainPresenter;

import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IMainView {

    private MainPresenter mPresenter;
    private int a;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenter(this);
        Toast.makeText(this, Utils.getString(R.string.testString),Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.button, R.id.main_btn1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Log.e("User-Agent", NetUtil.getUserAgent(this));
                break;
            case R.id.main_btn1:
                IntentUtil.startActivity(SecondActivity.class);
                break;
        }
    }
}
