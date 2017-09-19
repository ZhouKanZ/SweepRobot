package com.gps.ros.response;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/4 0004
 * @Descriptiong : xxx
 */

public class SubscribeResponse<T> {

    /**
     * topic : /cmd_vel
     * msg : {}
     * op : publish
     */
    private String topic;
    private T msg;
    private String op;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}
