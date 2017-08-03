package com.gps.ros.android;

import com.gps.ros.rosbridge.operation.Advertise;
import com.gps.ros.rosbridge.operation.Operation;
import com.gps.ros.rosbridge.operation.Publish;
import com.gps.ros.rosbridge.operation.Subscribe;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/12 0012
 * @Descriptiong : xxx
 */

public class TranslationManager {

    /**
     * 话题集合
     */
    private static Set<String> topicSet = new HashSet<>();

    /**
     * 发送话题
     */
    public static void advertiseTopic(Advertise advertise) {
        if (!topicSet.contains(advertise.topic)) {
            topicSet.add(advertise.topic);
            send(advertise);
        }
    }

    /**
     * publish
     */
    public static void publish(Publish publish) {
        // 表示话题没被advertise
        if (!topicSet.contains(publish.op)) {
            throw new RuntimeException("Before you publish smothing you must advertise this topic!");
        } else {
            send(publish);
        }
    }

    /**
     * 订阅
     *
     * @param sub
     */
    public static void subscribe(Subscribe sub) {
        send(sub);
    }


    /**
     * 发送消息
     *
     * @param op
     */
    public static void send(Operation op) {

        if (RosService.getRosBridgeClient() != null) {
            RosService.getRosBridgeClient().send(op.toJSON());
        } else {
            throw new RuntimeException("please keep websocket online");
        }
    }


}
