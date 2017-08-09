package com.gps.ros.response;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/6 0006
 * @Descriptiong : xxx
 */

public class PicturePose {

    /**
     * orientation : {"y":0.12183448994023736,"z":3.264542558438373E-9,"x":0.9925504304875403,"w":-2.6595286140339307E-8}
     * position : {"y":13.56066506485596,"z":7.267132360740532E-7,"x":13.183336469984924}
     */

    private OrientationBean orientation;
    private PositionBean position;

    public OrientationBean getOrientation() {
        return orientation;
    }

    public void setOrientation(OrientationBean orientation) {
        this.orientation = orientation;
    }

    public PositionBean getPosition() {
        return position;
    }

    public void setPosition(PositionBean position) {
        this.position = position;
    }

    public static class OrientationBean {
        /**
         * y : 0.12183448994023736
         * z : 3.264542558438373E-9
         * x : 0.9925504304875403
         * w : -2.6595286140339307E-8
         */

        private double y;
        private double z;
        private double x;
        private double w;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getZ() {
            return z;
        }

        public void setZ(double z) {
            this.z = z;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getW() {
            return w;
        }

        public void setW(double w) {
            this.w = w;
        }
    }

    public static class PositionBean {
        /**
         * y : 13.56066506485596
         * z : 7.267132360740532E-7
         * x : 13.183336469984924
         */

        private double y;
        private double z;
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getZ() {
            return z;
        }

        public void setZ(double z) {
            this.z = z;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }
}
