package com.gps.sweeprobot.model.taskqueue.view.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.widget.GpsImage;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/24 0024
 * @Descriptiong : xxx
 */

public class TaskDetailActivity extends BaseActivity {

    public static final String TASK_ID_KEY = "task_id_key";

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
    @BindView(R.id.layout_gesture)
    GestureFrameLayout layoutGesture;

    @Override
    protected TextView getTitleTextView() {
        return title;
    }

    @Override
    protected String getTitleText() {
        return "任务详情";
    }

    @Override
    public ImageView getLeftImageView() {
        return ivBack;
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        setLeftVisiable(true);
        layoutGesture
                .getController()
                .getSettings()
                .setMaxZoom(5)
                .setRotationEnabled(true);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_detail;
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        this.finish();
    }
}
