package com.gps.sweeprobot.bean;

/**
 * Create by WangJun on 2017/7/28
 * 发送给ROS的数据类
 */

public class WebSocketResult<T> {

    private String op;
    private String service;
    private T args;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public T getArgs() {
        return args;
    }

    public void setArgs(T args) {
        this.args = args;
    }
}
