package com.gps.sweeprobot.model.createmap.view.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/13 0013
 * @Descriptiong : 创建地图页面
 */

public class CreateActivity extends BaseActivity<CreateMapPresenter,CreateMapContract.View>
        implements CreateMapContract.View,
        RockerView.OnAngleChangeListener {

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
        return new CreateMapPresenter(this);
    }

    @Override
    protected void initData() {

        /* 隐藏状态栏 */
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.map_test);
        gpsMapview.setImageView(bitmap);
        setLeftVisiable(true);

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


    /**********/

    @Override
    public void clickScanControl(boolean isScan) {
    }

    @Override
    public void cancelScanControl() {
    }

    /**
     * offloat() 操作的单位是px
     * 每次动画产生的位移，不是基于现有的位置，而是基于view原本的位置
     *
     * @param view
     */
    @Override
    public void showControlLayout(View view) {

        ObjectAnimator.ofFloat(view, "translationX", 0, -DensityUtil.dip2px(100))
                .setDuration(500)
                .start();

    }

    @Override
    public void hideControlLayout(View view) {

        ObjectAnimator.ofFloat(view, "translationX", -DensityUtil.dip2px(100), 0)
                .setDuration(500)
                .start();

    }

    @Override
    public void showGetRobotsAnimation() {

        ObjectAnimator animator = ObjectAnimator
                .ofFloat(ivAnim, "rotation", 0f, 360f * 3)
                .setDuration(5000);

        animator.start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                CreateActivity.this.hideGetRobotsAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    // 设置不可见
    @Override
    public void hideGetRobotsAnimation() {
        ivAnim.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void angle(double angle) {

    }

    @Override
    public void onFinish() {

    }
}
