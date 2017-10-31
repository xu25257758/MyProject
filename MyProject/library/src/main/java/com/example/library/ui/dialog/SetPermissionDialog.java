package com.example.library.ui.dialog;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.library.R;
import com.example.library.R2;
import com.example.library.util.DeviceUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xuzhiqiang on 2017/9/21.
 */

public class SetPermissionDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_set_permission, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setMinimumWidth(DeviceUtil.getWidth(getActivity()) / 4 * 3);
        ButterKnife.bind(this,view);
    }

    @OnClick(R2.id.permission_cancel)
    public void cancel(View view){
        Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
    }
}
