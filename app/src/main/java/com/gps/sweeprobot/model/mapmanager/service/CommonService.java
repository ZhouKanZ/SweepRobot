package com.gps.sweeprobot.model.mapmanager.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Create by WangJun on 2017/7/18
 */

public interface CommonService {

    @GET
    @Streaming
    Call<ResponseBody> downImage(@Url String url);
}
