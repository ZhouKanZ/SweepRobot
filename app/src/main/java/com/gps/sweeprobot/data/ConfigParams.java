package com.gps.sweeprobot.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.gps.sweeprobot.MainApplication;

import java.util.HashMap;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/31 0031
 * @Descriptiong : sharedPreferens 存储基本数据
 */
public class ConfigParams {

    /* 表——table_config */
    private static final String  TABLE_CONFIG = "config";
    private static final String IS_FRIST_INSTALL = "is_frist_install";

    /* 根据表名存储的SharedPerferences */
    private static HashMap<String,SharedPreferences> preferencesMap;

    static {
        preferencesMap = new HashMap<>();
        putSharedPreferences(TABLE_CONFIG);
    }

    /**
     *  获取数据
     * @param ctx
     * @return
     */
    public static boolean isFristInstall(Context ctx){
        return preferencesMap.get(TABLE_CONFIG).getBoolean(IS_FRIST_INSTALL, true);
    }

    /**
     *  设置
     * @param isFrist
     * @return
     */
    public static boolean setIsFristInstall(boolean isFrist) {
        SharedPreferences.Editor editor =getEdit(TABLE_CONFIG);
        editor.putBoolean(IS_FRIST_INSTALL,isFrist);
        return editor.commit();
    }

    /**
     *  添加
     * @param key
     */
    private static void putSharedPreferences(String key){
        SharedPreferences value = MainApplication.getContext().getSharedPreferences(key
                ,MainApplication.getContext().MODE_PRIVATE);
        preferencesMap.put(key,value);
    }

    private static SharedPreferences.Editor getEdit(String key){
        return preferencesMap.get(key).edit();
    }

}
