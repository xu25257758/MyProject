package com.example.module1.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.library.ui.activity.BaseActivity;
import com.example.module1.R;

/**
 * Created by xuzhiqiang on 2017/10/23.
 */

public class ModuleStartActivity extends BaseActivity implements View.OnClickListener{

    Button module;
    @Override
    public int getContentViewId() {
        return R.layout.module1_main_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        module = (Button) findViewById(R.id.button);
        module.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button){
            Toast.makeText(this,"onclick",Toast.LENGTH_SHORT).show();
        }
    }
}
