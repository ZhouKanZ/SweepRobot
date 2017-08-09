package com.gps.sweeprobot.model.mapmanager.presenter;

import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.mapmanager.presenterimpl.MapFragPresenterImpl;
import com.gps.sweeprobot.model.mapmanager.view.fragment.MapFragment;

/**
 * Create by WangJun on 2017/7/18
 */

public abstract class MapFragmentPresenter extends BasePresenter<MapFragment> {

    public abstract void setMapDownListenr(MapFragPresenterImpl.MapDownListener listener);
}
