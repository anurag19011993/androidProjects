package com.example.language;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;

import androidx.fragment.app.DialogFragment;

import java.security.PublicKey;

public class MySpinnerDialog extends DialogFragment {
    Activity activity;
    AlertDialog dialog;

    MySpinnerDialog(Activity myactivity) {
        activity = myactivity;
    }

    void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    void dismissDailog() {
        dialog.dismiss();
    }
}
