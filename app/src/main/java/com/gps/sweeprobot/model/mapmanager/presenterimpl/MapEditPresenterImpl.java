package com.gps.sweeprobot.model.mapmanager.presenterimpl;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

import com.gps.sweeprobot.model.mapmanager.bean.MapListBean;
import com.gps.sweeprobot.model.mapmanager.model.MapListModel;
import com.gps.sweeprobot.model.mapmanager.presenter.MapEditPresenter;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create by WangJun on 2017/7/19
 */

public class MapEditPresenterImpl extends MapEditPresenter implements MapListModel.InfoHint {

    private static String MAP_KEY = "mapEdit";
    private List<MapListBean>  mapList;
    private Bitmap mapBitmap;
    private Drawable mapDrawable;

    public MapEditPresenterImpl() {

        mapList = new ArrayList<>();
    }



    @Override
    public HashMap<String, IModel> getiModelMap() {

        return loadModelMap(new MapListModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {

        HashMap<String, IModel> map = new HashMap<>();
        map.put(MAP_KEY,models[0]);
        return map;
    }

    @Override
    public RecyclerView.Adapter initAdapter() {

        return null;
    }

    /**
     * 设置传入adapter中的数据
     */
    @Override
    public void setData() {

        MapListModel mapListModel = (MapListModel) getiModelMap().get(MAP_KEY);
        mapListModel.downMapImg(this);
//        mapListModel.requestMapListData(this);
    }


    @Override
    public void successInfo(Drawable drawable) {

        getIView().getData(drawable,null);
    }

    @Override
    public void successInfo(Bitmap map) {
        getIView().getData(map);
    }

    @Override
    public void successMapListData(List<MapListBean> data) {

        mapList.clear();
        mapList.addAll(data);
        transData2View();
    }

    @Override
    public void failInfo() {

    }

    private void transData2View(){

        LogUtils.d("trans","========================");
        if (mapBitmap == null || mapList != null)
            return;
//        getIView().getData(mapBitmap,mapList);
    }
}
