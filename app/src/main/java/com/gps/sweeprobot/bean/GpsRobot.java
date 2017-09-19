package com.gps.sweeprobot.bean;

import android.graphics.Bitmap;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/9/14 0014
 * @Descriptiong : 吉佩斯机器人
 */

public class GpsRobot {

    private Settings settings;
    private Status status;

    public Settings getSettings() {
        return settings;
    }

    public Status getStatus() {
        return status;
    }

    public static class Settings{

        private double speed;
        private Type type;   // 机器人的类型
        private Bitmap bitmap;
        private Bitmap laserBitmap;

        public double getSpeed() {
            return speed;
        }

        public Type getType() {
            return type;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public Bitmap getLaserBitmap() {
            return laserBitmap;
        }

        /* 机器人的类型 */
        static enum Type{
            sweepRobot,
            washRobot
        }
    }

    public static class Status{

    }

}
