package com.gps.sweeprobot.http;

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

    @GET("maps/map_pose_0809.jpg")
    @Streaming
    Observable<ResponseBody> downImage();

    @GET
    @Streaming
    Observable<ResponseBody> getMapList(@Url String url);

    @GET
    @Streaming
    Observable<ResponseBody> downInetImage(@Url String url);

}

