package com.gps.sweeprobot.model.setting.model;

import com.gps.sweeprobot.R;

/**
 * Create by WangJun on 2017/8/10
 */

public class SettingTemplate {

    private int id;
    private int icon;
    private int type;
    private int rightIcon = R.mipmap.right_item;
    private String title;
    private String detail;
    private boolean visible;
    private boolean checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getRightIcon() {
        return rightIcon;
    }

    public void setRightIcon(int rightIcon) {
        this.rightIcon = rightIcon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
