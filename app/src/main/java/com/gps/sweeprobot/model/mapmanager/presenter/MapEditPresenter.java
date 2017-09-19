package com.gps.sweeprobot.model.mapmanager.presenter;

import android.graphics.PointF;
import android.os.Bundle;

import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.database.MyPointF;
import com.gps.sweeprobot.model.mapmanager.view.activity.MapEditActivity;

import java.util.List;

/**
 * Create by WangJun on 2017/7/19
 */

public abstract class MapEditPresenter extends BasePresenter<MapEditActivity> {

    public abstract void addViewOnClick();

    public abstract void subViewOnClick();

    public abstract void pointActionOnClick();

    public abstract void obstacleActionOnClick();

    public abstract void positionViewOnClick();

    public abstract void commitViewOnClick();

    public abstract void addPointViewOnClick(String name);

    public abstract void itemOnClick(String name,int position);

    public abstract void itemLongClick(int position);

    public abstract void onDestroy();

    public abstract void obstacleViewOnClick();

    //获取地图列表中的position，以方便区分点击的是哪张地图
    public abstract void setBundle(Bundle bundle);

    public abstract void savePoint(PointF pointF,String pointName);

    public abstract void saveObstacle(List<MyPointF> myPointFs,String name);


    public abstract void exitMap();

    public abstract void enterMap();
}
