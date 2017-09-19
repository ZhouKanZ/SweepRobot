package com.gps.ros.response;

import java.util.List;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/9 0009
 * @Descriptiong : 镭射点
 */

public class LaserPose {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * y : 47.345045897670836
         * x : 51.07507311781742
         */

        private double y;
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }
}
