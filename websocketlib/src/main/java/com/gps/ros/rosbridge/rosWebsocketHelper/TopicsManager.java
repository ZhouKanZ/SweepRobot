package com.gps.ros.rosbridge.rosWebsocketHelper;

import com.gps.ros.rosbridge.ROSBridgeClient;
import com.gps.ros.rosbridge.operation.Advertise;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/10 0010
 * @Descriptiong : 用来管理发布和订阅的topic
 */

public class TopicsManager {

    /**
     *  保存已经订阅的Topic
     */
    public static Set<String> topicSet = new HashSet<>();

    /**
     * 判断是否存在
     * @param topic
     * @return
     */
    public static boolean checkExist(String topic){

        if (topicSet.contains(topic)){
            return true;
        }else {
            return false;
        }
    }

    /**
     *  advertise
     */
    public static void advertise(Advertise advertise){

    }

}
