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

    /**
     *  位置
     */
    public class Position{
        public static final String TOPIC = "amcl_pose";
        public static final String TYPE  = "geometry_msgs/PoseWithCovarianceStamped";
    }

    /**
     *  ros点
     */
    public class NaviPosition{
        public static final String TOPIC = "nav_position";
        public static final String TYPE  = "basic_msgs/nav_pose";
    }

}
