package com.gps.sweeprobot.model.createmap.view.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.gps.ros.response.LaserPose;
import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.http.WebSocketHelper;
import com.gps.sweeprobot.model.createmap.bean.ControlTab;
import com.gps.sweeprobot.model.createmap.contract.CreateMapContract;
import com.gps.sweeprobot.model.createmap.presenter.CreateMapPresenter;
import com.gps.sweeprobot.utils.JsonCreator;
import com.gps.sweeprobot.utils.ToastManager;
import com.gps.sweeprobot.widget.GpsImage;
import com.gps.sweeprobot.widget.RockerView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
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

public class CreateActivity extends BaseActivity<CreateMapPresenter, CreateMapContract.View>
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
    @BindView(R.id.gps_image)
    GpsImage gpsImage;
    @BindView(R.id.rockerView_center)
    RockerView rockerViewCenter;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.rv_control)
    RecyclerView rvControl;
    @BindView(R.id.layout_control)
    RelativeLayout layoutControl;

    private double[] velocity = new double[2];

    private List<ControlTab> controlTabs;

    private boolean controllayoutisHide = true;
    private boolean isScanTasked = true;

    private RecyclerView.Adapter adapter;
    private DialogPlus dialog;
    private AlertDialog.Builder completeDialogBuilder;
    private GpsMapBean gpsMapBean;

    /* 是否保存 -- 是否完成 */
    private boolean isSaved = false
                   ,isCompleted =false;

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

        MainApplication.startRos++;
        initView();
        initDialog();

        /* 根据不同的情况来获取gpsMapBean */
        gpsMapBean = mPresenter.getGpsMapBean();

        mPresenter.subscribe();
        mPresenter.registerRxBus();
        mPresenter.startScanMap();
    }

    private void initDialog() {

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

                                    isCompleted = true;
                                    MainApplication.iscreateMapping = true;

                                    gpsMapBean.setName(robotName);
                                    gpsMapBean.setCompletedMapUrl("/maps/now/map.jpg");
                                    // gpsMapBean.setCompletedMapUrl(UrlHelper.COMPLETE_URL);
                                    gpsMapBean.setDate( Calendar.getInstance().getTime());
                                    // 更新
                                    mPresenter.saveMap(gpsMapBean);
                                    // 只需要清除id即可
                                    mPresenter.clearId();
                                    // 完成
                                    WebSocketHelper.send(JsonCreator.mappingStatus(2,gpsMapBean.getId(),gpsMapBean.getName(),"/var/www/maps").toJSONString());

                                }
                                dialog.dismiss();
                                CreateActivity.this.finish();
                                break;
                        }

                    }
                })
                .setExpanded(false)
                .setContentWidth(RelativeLayout.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.CENTER)
                .create();

        completeDialogBuilder = new  AlertDialog.Builder(this);
    }

    /* 设置侧边栏 和监听 */
    private void initView() {


        MainApplication.iscreateMapping = false;
        controlTabs = new ArrayList<>();
        controlTabs.add(new ControlTab(R.mipmap.play,"保存"));
        controlTabs.add(new ControlTab(R.mipmap.right_blue,"完成"));
        controlTabs.add(new ControlTab(R.mipmap.cancel_,"取消"));
        adapter =  new CommonAdapter<ControlTab>(this, R.layout.control_item, controlTabs) {

            @Override
            protected void convert(ViewHolder holder, ControlTab controlTab, int position) {
                ((ImageView) holder.getView(R.id.iv)).setImageBitmap(
                        BitmapFactory.decodeResource(getResources(), controlTab.getResId()));
                holder.setText(R.id.tv,controlTab.getControllName());
                holder.setOnClickListener(R.id.iv, new OnControlClickListener(position));
            }
        };

        rvControl.setLayoutManager(new LinearLayoutManager(this));
        rvControl.setAdapter(adapter);

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
        ObjectAnimator.ofFloat(view, "translationX", 0, -rvControl.getWidth())
                .setDuration(500)
                .start();
    }

    @Override
    public void hideControlLayout(View view) {
        ObjectAnimator.ofFloat(view, "translationX", -rvControl.getWidth(), 0)
                .setDuration(500)
                .start();
    }

    @Override
    public void changeRobotPos(double x, double y) {
        Log.d(TAG, "changeRobotPos: x :" + x + "   y:" + y);
        gpsImage.setRobotPosition((float) x, (float) y);
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
        gpsImage.setLaserPoints(points);
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
    public void showCompleteDialog(String msg,int type) {

        if (type != -1){

            completeDialogBuilder.setMessage(msg)
                                .setNegativeButton("仍要退出", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        CreateActivity.this.finish();
                                    }
                                })
                                .setPositiveButton("继续完成", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
            completeDialogBuilder.create();
            completeDialogBuilder.show();
        }else {

        }
        return;
    }


    @Override
    public void onStarting() {
        mPresenter.loopSendCommandToRos();
    }

    /**
     * @param angle  角度[0,360)
     * @param length [0,R-r]
     */
    @Override
    public void change(double angle, float length) {
        velocity[0] = angle;
        velocity[1] = length;
    }

    @Override
    public void onFinish() {
        mPresenter.stopLoop();
        mPresenter.cancel(gpsMapBean);
    }

    @Override
    public void accept(@NonNull Bitmap bitmap) throws Exception {
        gpsImage.setMap(bitmap);
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

    @OnClick({R.id.iv_back, R.id.iv_right, R.id.iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

                StringBuilder msg = new StringBuilder();
                int type = -1;   // -1不显示 0 表示未保存  1表示保存但未完成
                if (!isCompleted){   // true
                    if (!isSaved){
                        type = 0;
                        msg.append("地图未保存，请确认是否退出");
                    }else {
                        type = 1;
                        msg.append("地图未完成，请确认是否退出");
                    }
                    this.showCompleteDialog(msg.toString(), type);
                }else {
                    this.finish();
                }
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
        }
    }

    /**
     *  右侧布局的点击事件  分别有保存  -- 完成  -- 取消
     */
    public class OnControlClickListener implements View.OnClickListener {

        private int position;

        public OnControlClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            // 暂停 -- 保存
            if (position == 0) {
                // 表示现在正在扫描地图
                isSaved = true;
//                mPresenter.saveMap();
                mPresenter.saveTempMap(gpsMapBean);
//                switchControllPause(isScanTasked);
            }

            /* 完成 */
            if (position == 1) {
                MainApplication.startRos = 0;
                mPresenter.finishScanMap();
            }

            /* 取消 */
            if (position == 2){
                // 删除并清除id的值
                // 删除
                MainApplication.iscreateMapping = true;
                MainApplication.startRos = 0;
                mPresenter.cancel(gpsMapBean);
                mPresenter.clearMap(gpsMapBean);
                CreateActivity.this.finish();
            }
        }
    }

    /**
     * 暂停开始的控制，和视图的切换
     * @param isScanTasked
     */
    private void switchControllPause(boolean isScanTasked) {
        // 暂停
        if (isScanTasked){
            mPresenter.stopScanMap();
            // paly ---- > 对应的应该是暂停  Q^Q^Q
            controlTabs.get(0).setResId(R.mipmap.stop);
            controlTabs.get(0).setControllName("暂停");
        // 开始
        }else {
            mPresenter.startScanMap();
            controlTabs.get(0).setResId(R.mipmap.play);
            controlTabs.get(0).setControllName("开始");
        }
        this.isScanTasked = !isScanTasked;
        adapter.notifyDataSetChanged();
    }

}
