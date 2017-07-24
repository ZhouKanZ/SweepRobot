package com.gps.sweeprobot.model.mapmanager.model;

import com.gps.sweeprobot.model.mapmanager.bean.MapListBean;
import com.gps.sweeprobot.mvp.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by WangJun on 2017/7/17
 * 主要做一些数据处理呀,网路请求呀
 */

public class MapManagerModel implements IModel {

    private List<MapListBean> data;

    public void getMapListData(InfoHint infoHint){

        data = new ArrayList<>();

        infoHint.successInfo(data);
    }

    public interface InfoHint{

        void successInfo(List<MapListBean> data);

        void failInfo();
    }
}
