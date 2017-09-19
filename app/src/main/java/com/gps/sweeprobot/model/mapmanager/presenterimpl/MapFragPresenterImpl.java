//package com.gps.sweeprobot.model.mapmanager.presenterimpl;
//
//import android.graphics.Bitmap;
//import android.support.v7.widget.RecyclerView;
//
//import com.gps.sweeprobot.model.mapmanager.model.MapFragModel;
//import com.gps.sweeprobot.model.mapmanager.presenter.MapFragmentPresenter;
//import com.gps.sweeprobot.mvp.IModel;
//import com.gps.sweeprobot.url.UrlHelper;
//
//import java.util.HashMap;
//
///**
// * Create by WangJun on 2017/7/18
// */
//
//public class MapFragPresenterImpl extends MapFragmentPresenter {
//
//    public static final String MAP_KEY="mapJpg";
//
//    private MapDownListener listener;
//
//
//    @Override
//    public HashMap<String, IModel> getiModelMap() {
//        return loadModelMap(new MapFragModel());
//    }
//
//    @Override
//    public HashMap<String, IModel> loadModelMap(IModel... models) {
//
//        HashMap<String, IModel> map = new HashMap<>();
//        map.put(MAP_KEY,models[0]);
//        return map;
//    }
//
//    @Override
//    public RecyclerView.Adapter initAdapter() {
//        return null;
//    }
//
//    @Override
//    public void setData() {
//
//        ((MapFragModel) getiModelMap().get(MAP_KEY)).downMapJpg(UrlHelper.MAP_JPG_URL, new MapFragModel.InfoHint() {
//            @Override
//            public void successInfo(Bitmap bitmap) {
//
//                listener.mapDownListener(bitmap);
//            }
//
//            @Override
//            public void failInfo(String str) {
//
//            }
//        });
//    }
//
//
//    @Override
//    public void setMapDownListenr(MapDownListener listener) {
//
//        this.listener=listener;
//    }
//
//    public interface MapDownListener{
//
//        void mapDownListener(Bitmap bitmap);
//    }
//
//}
