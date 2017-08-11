package com.gps.sweeprobot.test;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.gps.ros.response.LaserPose;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.model.createmap.contract.CreateMapContract;
import com.gps.sweeprobot.model.createmap.presenter.CreateMapPresenter;
import com.gps.sweeprobot.widget.GpsImage;
import com.gps.sweeprobot.widget.RockerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/10 0010
 * @Descriptiong : xxx
 */

public class TestActivity extends BaseActivity<CreateMapPresenter, CreateMapContract.View>
        implements
        CreateMapContract.View,
        RockerView.OnAngleChangeListener,
        Animator.AnimatorListener,
        Consumer<Bitmap> {

    private static final String TAG = "TestActivity";

    @BindView(R.id.gps_layout)
    GestureFrameLayout gpsLayout;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv)
    GpsImage iv;
    @BindView(R.id.rockerView_center)
    RockerView rockerViewCenter;

    private double[] velocity = new double[2];
    private long lasttime = 0L;
    private long currenttime = 0L;
    private static final int TIME_INTERVAL = 100;

    @Override
    protected TextView getTitleTextView() {
        return title;
    }

    @Override
    protected String getTitleText() {
        return "创建地图";
    }

    @Override
    protected CreateMapPresenter loadPresenter() {
        return new CreateMapPresenter();
    }


    @Override
    protected void initData() {

        initView();

        mPresenter.subscribe();
        mPresenter.registerRxBus();
        mPresenter.startScanMap();
    }

    private void initView() {

        setLeftVisiable(true);
        gpsLayout
                .getController()
                .getSettings()
                .setRotationEnabled(true)
                .setMaxZoom(10f);

        rockerViewCenter.setOnAngleChangeListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void otherViewClick(View view) {


    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

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
        Log.d(TAG, "changeRobotPos: x :" + x + "   y:" + y);
        iv.setRobotPosition((float) x, (float) y);
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
        return velocity;
    }

    @Override
    public void showLaserPoints(List<LaserPose.DataBean> points) {
        Log.d(TAG, "showLaserPoints: " + points.size());
        iv.setLaserPoints(points);
    }

    @Override
    public void showIutInfoDialog() {

    }

    @Override
    public void hideIutInfoDialog() {

    }


    @Override
    public void onStarting() {
        mPresenter.loopSendCommandToRos();
    }

    /**
     * @param angle 角度[0,360)
     * @param length [0,R-r]
     */
    @Override
    public void change(double angle, float length) {
            velocity[0] = angle;
            velocity[1] = length;
    }

    @Override
    public void onFinish() {
//        velocity[0] = 0;
//        velocity[1] = 0;
//        mPresenter.sendCommandToRos();
        mPresenter.stopLoop();
    }

    @Override
    public void accept(@NonNull Bitmap bitmap) throws Exception {
        Log.d("test", "accept: " + bitmap);
        iv.setMap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.stopScanMap();
        mPresenter.unregisterRxBus();
    }

    @Override
    public ImageView getLeftImageView() {
        return ivBack;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        this.finish();
    }
}
