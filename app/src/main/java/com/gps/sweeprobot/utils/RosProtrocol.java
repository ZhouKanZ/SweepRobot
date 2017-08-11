package com.gps.sweeprobot.utils;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/17 0017
 * @Descriptiong : Ros相关的位置信息
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
     * 标记点
     */
    public class Point{

        public static final String OPERATE = "call_service";
        public static final String SERVICE = "nav_pose_set";
        public static final String FRAME_ID = "map";
    }

    public class Obstacle{

        public static final String OPERATE = "call_service";
        public static final String SERVICE = "wall_pose_set";
    }
    

    public class NaviPosition{
        public static final String TOPIC = "nav_position";
        public static final String TYPE  = "basic_msgs/nav_pose";
    }

    /**
     *  机器人位置信息
     */
    public class PicturePose{
        public static final String TOPIC = "picture_pose";
        public static final String TYPE  = "geometry_msgs/Pose";
    }

    /**
     * 激光点
     */
    public class LaserPose{
        public static final String TOPIC = "laser_pose_picture";
        public static final String TYPE  = "basic_msgs/laser_points";
    }

}
