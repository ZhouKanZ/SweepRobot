package com.gps.sweeprobot.model.createmap.view.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.model.createmap.bean.ControlTab;
import com.gps.sweeprobot.model.createmap.contract.CreateMapContract;
import com.gps.sweeprobot.model.createmap.presenter.CreateMapPresenter;
import com.gps.sweeprobot.utils.DensityUtil;
import com.gps.sweeprobot.widget.GpsImageView;
import com.gps.sweeprobot.widget.RockerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/13 0013
 * @Descriptiong : 创建地图页面
 */
public class CreateActivity extends BaseActivity<CreateMapPresenter, CreateMapContract.View>
        implements
        CreateMapContract.View,
        RockerView.OnAngleChangeListener,
        Animator.AnimatorListener,
        Consumer<Bitmap> {

    private static final String TAG = "CreateActivity";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rockerView_center)
    RockerView rockerViewCenter;
    @BindView(R.id.gps_mapview)
    GpsImageView gpsMapview;
    @BindView(R.id.iv_point_south)
    ImageView ivPointSouth;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.rv_control)
    RecyclerView rvControl;
    @BindView(R.id.layout_control)
    RelativeLayout layoutControl;
    @BindView(R.id.iv_anim)
    ImageView ivAnim;

    private List<ControlTab> controlTabs;
    private SimpleTarget<Bitmap> target;

    /**
     *  速度
     */
    private double[] velocity = new double[2];
    private long lasttime = 0L;
    private long currenttime = 0L;
    private static final int TIME_INTERVAL = 100;

    /**
     * 控制layout是否隐藏
     */
    private boolean controllayoutisHide = true;

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

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void initData() {

        /* 隐藏状态栏 */
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setLeftVisiable(true);

        gpsMapview.setImageView(BitmapFactory.decodeResource(getResources(), R.mipmap.place_holder));
        controlTabs = new ArrayList<>();
        controlTabs.add(new ControlTab(R.mipmap.play));
        controlTabs.add(new ControlTab(R.mipmap.stop));
        controlTabs.add(new ControlTab(R.mipmap.cancel));

        rvControl.setLayoutManager(new LinearLayoutManager(this));
        rvControl.setAdapter(new CommonAdapter<ControlTab>(this, R.layout.control_item, controlTabs) {

            @Override
            protected void convert(ViewHolder holder, ControlTab controlTab, int position) {
                ((ImageView) holder.getView(R.id.iv)).setImageBitmap(
                        BitmapFactory
                                .decodeResource(getResources(), controlTab.getResId()));
            }
        });

        rockerViewCenter.setOnAngleChangeListener(this);
        mPresenter.startScanMap();

    }

    private void test() {
        // 测试添加点
        gpsMapview.addPoint(100, 100, "我添加的点");
    }

    @Override
    public ImageView getLeftImageView() {
        return ivBack;
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_createmap;
    }

    @Override
    protected void otherViewClick(View view) {
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @OnClick({R.id.iv_back, R.id.iv, R.id.iv_point_south})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.iv:

                if (controllayoutisHide) {
                    iv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.hide));
                    showControlLayout(layoutControl);
                    controllayoutisHide = !controllayoutisHide;
                } else {
                    iv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.show));
                    hideControlLayout(layoutControl);
                    controllayoutisHide = !controllayoutisHide;
                }

                break;
            case R.id.iv_point_south:
                break;
        }
    }


    @Override
    public void displayMap(Bitmap bitmap) {
        gpsMapview.setImageView(bitmap);
    }

    /**********/

    @Override
    public void clickScanControl(boolean isScan) {}

    @Override
    public void cancelScanControl() {}

    /**
     *  offloat() 操作的单位是px
     * 每次动画产生的位移，不是基于现有的位置，而是基于view原本的位置
     *
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void showControlLayout(View view) {

        ObjectAnimator.ofFloat(view, "translationX", 0, -DensityUtil.dip2px(100))
                .setDuration(500)
                .start();

    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void hideControlLayout(View view) {

        ObjectAnimator.ofFloat(view, "translationX", -DensityUtil.dip2px(100), 0)
                .setDuration(500)
                .start();
    }

    @Override
    public void changeRobotPos(Point point) {



    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void showGetRobotsAnimation() {

        ObjectAnimator animator = ObjectAnimator
                .ofFloat(ivAnim, "rotation", 0f, 360f * 3)
                .setDuration(5000);
        animator.start();
        animator.addListener(this);

    }

    // 设置不可见
    @Override
    public void hideGetRobotsAnimation() {
        ivAnim.setVisibility(View.INVISIBLE);
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
    public void onStart() {
        super.onStart();
        mPresenter.subscribe();
    }

    @Override
    public void change(double angle, float length) {

        /**
         *  每隔一定的时间发送一次指令
         */
        if (lasttime == 0L) {
            velocity[0] = angle;
            velocity[1] = length;
            mPresenter.sendCommandToRos();
            lasttime = System.currentTimeMillis();
        } else {
            currenttime = System.currentTimeMillis();
            if (currenttime - lasttime >= TIME_INTERVAL) {
                velocity[0] = angle;
                velocity[1] = length;
                mPresenter.sendCommandToRos();
                lasttime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void onFinish() {

        velocity[0] = 0;
        velocity[1] = 0;
        mPresenter.sendCommandToRos();

    }

    @Override
    public void onAnimationStart(Animator animation) {}

    @Override
    public void onAnimationEnd(Animator animation) {
        hideGetRobotsAnimation();
    }

    @Override
    public void onAnimationCancel(Animator animation) {}

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void accept(@NonNull Bitmap bitmap) throws Exception {

        Log.d(TAG, "accept: " + Thread.currentThread().getName());
        gpsMapview.setImageView(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.stopScanMap();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: -----------------" );
    }
}
