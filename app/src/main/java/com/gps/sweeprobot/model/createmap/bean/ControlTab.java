package com.gps.sweeprobot.model.createmap.bean;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/14 0014
 * @Descriptiong : xxx
 */

public class ControlTab {

    private int resId;
    private String controllName;

    public ControlTab(int resId,String controllName) {
        this.resId = resId;
        this.controllName = controllName;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public void setControllName(String controllName) {
        this.controllName = controllName;
    }

    public String getControllName() {
        return controllName;
    }
}
