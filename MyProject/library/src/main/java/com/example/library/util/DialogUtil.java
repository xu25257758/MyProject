package com.example.library.util;

import android.app.Activity;

import com.example.library.MyApplication;
import com.example.library.ui.dialog.LoadingDialog;
import com.example.library.ui.dialog.SetPermissionDialog;

/**
 * Created by xuzhiqiang on 2017/10/18.
 */

public class DialogUtil {
    private static LoadingDialog mLoadingDialog;

    public static void showPermissionDialog() {
        SetPermissionDialog dialog = new SetPermissionDialog();
        dialog.show(MyApplication.getInstance().getTopActivity().getFragmentManager(), "permissionDialog");
    }

    /**
     * 显示正在加载弹框
     *
     * @param activity
     */
    public static void showLoadingDialog(Activity activity) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog();
        }
        mLoadingDialog.show(activity.getFragmentManager(), "LoadingDialog");
    }

    /**
     * 隐藏正在加载弹框
     */
    public static void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isVisible()) {
            mLoadingDialog.dismiss();
        }
    }
}
