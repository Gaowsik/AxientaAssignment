package com.assignment.android.mvvmProjectRetrofit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.gson.Gson;

public class Utills {

    public static void showInfoAlertDialogNoIcon(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static String getObjectAsString(Object object) {
        return new Gson().toJson(object);
    }

    public static Object getBundleObject(Bundle bundle, Class clz, String keyword) {
        if (bundle != null) {
            if (isNotEmptyString(bundle.getString(keyword))) {
                return new Gson().fromJson(bundle.getString(keyword), clz);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //Method to check if string is empty
    public static boolean isNotEmptyString(String value) {
        if (value != null && !value.equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }
}
