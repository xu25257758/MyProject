package com.example.myproject.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.library.ui.dialog.BaseDialogFragment;
import com.example.myproject.R;

public class TestDialog extends BaseDialogFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.dialog_test;
    }

}
