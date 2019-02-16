package com.manroid.lockdevice;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
        implements LockAlertDialogFragment.OnClickDialogListener {

    private static final String MY_PREFS = "LOCK_DEVICE";
    private static final String PREFS_ACTIVE = "isActive";
    private DevicePolicyManager dpm;
    private ComponentName component;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        component = new ComponentName(this, LockReceiver.class);
        if (dpm.isAdminActive(component)) {
            dpm.lockNow();
        } else {
            openAdmin();
        }

        isActive();
    }

    public void openAdmin() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, component);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Only after opening administrator can be used");
        startActivity(intent);
    }

    private void showAlertDialog() {
        FragmentManager fm = getSupportFragmentManager();
        LockAlertDialogFragment alertDialog = LockAlertDialogFragment.newInstance(this);
        alertDialog.show(fm, "fragment_alert");
    }

    private void isActive() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        boolean isActive = prefs.getBoolean(PREFS_ACTIVE, false);
        if (isActive) {
            finish();
        }
        else {
            showAlertDialog();
        }
    }

    @Override
    public void onFinishDialog() {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();
        editor.putBoolean(PREFS_ACTIVE, true);
        editor.apply();
        finish();
    }
}
