package com.gps.sweeprobot.model.main.bean;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/13 0013
 * @Descriptiong : xxx
 */

public class MainTab {

    private String tabName ;
    private String tabDesc;
    private int tabImageRes;

    public MainTab(String tabName, int tabImageRes,String tabDesc) {
        this.tabName = tabName;
        this.tabImageRes = tabImageRes;
        this.tabDesc = tabDesc;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public int getTabImageRes() {
        return tabImageRes;
    }

    public void setTabImageRes(int tabImageRes) {
        this.tabImageRes = tabImageRes;
    }

    public String getTabDesc() {
        return tabDesc;
    }

    public void setTabDesc(String tabDesc) {
        this.tabDesc = tabDesc;
    }
}
