package com.gps.sweeprobot.model.mapmanager.contract;

import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.mvp.IView;

import java.util.List;

/**
 * Create by WangJun on 2017/7/17
 */

public class MapManagerContract {

    public interface presenter{}

    public interface view extends IView{

        void notifyAdapterDataChanged(List<GpsMapBean> data);
    }
}
