package com.gps.sweeprobot.model.mapmanager.contract;

import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.mvp.IView;

import java.util.List;

/**
 * Create by WangJun on 2017/7/17
 */

public class MapManagerContract {

    public static final String MAP_ID_KEY = "map_id_key";
    public static final String MAP_NAME_KEY = "map_name_key";

    public interface presenter{}

    public interface view extends IView{

        void notifyAdapterDataChanged(List<GpsMapBean> data);
    }
}
