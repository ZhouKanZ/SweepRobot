package com.gps.sweeprobot.model.createmap.contract;

import android.view.View;

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

    public interface View{
        void clickScanControl(boolean isScan);
        void cancelScanControl();
        void showControlLayout(android.view.View view);
        void hideControlLayout(android.view.View view);
    }

}
