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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.bean.GpsMap;
import com.gps.sweeprobot.model.createmap.bean.ControlTab;
import com.gps.sweeprobot.model.createmap.contract.CreateMapContract;
import com.gps.sweeprobot.model.createmap.presenter.CreateMapPresenter;
import com.gps.sweeprobot.utils.DensityUtil;
import com.gps.sweeprobot.utils.ToastManager;
import com.gps.sweeprobot.widget.GpsImageView;
import com.gps.sweeprobot.widget.RockerView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
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
    private DialogPlus dialog;

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
        initView();
        /**
         *  先注册RxBus再进行发送,可以确保信息不会丢失  PublishSubject 的机制
         */
        mPresenter.registerRxBus();
        mPresenter.startScanMap();
    }

    private void initView() {
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
            protected void convert(ViewHolder holder, ControlTab controlTab,int position) {
                ((ImageView) holder.getView(R.id.iv)).setImageBitmap(
                        BitmapFactory.decodeResource(getResources(), controlTab.getResId()));
                holder.setOnClickListener(R.id.iv, new OnControlClickListener(position));
            }
        });

        rockerViewCenter.setOnAngleChangeListener(this);
//        test();

        /* init dialog  */
        dialog = DialogPlus.newDialog(this)
                .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialog_inputinfo))
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {

                        switch (view.getId()) {
                            case R.id.btn_1:
                                dialog.dismiss();
                                break;
                            case R.id.btn_2:
                                EditText etName = (EditText) dialog.getHolderView().findViewById(R.id.et_name);
                                String   robotName = etName.getEditableText().toString();

                                if (TextUtils.isEmpty(robotName)){
                                    ToastManager.show(mCtz,"您忘了为机器人命名噢", Toast.LENGTH_SHORT);
                                }else {
                                    mPresenter.saveMap(new GpsMap());
                                }
                                dialog.dismiss();
                                break;
                        }

                    }
                })
                .setExpanded(false)
                .setContentWidth(RelativeLayout.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.CENTER)
                .create();

    }

    private void test() {
        // 测试添加点
        int px_100 = DensityUtil.dip2px(100);
        gpsMapview.addPoint(px_100, px_100, "我添加的点");
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
    public void changeRobotPos(double x, double y) {
        Log.d(TAG, "changeRobotPos: x :" + x + "   y:" +y);
        gpsMapview.addRobotPoint((float)x,(float)y);
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
    public void showLaserPoints(List<Point> points) {

    }

    @Override
    public void showIutInfoDialog() {
        dialog.show();
    }

    @Override
    public void hideIutInfoDialog() {
        dialog.dismiss();
    }

    @Override
    public void onStart() {

        super.onStart();
        mPresenter.subscribe();
        // 开始的时候 便开始获取机器人的位置
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
        gpsMapview.setImageView(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.stopScanMap();
        mPresenter.unregisterRxBus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: -----------------" );
        /**
         * 将消耗性能的操作 在执行onpaused的时候暂停掉，onresume的时候重新开始
         * ex: 轮询和视图的更新
         */
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     *  控制布局点击事件
     */
    public  class OnControlClickListener implements View.OnClickListener{

        private int position;

        public OnControlClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {

            // 开始
            if (position == 0){
                mPresenter.startScanMap();
            }

            // 暂停
            if (position == 1){
                mPresenter.stopScanMap();
            }

            // 完成
            if (position == 2){
                mPresenter.finishScanMap();
            }

        }
    }
}
