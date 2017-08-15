package com.gps.sweeprobot.model.mapmanager.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.database.MyPointF;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.database.VirtualObstacleBean;
import com.gps.sweeprobot.http.Http;
import com.gps.sweeprobot.model.mapmanager.service.CommonService;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.url.UrlHelper;
import com.gps.sweeprobot.utils.CommunicationUtil;
import com.gps.sweeprobot.utils.LogManager;
import com.gps.sweeprobot.utils.LogUtils;
import com.gps.sweeprobot.utils.ToastManager;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Create by WangJun on 2017/7/19
 * 获取地图集合的数据
 */

public class MapListModel implements IModel {


    /***
     * 获取地图图片
     * @param infoHint
     */
    public void downMapImg(int mapId,final InfoHint infoHint) {

        GpsMapBean gpsMapBean = DataSupport.find(GpsMapBean.class, mapId);

        Http.getHttpService()
                .getMapList(UrlHelper.BASE_URL + gpsMapBean.getCompletedMapUrl())
                .subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull ResponseBody responseBody) throws Exception {

                        Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                        return bitmap;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //切断事件
                    }

                    @Override
                    public void onNext(@NonNull Bitmap bitmap) {
                        infoHint.successInfo(bitmap);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastManager.showShort("have a error by request map data from server");
                        infoHint.failInfo(e);
                    }

                    @Override
                    public void onComplete() {
                        LogManager.i("on complete");
                    }
                });
    }

    /**
     * 保存标记点数据，并通知服务器
     * @param pointF
     * @param pointName
     * @param mapid
     */
    public void savePoint(PointF pointF,String pointName,int mapid){

        //将标记点数据存进数据库
        PointBean pointBean = new PointBean();
        pointBean.setX(pointF.x);
        pointBean.setY(pointF.y);
        pointBean.setPointName(pointName);
        pointBean.save();

        //将标记点存进地图数据中
        GpsMapBean mapBean = DataSupport.find(GpsMapBean.class,mapid);
        List<PointBean> all = DataSupport.findAll(PointBean.class);
        mapBean.setPointBeanList(all);
        mapBean.save();

        //通知服务器
        CommunicationUtil.sendPoint2Ros(pointBean);
    }

    /**
     * 保存虚拟墙数据，并通知服务器
     * @param myPointFs
     * @param name
     * @param mapid
     */
    public void saveObstacle(List<MyPointF> myPointFs,String name,int mapid){

        //将虚拟墙数据存进数据库
        DataSupport.saveAll(myPointFs);
        VirtualObstacleBean virtualObstacleBean = new VirtualObstacleBean();
        virtualObstacleBean.setName(name);
        virtualObstacleBean.setMyPointFs(myPointFs);
        virtualObstacleBean.save();

        //将虚拟墙存进地图数据中
        GpsMapBean mapBean = DataSupport.find(GpsMapBean.class, mapid);
        List<VirtualObstacleBean> all = DataSupport.findAll(VirtualObstacleBean.class);
        mapBean.setVirtualObstacleBeanList(all);
        mapBean.save();

        //通知服务器
        CommunicationUtil.sendObstacle2Ros(virtualObstacleBean);
    }

    public void test(final InfoHint infoHint){

        Http.getRetrofit()
                .create(CommonService.class)
                .downImage("https://img6.bdstatic.com/img/image/smallpic/yangmixiaotugengxin.jpg")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        LogManager.i(MainApplication.getContext().getThreadName());

                        Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        LogUtils.e("123",t.getMessage());
                    }
                });

    }


    public interface InfoHint {

        void successInfo(Bitmap map);

        void failInfo(Throwable e);
    }
}
