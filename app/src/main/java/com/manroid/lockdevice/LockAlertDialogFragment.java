package com.manroid.lockdevice;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;

public class LockAlertDialogFragment extends DialogFragment {

    private OnClickDialogListener onClickDialogListener;

    public interface OnClickDialogListener {
        void onFinishDialog();
    }

    public LockAlertDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static LockAlertDialogFragment newInstance(OnClickDialogListener onClickDialogListener) {
        LockAlertDialogFragment frag = new LockAlertDialogFragment();
        frag.onClickDialogListener = onClickDialogListener;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_setting, container);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.dimAmount = 0;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER_VERTICAL);
        getDialog().getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnOK = view.findViewById(R.id.btn_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDialogListener.onFinishDialog();
            }
        });
    }
}