package com.example.library;

import android.app.Activity;
import android.app.Application;

import com.example.library.net.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuzhiqiang on 2017/10/11.
 */

public class MyApplication extends Application {

    private static MyApplication instance;
    private List<Activity> activities;

    @Override
    public void onCreate() {
        super.onCreate();
        initAttr();
        init();
    }

    private void initAttr() {
        setTheme(R.style.AppTheme);
    }

    private void init() {
        RequestParams.HOST = "http://192.168.1.190:8080/";
        instance = this;
        activities = new ArrayList<>();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public Activity getTopActivity() {
        return activities.size() > 0 ? activities.get(activities.size() - 1) : null;
    }

    public void finish() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

}
