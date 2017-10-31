package com.example.library.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.library.R;
import com.example.library.R2;

import butterknife.OnClick;

/**
 * Created by xuzhiqiang on 2017/10/23.
 */

public class SecondActivity extends BaseActivity {


    @Override
    public int getContentViewId() {
        return R.layout.second_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R2.id.second_btn)
    public void onSecond(View view){
        boolean b = view.getId() == R2.id.second_btn;
        Toast.makeText(this,"second1,"+b,Toast.LENGTH_SHORT).show();
    }
}
