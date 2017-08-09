package com.gps.sweeprobot.model.createmap.contract;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.alibaba.fastjson.JSONObject;
import com.gps.ros.response.LaserPose;
import com.gps.sweeprobot.bean.GpsMap;
import com.gps.sweeprobot.mvp.IView;
import com.gps.sweeprobot.mvp.RxBusModel;
import com.gps.sweeprobot.mvp.RxBusPresenter;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/14 0014
 * @Descriptiong : xxx
 */

public class CreateMapContract {

    public interface Presenter extends RxBusPresenter{
        /**
         *  开始扫描地图
         */
        void startScanMap();

        /**
         *  停止扫描地图
         */
        void stopScanMap();

        /**
         *  给机器人发送指令
         */
        void sendCommandToRos();

        /**
         *  得到机器人的位置
         */
        void getRosPosition();

        void saveMap(GpsMap map);

        void subscribe();
        /**
         *  结束扫描地图
         */
        void finishScanMap();



    }

    public interface View extends IView{

        void displayMap(Bitmap bitmap);

        void clickScanControl(boolean isScan);
        void cancelScanControl();
        void showControlLayout(android.view.View view);
        void hideControlLayout(android.view.View view);

        /**
         *  改变机器人的位置
         */
        void changeRobotPos(double x,double y);

        /**
         *  首次进入页面时 加载机器人位置
         */
        void showGetRobotsAnimation();

        /**
         *  地图加载成可视后隐藏动画
         */
        void hideGetRobotsAnimation();

        /**
         * 得到consumer
         * @return
         */
        Consumer<Bitmap> getConsumer();

        /**
         *
         * @return 得到速度  0位置表示角度  1 位置表示length
         */
        double[] getVelocity();

        /**
         *  显示镭射点
         * @param points
         */
        void showLaserPoints(List<LaserPose.DataBean> points);

        void showIutInfoDialog();
        void hideIutInfoDialog();
    }

    public interface Model extends RxBusModel<JSONObject> {

        /**
         *  扫描地图
         * @param consumer
         */
        Disposable startScan(Consumer<Bitmap> consumer);

        /**
         * 停止扫描地图
         */
        void stopScan(Disposable disposable);

        /**
         * 保存地图
         */
        void saveMap(GpsMap gpsMap);


        /**
         *  发送速度到机器人端
         * @param angel    角度
         * @param length   长度
         */
        void sendVelocityToRos(double angel, float length);

        void subscribe();

    }
}
