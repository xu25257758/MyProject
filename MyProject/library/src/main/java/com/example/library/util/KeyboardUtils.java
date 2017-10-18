package com.example.library.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by xuzhiqiang on 2017/10/18.
 * 此类用于隐藏和显示软键盘
 */

public class KeyboardUtils {
    /**
     * 隐藏软键盘
     * @param view
     */
    public static void hideSoftKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()){
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
        }
    }

    /**
     * 显示软键盘
     * @param edit 需要输入数据的EditText
     */
    public static void showSoftKeyboard(final EditText edit){
        if (edit == null){
            return;
        }
        edit.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edit,0);
            }
        },100);
    }

}
