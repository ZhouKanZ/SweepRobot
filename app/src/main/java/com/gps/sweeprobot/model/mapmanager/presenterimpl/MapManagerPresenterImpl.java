package com.gps.sweeprobot.model.mapmanager.presenterimpl;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.model.mapmanager.adaper.item.MapListItem;
import com.gps.sweeprobot.model.mapmanager.contract.MapManagerContract;
import com.gps.sweeprobot.model.mapmanager.model.MapManagerModel;
import com.gps.sweeprobot.model.mapmanager.presenter.MapManagerPresenter;
import com.gps.sweeprobot.model.view.adapter.CommonRcvAdapter;
import com.gps.sweeprobot.model.view.adapter.item.AdapterItem;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.utils.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create by WangJun on 2017/7/17
 */

public class MapManagerPresenterImpl extends MapManagerPresenter {

    //地图数据集合
    private List<GpsMapBean> maps;
    //获取model的key
    private static final String MAP_KEY = "mapList";
    //item点击事件监听
    private MapListItem.MOnItemClickListener listener;
    //model
    private MapManagerModel mapManagerModel;
    //view
    private MapManagerContract.view managerView;

    public MapManagerPresenterImpl(MapListItem.MOnItemClickListener listener) {
        this.listener = listener;
        maps = new ArrayList<>();
    }


    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new MapManagerModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {

        HashMap<String, IModel> map = new HashMap<>();
        map.put(MAP_KEY, models[0]);
        return map;
    }

    @Override
    public RecyclerView.Adapter initAdapter() {

        return new CommonRcvAdapter<GpsMapBean>(maps) {

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {

                MapListItem mapListItem = new MapListItem(listener);
                mapListItem.setRequestMapListener(mapManagerModel);
                return mapListItem;
            }
        };
    }


    @Override
    public void setData() {

        mapManagerModel = (MapManagerModel) getiModelMap().get(MAP_KEY);

        mapManagerModel.getMapsFromDatabase(new MapManagerModel.InfoHint() {
            @Override
            public void successInfo(List<GpsMapBean> data) {

                maps.clear();
                maps.addAll(data);
            }

            @Override
            public void failInfo() {


            }
        });
    }

    /**
     * 获得地图的主键
     * @param position
     * @return
     */
    @Override
    public int getMapId(int position) {
        return maps.get(position).getId();
    }

    /**
     * dialog的删除键点击事件
     * @param position
     */
    @Override
    public void deleteViewOnClick(int position) {

        LogManager.i("maps size ==========="+maps.size()+"*********position =========="+position);
        //数据库更新
        mapManagerModel.deleteMapData(maps.get(position).getId());
        //移除数据
        maps.remove(position);
        //adapter 刷新
        managerView.notifyAdapterDataChanged(maps);
        //通知服务器
    }

    @Override
    public void setView(MapManagerContract.view view) {

        managerView = view;
    }
}
