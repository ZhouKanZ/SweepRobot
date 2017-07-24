package com.gps.sweeprobot.model.createmap.contract;

import android.view.View;

import com.gps.sweeprobot.mvp.IView;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/14 0014
 * @Descriptiong : xxx
 */

public class CreateMapContract {

    public interface Presenter{
        void startScanMap();
        void stopScanMap();
        void sendCommandToRos();
    }

    public interface View extends IView{
        void clickScanControl(boolean isScan);
        void cancelScanControl();
        void showControlLayout(android.view.View view);
        void hideControlLayout(android.view.View view);

        /**
         *  首次进入页面时 加载机器人位置
         */
        void showGetRobotsAnimation();

        /**
         *  地图加载成可视后隐藏动画
         */
        void hideGetRobotsAnimation();
    }

}
