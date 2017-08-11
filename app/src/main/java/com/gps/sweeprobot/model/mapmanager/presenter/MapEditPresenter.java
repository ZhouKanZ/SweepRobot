package com.gps.sweeprobot.model.mapmanager.presenter;

import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.mapmanager.view.activity.MapEditActivity;

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
}
