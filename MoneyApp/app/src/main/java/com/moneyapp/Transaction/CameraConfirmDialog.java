package com.moneyapp.Transaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.moneyapp.Transaction.Camera;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class CameraConfirmDialog extends DialogFragment {
    OnConfirmListener callback;

    public void setOnConfirmListener(Camera activity) {
        callback = activity;
    }

    public interface OnConfirmListener {
        void confirmOption(int del);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getArguments().getString("reg"))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        callback.confirmOption(1);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        callback.confirmOption(0);
                    }
                });
        return builder.create();
    }
}