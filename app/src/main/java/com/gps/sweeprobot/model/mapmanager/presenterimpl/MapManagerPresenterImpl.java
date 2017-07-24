package com.gps.sweeprobot.model.mapmanager.presenterimpl;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.gps.sweeprobot.model.mapmanager.adaper.item.MapListItem;
import com.gps.sweeprobot.model.mapmanager.bean.MapListBean;
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

    private List<MapListBean> mapListBeanList;
    private static final String MAP_KEY="mapList";
    private MapListItem.MOnItemClickListener listener;


    public MapManagerPresenterImpl(MapListItem.MOnItemClickListener listener) {
        this.listener = listener;
        mapListBeanList=new ArrayList<>();
    }

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new MapManagerModel()) ;
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {

        HashMap<String,IModel> map = new HashMap<>();
        map.put(MAP_KEY,models[0]);
        return map;
    }

    @Override
    public RecyclerView.Adapter initAdapter() {
        return new CommonRcvAdapter<MapListBean>(mapListBeanList) {

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {

                return new MapListItem(listener);
            }
        };
    }

    @Override
    public void setData() {

        ((MapManagerModel) getiModelMap().get(MAP_KEY)).getMapListData(new MapManagerModel.InfoHint() {
            @Override
            public void successInfo(List<MapListBean> data) {

                mapListBeanList.addAll(data);
            }

            @Override
            public void failInfo() {

            }
        });
    }
}
