package com.gps.sweeprobot.utils;

import android.widget.Toast;

import com.gps.sweeprobot.MainApplication;


/**
 * Created by gaosheng on 2016/12/1.
 * 23:34
 * com.example.gaosheng.myapplication.utils
 */

public class ToastUtil {

    public static Toast toast;

    public static void setToast(String str) {

        if (toast == null) {
            toast = Toast.makeText(MainApplication.getContext(), str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }
}
