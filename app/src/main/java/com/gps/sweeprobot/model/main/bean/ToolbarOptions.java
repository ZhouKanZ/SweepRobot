package com.gps.sweeprobot.model.main.bean;

import com.gps.sweeprobot.R;

/**
 * Create by WangJun on 2017/7/24
 */

public class ToolbarOptions {

    /**
     * toolbar的title资源id
     */
    public int titleId = 0;
    /**
     * toolbar的title
     */
    public String titleString;
    /**
     * toolbar的logo资源id
     */
    public int logoId;
    /**
     * toolbar的返回按钮资源id，默认开启的资源nim_actionbar_dark_back_icon
     */
    public int navigateId = R.mipmap.back;
    /**
     * toolbar的返回按钮，默认开启
     */
    public boolean isNeedNavigate = true;

}
