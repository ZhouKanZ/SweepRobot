package com.gps.sweeprobot.model.createmap.model;

import com.gps.ros.response.PicturePose;

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

}
