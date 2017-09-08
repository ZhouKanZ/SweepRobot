package com.gps.sweeprobot.http;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/21 0021
 * @Descriptiong : 存放App的静态常量
 */

public class Constant {

    private static final String Robot_Domain = "http://192.168.2.133:82/";

    /* test */
    public static String test = "http://192.168.2.117:82";

    /* 焦建 */
    public static String JiaoJian = "192.168.31.133:9090";

    /* 域名 */
    public static String DOMAIN = Robot_Domain;

    /* 机器人的域名 */
    public static String ROBOT_DOMIN = "192.168.2.128:9090";

    /* 获取预览地图 */
    public static final String GET_MAP = "/maps/map_test.jpg";

    /** 标记点或虚拟墙的删除操作 */
    public static final int DELETE = 1;

    /** 标记点或虚拟墙添加操作 */
    public static final int ADD = 2;

    /** 标记点或虚拟墙更新操作 */
    public static final int UPDATE = 3;

}
