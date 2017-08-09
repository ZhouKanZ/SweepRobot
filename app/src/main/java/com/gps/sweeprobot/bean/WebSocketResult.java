package com.gps.sweeprobot.bean;

/**
 * Create by WangJun on 2017/7/28
 */

public class WebSocketResult<T> {

    private String op;
    private String topic;
    private T msg;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

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
}
