package com.gps.ros.response;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/4 0004
 * @Descriptiong : service的返回值
 */

public class ServiceResponse<T>  {

    /**
     * values : {}
     * result : true
     * service : /rosapi/get_time
     * op : service_response
     */

    private T values;
    private boolean result;
    private String service;
    private String op;

    public T getValues() {
        return values;
    }

    public void setValues(T values) {
        this.values = values;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}
