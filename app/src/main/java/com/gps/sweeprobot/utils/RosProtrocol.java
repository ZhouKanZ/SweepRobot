package com.gps.sweeprobot.utils;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/17 0017
 * @Descriptiong : xxx
 */

public class RosProtrocol {

    /**
     *  速度
     */
    public class Speed{
        public static final String TOPIC = "cmd_vel";
        public static final String TYPE  = "geometry_msgs/Twist";
    }

    public class Position{
        public static final String TOPIC = "amcl_pose";
        public static final String TYPE  = "geometry_msgs/PoseWithCovarianceStamped";
    }

    /**
     * 标记点
     */
    public class Point{
        public static final String TOPIC = "/move_base/goal";
        public static final String OPERATE = "publish";
        public static final String FRAME_ID = "map";
    }
    
}
