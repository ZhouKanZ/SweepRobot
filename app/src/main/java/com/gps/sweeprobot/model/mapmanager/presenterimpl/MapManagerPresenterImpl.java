package com.gps.sweeprobot.model.mapmanager.presenterimpl;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.model.mapmanager.adaper.item.MapListItem;
import com.gps.sweeprobot.model.mapmanager.model.MapManagerModel;
import com.gps.sweeprobot.model.mapmanager.presenter.MapManagerPresenter;
import com.gps.sweeprobot.model.view.adapter.CommonRcvAdapter;
import com.gps.sweeprobot.model.view.adapter.item.AdapterItem;
import com.gps.sweeprobot.mvp.IModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create by WangJun on 2017/7/17
 */

public class MapManagerPresenterImpl extends MapManagerPresenter {

    private List<GpsMapBean> maps;
    private static final String MAP_KEY = "mapList";
    private MapListItem.MOnItemClickListener listener;
    private MapManagerModel mapManagerModel;

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

    @Override
    public int getMapId(int position) {
        return maps.get(position).getId();
    }
}
