package com.example.library.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.library.MyApplication;

public class UIUtils {

    private static Handler mHandler;

    public static Context getContext() {
        return MyApplication.getInstance();
    }

    public static ContentResolver getContentResolver() {
        return getContext().getContentResolver();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isOnMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * dip转换px
     */
    public static int dip2px(float dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 资源目录下的Uri,
     *
     * @param resId R.drawable.a
     * @return
     */
    public static Uri getResourceUri(int resId) {
        return Uri.parse("android.resource://" + getContext().getPackageName() + "/" + resId);
    }

    public static Uri getResourceUri(String assets) {
        return Uri.parse("android.resource://" + getContext().getPackageName() + "/android_asset/" + assets);
    }

    public static int getScreenWidth() {
        DisplayMetrics display = getContext().getResources()
                .getDisplayMetrics();
        return display.widthPixels;
    }

    /**
     * @return 获取屏幕高度
     */
    public static int getScreenHeight() {
        DisplayMetrics display = getContext().getResources()
                .getDisplayMetrics();
        return display.heightPixels;
    }

    /**
     * @return 获取屏幕宽度
     */
    public static int getStatusBarHeight() {
        int result = 50;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static float getDensity() {
        return getResources().getDisplayMetrics().density;
    }

    public static int getDpi() {
        return getResources().getDisplayMetrics().densityDpi;
    }


    /**
     * 获取主线程的handler
     */
    public static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    /**
     * 延时在主线程执行runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 从主线程looper里面移除runnable
     */
    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    public static View inflate(int resId, ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(resId, parent, false);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字
     */
    public static String getString(int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getColorDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }


    /**
     * Returns {@code true} if called on the main thread, {@code false} otherwise.
     */
    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final int resId) {
        showToastSafe(getString(resId));
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final String str) {
        if (isOnMainThread()) {
            showToast(str);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showToast(str);
                }
            });
        }
    }

    public static void showToast(final String str) {
        showToast(str, Toast.LENGTH_SHORT);
    }

    public static void showToast(final String str, final int duration) {
        if (isOnMainThread()) {
            Toast.makeText(getContext(), str, duration).show();
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), str, duration).show();
                }
            });
        }
    }

    public static void showLongToast(String str) {
        showToast(str, Toast.LENGTH_LONG);
    }

    public static void showToast(int resId) {
        showToast(getString(resId));
    }

    /**
     * 扩大点击区域
     */
    public static void expandViewTouchDelegate(final View view, final int top,
                                               final int bottom, final int left, final int right) {
        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);
                bounds.top -= top;
                bounds.bottom += bottom;
                bounds.left -= left;
                bounds.right += right;
                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);
                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }

    /**
     * 恢复点击区域
     */
    public static void restoreViewTouchDelegate(final View view) {
        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                bounds.setEmpty();
                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);
                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }

    public static void visibility(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view.getVisibility() != View.VISIBLE) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public static void invisibility(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view.getVisibility() != View.INVISIBLE) {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public static void gone(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view.getVisibility() != View.GONE) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }
}
