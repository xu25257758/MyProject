package com.example.library.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.library.MyApplication;
import com.example.library.R;
import com.example.library.iview.IBaseView;
import com.example.library.util.DialogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xuzhiqiang on 2017/10/18.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    private Unbinder unbinder;
    private OnPermissionCallback mCallback;
    private int requestCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
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

    /**
     * 申请权限
     * @param permission
     * @param requestCode
     * @param mCallback
     * @return
     */
    public boolean checkSelfPermission(String permission,int requestCode,OnPermissionCallback mCallback){
        this.requestCode = requestCode;
        this.mCallback = mCallback;
        if(ContextCompat.checkSelfPermission(this,permission)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{permission},requestCode);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (this.requestCode == requestCode && mCallback != null){
            mCallback.callback(requestCode);
            this.requestCode = -1;
            mCallback = null;
        }
    }

    public interface OnPermissionCallback{
        void callback(int requestCode);
    }

    public void startActivity(Class target) {
        Intent intent = new Intent(this, target);
        startActivity(intent);
    }
}
