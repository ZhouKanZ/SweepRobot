package com.gps.sweeprobot.model.taskqueue.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.State;
import com.gps.ros.response.LaserPose;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.bean.FitParams;
import com.gps.sweeprobot.bean.WebSocketResult;
import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.http.Http;
import com.gps.sweeprobot.http.WebSocketHelper;
import com.gps.sweeprobot.model.createmap.contract.CreateMapContract;
import com.gps.sweeprobot.model.createmap.presenter.CreateMapPresenter;
import com.gps.sweeprobot.utils.DegreeManager;
import com.gps.sweeprobot.utils.JsonCreator;
import com.gps.sweeprobot.widget.GpsView;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/29 0029
 * @Descriptiong : xxx
 */

public class TaskExecuteActivity extends BaseActivity<CreateMapPresenter, CreateMapContract.View>
        implements CreateMapContract.View,
        Consumer<Bitmap> {

    private static final String TAG = "TaskExecuteActivity";
    public static final String MAP_ID = "map_id_ccc";
    public static final String TASK_ID = "TASK_ID";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.gpsview)
    GpsView gpsview;
    @BindView(R.id.ib_fit_complete)
    ImageButton ibFitComplete;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int map_id = -1;
    private int task_id = -1;
    private GpsMapBean gpsMapBean = null;

    private Double[] position = new Double[2];

    @Override
    protected TextView getTitleTextView() {
        return title;
    }

    @Override
    protected String getTitleText() {
        return "任务执行详情";
    }

    @Override
    protected CreateMapPresenter loadPresenter() {
        return new CreateMapPresenter();
    }

    @Override
    public ImageView getLeftImageView() {
        return ivBack;
    }

    @Override
    protected void initData() {

        Log.d(TAG, "initData: ");
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getBundleExtra(TaskExecuteActivity.class.getSimpleName());
            map_id = bundle.getInt(MAP_ID);
            task_id = bundle.getInt(TASK_ID);
            Log.d(TAG, "initData: " + map_id);
        }

        if (map_id != -1){
            gpsMapBean = DataSupport.find(GpsMapBean.class,map_id);
            Log.d(TAG, "initData: " + gpsMapBean);
        }

        setLeftVisiable(true);
        mPresenter.subscribe();
        mPresenter.registerRxBus();

        Log.e(TAG, "initData: " + "maps/"+map_id+"/"+map_id+".jpg");
        Http.getHttpService()
                .downInetImage("maps/"+map_id+"/"+map_id+".jpg")
                .map(new Function<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull ResponseBody responseBody) throws Exception {
                        return BitmapFactory.decodeStream(responseBody.byteStream());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Function<Throwable, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull Throwable throwable) throws Exception {
                        return BitmapFactory.decodeResource(getResources(), R.mipmap.testmap);
                    }
                })
                .subscribe(this, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        Log.d("xx", "accept: ");
                    }
                });

        gpsview
                .getController()
                .getSettings()
                .setMaxZoom(10)
                .setRotationEnabled(true)
                .setOverscrollDistance(1000, 1000)
                .setZoomEnabled(false)
                .disableBounds();

        // 进入
        WebSocketHelper.send(JsonCreator.mappingStatus(6,gpsMapBean.getId(),gpsMapBean.getName(),"/var/www/maps").toJSONString());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_execute;
    }

    @Override
    protected void otherViewClick(View view) {
    }

    @Override
    protected void initListener() {
    }

    @Override
    public void displayMap(Bitmap bitmap) {
    }

    @Override
    public void clickScanControl(boolean isScan) {
    }

    @Override
    public void cancelScanControl() {
    }

    @Override
    public void showControlLayout(View view) {
    }

    @Override
    public void hideControlLayout(View view) {
    }

    @Override
    public void changeRobotPos(double x, double y) {
        position[0] = x;
        position[1] = y;
        gpsview.setRobotPosition(x, y);
    }

    @Override
    public void showGetRobotsAnimation() {
    }

    @Override
    public void hideGetRobotsAnimation() {
    }

    @Override
    public Consumer<Bitmap> getConsumer() {
        return this;
    }

    @Override
    public double[] getVelocity() {
        return new double[0];
    }

    @Override
    public void showLaserPoints(List<LaserPose.DataBean> points) {
        gpsview.setLaserPoints(points);
    }

    @Override
    public void showIutInfoDialog() {
    }

    @Override
    public void hideIutInfoDialog() {
    }

    @Override
    public void showCompleteDialog(String msg, int type) {

    }

    @Override
    public void accept(@NonNull Bitmap bitmap) throws Exception {
        gpsview.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unregisterRxBus();
        WebSocketHelper.send(JsonCreator.mappingStatus(7,gpsMapBean.getId(),gpsMapBean.getName(),"/var/www/maps").toJSONString());
    }

    @OnClick({R.id.iv_back, R.id.ib_fit_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.ib_fit_complete:
                // 需要在操作之前获取到没有发生变化前时的 ---
                Matrix matrix = gpsview.completeFit();
                convertPositionAfterFit(matrix);
                break;
        }
    }

    /**
     *  计算角度 -- 计算 x y
     * @param matrix
     */
    private void convertPositionAfterFit(Matrix matrix){
        if (gpsMapBean == null){
            return;
        }

        float[] matrixValues = new float[9];
        matrix.getValues(matrixValues);
        WebSocketResult<FitParams> fit = new WebSocketResult();
        fit.setService("/nav_flag");
        fit.setOp("call_service");
        FitParams fitP = new FitParams();
        fitP.setMap_id(gpsMapBean.getId());
        fitP.setMap_name(gpsMapBean.getName());
        fitP.setNav_flag(1);
        FitParams.Gridpose gridPose = new FitParams.Gridpose();

        PointF pointF = DegreeManager.changeRelativePoint(gpsview.getRobotPosition()[0],gpsview.getRobotPosition()[1],matrix);
        gridPose.setX(pointF.x);
        gridPose.setY(pointF.y);
        gridPose.setAngle((float) Math.atan(matrixValues[3]/matrixValues[4]));
        fitP.setGridpose(gridPose);
        fit.setArgs(fitP);

        Log.d("xxx", "convertPositionAfterFit: " + com.alibaba.fastjson.JSON.toJSONString(fit));

        WebSocketHelper.send(com.alibaba.fastjson.JSON.toJSONString(fit));

    }

}
