package com.gps.sweeprobot.model.taskqueue.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.ros.response.LaserPose;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.http.Http;
import com.gps.sweeprobot.model.createmap.contract.CreateMapContract;
import com.gps.sweeprobot.model.createmap.presenter.CreateMapPresenter;
import com.gps.sweeprobot.widget.GpsView;

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

public class TaskExecuteActivity extends BaseActivity<CreateMapPresenter,CreateMapContract.View>
        implements CreateMapContract.View,
        Consumer<Bitmap> {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.gpsview)
    GpsView gpsview;

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
        setLeftVisiable(true);
        mPresenter.subscribe();
        mPresenter.registerRxBus();

        Http.getHttpService()
                .downInetImage("maps/now/map.jpg")
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
                        Log.d("xx", "accept: ");
                    }
                });

        gpsview
                .getController()
                .getSettings()
                .setMaxZoom(10)
                .setRotationEnabled(true);

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

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        this.finish();
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
        gpsview.setRobotPosition(x,y);
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
    public void accept(@NonNull Bitmap bitmap) throws Exception {
        gpsview.setBackgroud(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unregisterRxBus();
    }
}
