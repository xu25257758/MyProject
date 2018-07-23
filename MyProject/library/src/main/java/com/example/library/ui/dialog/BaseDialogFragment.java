package com.example.library.ui.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.example.library.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseDialogFragment extends DialogFragment{

    private boolean transparentBg;//是否设置透明背景
    private boolean matchLayout;//是否铺满整个屏幕
    private Unbinder bind;
    private boolean animation = true;//弹框动画

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(),container,false);
    }

    protected abstract int getLayoutId();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        if (animation){
            windowParams.windowAnimations = R.style.dialogAnim;
        }
        if (transparentBg){
            windowParams.dimAmount = 0.0f;
        }
        if (matchLayout){
            windowParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            windowParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        window.setAttributes(windowParams);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    /**
     * 是否设置透明背景
     * @param transparentBg
     */
    public void setTransparentBg(boolean transparentBg) {
        this.transparentBg = transparentBg;
    }

    /**
     * 弹窗布局是否铺满整个屏幕
     * @param matchLayout
     */
    public void setMatchLayout(boolean matchLayout) {
        this.matchLayout = matchLayout;
    }

    public void setAnimation(boolean animation) {
        this.animation = animation;
    }
}
