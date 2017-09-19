package com.gps.sweeprobot.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.gps.sweeprobot.R;

/**
 * Create by WangJun on 2017/7/24
 */

public class DialogUtil {

    public static void createAlertDialog(Context context,String msg){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton(context.getString(R.string.dialog_sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(context.getString(R.string.dialog_input_wifi_btn_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
}
