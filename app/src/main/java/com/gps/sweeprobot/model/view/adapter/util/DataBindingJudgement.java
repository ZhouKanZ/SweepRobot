package com.gps.sweeprobot.model.view.adapter.util;

/**
 * Create by WangJun on 2017/7/13
 */

public class DataBindingJudgement {

    public static final boolean SUPPORT_DATABINDING;

    static {
        boolean hasDependency;
        try {
            Class.forName("android.databinding.ObservableList");
            hasDependency = true;
        } catch (ClassNotFoundException e) {
            hasDependency = false;
        }

        SUPPORT_DATABINDING = hasDependency;
    }
}
