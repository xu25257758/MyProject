package com.example.library.util;

import android.app.Activity;
import android.content.Intent;

import com.example.library.MyApplication;

/**
 * Created by xuzhiqiang on 2017/10/24.
 */

public class IntentUtil {
    public static void startActivity(Class target) {
        Activity start = MyApplication.getInstance().getTopActivity();
        Intent intent = new Intent(start, target);
        start.startActivity(intent);
    }
}
