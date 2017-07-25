package com.gps.sweeprobot.model.mapmanager.presenterimpl;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.gps.sweeprobot.bean.IAction;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.model.mapmanager.adaper.item.ActionItem;
import com.gps.sweeprobot.model.mapmanager.bean.MapListBean;
import com.gps.sweeprobot.model.mapmanager.model.ActionModel;
import com.gps.sweeprobot.model.mapmanager.model.MapListModel;
import com.gps.sweeprobot.model.mapmanager.presenter.MapEditPresenter;
import com.gps.sweeprobot.model.view.adapter.CommonRcvAdapter;
import com.gps.sweeprobot.model.view.adapter.item.AdapterItem;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.utils.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create by WangJun on 2017/7/19
 */

public class MapEditPresenterImpl extends MapEditPresenter implements MapListModel.InfoHint {

    private static String MAP_KEY = "mapEdit";
    private static String ACTION_KEY = "action_key";
    private List<MapListBean> mapList;
    private List<IAction> actionList;
    private ActionItem.ActionOnItemListener listener;

    public MapEditPresenterImpl(ActionItem.ActionOnItemListener listener) {

        mapList = new ArrayList<>();
        actionList = new ArrayList<>();
        this.listener = listener;
    }



    @Override
    public HashMap<String, IModel> getiModelMap() {

        return loadModelMap(new MapListModel(),new ActionModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {

        HashMap<String, IModel> map = new HashMap<>();
        map.put(MAP_KEY,models[0]);
        map.put(ACTION_KEY,models[1]);
        return map;
    }

    @Override
    public RecyclerView.Adapter initAdapter() {

        return new CommonRcvAdapter<IAction>(actionList) {

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                return new ActionItem(listener);
            }
        };
    }

    /**
     * 设置传入adapter中的数据
     */
    @Override
    public void setData() {

        //获取地图img
        MapListModel mapListModel = (MapListModel) getiModelMap().get(MAP_KEY);
        mapListModel.downMapImg(this);

        //测试数据
        PointBean pointBean = new PointBean();
        pointBean.setMapName("testMap");
        pointBean.setX(300);
        pointBean.setY(400);
        pointBean.setPointName("test");
        pointBean.save();

        //获取action的数据
        ActionModel actionModel = (ActionModel) getiModelMap().get(ACTION_KEY);
        actionModel.getActionData(new ActionModel.InfoMessager() {
            @Override
            public void successInfo(List<PointBean> data) {
                actionList.clear();
                actionList.addAll(data);
            }

            @Override
            public void failInfo(Throwable e) {
                LogManager.e(e.getMessage());
            }
        });
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
    }

    @Override
    public void failInfo(Throwable e) {

        LogManager.e(e.getMessage());
    }

}
