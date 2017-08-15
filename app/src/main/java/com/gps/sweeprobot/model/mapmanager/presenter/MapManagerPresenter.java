package com.gps.sweeprobot.model.mapmanager.presenter;

import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.mapmanager.view.activity.MapManagerActivity;

/**
 * Create by WangJun on 2017/7/17
 */

public abstract class MapManagerPresenter extends BasePresenter<MapManagerActivity> {

    public abstract int getMapId(int presenter);
}
