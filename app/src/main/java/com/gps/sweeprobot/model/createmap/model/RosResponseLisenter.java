package com.gps.sweeprobot.model.createmap.model;

import com.gps.ros.response.LaserPose;
import com.gps.ros.response.PicturePose;

import java.util.List;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/6 0006
 * @Descriptiong : xxx
 */

public interface RosResponseLisenter {

    /**
     * 当收到picture
     * @param pose
     */
    void OnReceiverPicture(PicturePose pose);

    /**
     *  接收到激光点
     */
    void onReceiVerLaserPose(List<LaserPose.DataBean> lasers);

}
