package com.gps.sweeprobot.model.main.contract;

import com.gps.ros.rosbridge.ROSBridgeClient;
import com.gps.ros.rosbridge.ROSClient;
import com.gps.sweeprobot.mvp.IView;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/13 0013
 * @Descriptiong : xxx
 */

public class IpContract {

    public interface Presenter{

        /**
         *  连接到Ros
         */
        void connectToRos();

    }

    public interface View extends IView {

        /**
         *  得到iptext
         * @return
         */
        String getIpText();

        void showProgress();

        void hideProgress();

        void showToast(String msg);

        void startAct();

        void showDialog();
    }

    public interface Model {
        ROSBridgeClient connect(String domain , ROSClient.ConnectionStatusListener listener);
    }
}
