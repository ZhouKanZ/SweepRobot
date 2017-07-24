package com.gps.sweeprobot.http;

import com.gps.sweeprobot.model.mapmanager.bean.MapListBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Create by WangJun on 2017/7/18
 * 网络请求接口
 * http://192.168.2.142:82/maps/3f975d03-a803-4483-b62d-dc2341f13eb4.png
 * http://192.168.2.136:82/maps/fb_map.jpg
 */

public interface HttpService {

    @GET("maps/fb_map.jpg")
    @Streaming
    Observable<ResponseBody> downImage();

    @GET("maps/map_testmap.json")
    Observable<List<MapListBean>> getMapList();

    @GET
    @Streaming
    Observable<ResponseBody> downInetImage(@Url String url);
}
