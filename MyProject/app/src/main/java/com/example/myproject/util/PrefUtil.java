package com.example.myproject.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myproject.MyApplication;

public class PrefUtil {

    private static final String PREF_FILE = "pref_file";
    private static SharedPreferences sp;

    public static SharedPreferences getSharedPreferences() {
        return MyApplication.getInstance().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
    }

    public static void putString(String key, String value) {
        if (sp == null){
            sp = getSharedPreferences();
        }
        sp.edit().putString(key, value).commit();
        sp = null;
    }

    public static String getString(String key, String defaultValue) {
        if (sp == null){
            sp = getSharedPreferences();
        }
        return sp.getString(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        if (sp == null){
            sp = getSharedPreferences();
        }
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defalut) {
        if (sp == null){
            sp = getSharedPreferences();
        }
        return sp.getInt(key, defalut);
    }

    public static void putBoolean(String key, boolean value) {
        if (sp == null){
            sp = getSharedPreferences();
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defalut) {
        if (sp == null){
            sp = getSharedPreferences();
        }
        return sp.getBoolean(key, defalut);
    }

    public static void putStrings(String[] key_value) {
        if (key_value.length % 2 != 0)
            throw new IllegalArgumentException("请输入key-value的参数");
        if (sp == null){
            sp = getSharedPreferences();
        }
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < key_value.length; i += 2) {
            editor.putString(key_value[i], key_value[i + 1]);
        }
        editor.commit();
    }

    public static String[] getStrings(String[] keys) {
        if (sp == null){
            sp = getSharedPreferences();
        }
        String[] values = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            values[i] = sp.getString(keys[i], "");
        }
        return values;
    }
}
