package com.gps.sweeprobot.model.setting.contact;

import com.gps.sweeprobot.mvp.IView;

/**
 * Create by WangJun on 2017/8/9
 */

public class SetContract {

    public interface view extends IView{

        void onSpeedItemClick();

        void onInfoItemClick();

        void onVersionItemClick();
    }
}
