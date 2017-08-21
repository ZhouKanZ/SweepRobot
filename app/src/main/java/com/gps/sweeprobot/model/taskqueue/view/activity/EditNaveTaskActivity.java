package com.gps.sweeprobot.model.taskqueue.view.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.model.taskqueue.adapter.PointAdapter;
import com.gps.sweeprobot.model.taskqueue.contract.EditNaveTaskContract;
import com.gps.sweeprobot.model.taskqueue.presenter.EditNaveTaskPresenter;
import com.gps.sweeprobot.widget.GpsImage;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/18 0018
 * @Descriptiong : xxx
 */

public class EditNaveTaskActivity extends BaseActivity<EditNaveTaskPresenter,EditNaveTaskContract.View>
        implements EditNaveTaskContract.View
        ,PointAdapter.OnItemClickListener
{

    public static final String MAP_ID_KEY = "map_id_key";
    public static final String TYPE_ID = "type_id";
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
    @BindView(R.id.gesture_layout)
    GestureFrameLayout gestureLayout;
    @BindView(R.id.rv_candidate_pose)
    RecyclerView rvCandidatePose;
    @BindView(R.id.rv_seleted_pose)
    RecyclerView rvSeletedPose;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.rl_pose_edit)
    RelativeLayout rlPoseEdit;

    private int mapId = -1;
    private int typeId = -1;

    private boolean isEditLayoutShow = false;
    private GpsMapBean gpsMapBean;
    private PointAdapter candidateAdapter,selectedAdapter;
    private List<PointBean> candidatePoints,selectedPoints;

    @Override
    protected TextView getTitleTextView() {
        return title;
    }

    @Override
    protected String getTitleText() {
        return "编辑任务";
    }

    @Override
    protected EditNaveTaskPresenter loadPresenter() {
        return new EditNaveTaskPresenter();
    }

    @Override
    protected void initData() {

        gestureLayout
                .getController()
                .getSettings()
                .setMaxZoom(10f)
                .setRotationEnabled(true);

        candidatePoints = new ArrayList<>();
        selectedPoints = new ArrayList<>();

        candidateAdapter = new PointAdapter(candidatePoints,this);
        selectedAdapter = new PointAdapter(selectedPoints,this);

        candidateAdapter.setOnItemClickListener(this);
        selectedAdapter.setOnItemClickListener(this);

        rvCandidatePose.setLayoutManager(new LinearLayoutManager(this));
        rvSeletedPose.setLayoutManager(new LinearLayoutManager(this));

        rvCandidatePose.setAdapter(candidateAdapter);
        rvSeletedPose.setAdapter(selectedAdapter);

        /* xxxxxxxxxxx */

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(EditNaveTaskActivity.class.getSimpleName());
            mapId = bundle.getInt(MAP_ID_KEY);
            typeId = bundle.getInt(TYPE_ID);
        }

        if (mapId != -1) {
            gpsMapBean = DataSupport.find(GpsMapBean.class, mapId);
            mPresenter.initData(mapId);
        }



    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editnavetask;
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @OnClick({R.id.iv_back, R.id.iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.iv:
                if (!isEditLayoutShow){
                    showPoseEditLayout();
                }else {
                    hidePoseEditLayout();
                }
                isEditLayoutShow = !isEditLayoutShow;
                break;
        }
    }

    @Override
    public void notifyCandidateAdapter(List<PointBean> pointBeens) {
        candidatePoints.clear();
        candidatePoints.addAll(pointBeens);
        candidateAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifySelectedAdapter(List<PointBean> pointBeens) {
        selectedPoints.clear();
        selectedPoints.addAll(pointBeens);
        selectedAdapter.notifyDataSetChanged();
    }

    @Override
    public void addNavePose(PointBean point) {

    }

    @Override
    public void removeNavePose(PointBean point) {

    }

    @Override
    public void showPoseEditLayout() {
        ObjectAnimator.ofFloat(rlPoseEdit, "translationX", 0, -rvCandidatePose.getWidth()*2)
                .setDuration(500)
                .start();
    }

    @Override
    public void hidePoseEditLayout() {
        ObjectAnimator.ofFloat(rlPoseEdit, "translationX", -rvCandidatePose.getWidth()*2, 0)
                .setDuration(500)
                .start();
    }

    @Override
    public void onAddClick(View view, int position) {

        selectedPoints.add(candidatePoints.get(position));
        selectedAdapter.notifyDataSetChanged();

        candidatePoints.remove(position);
        candidateAdapter.notifyItemRemoved(position);

    }

    @Override
    public void onSubClick(View view, int position) {

        selectedPoints.remove(position);
        selectedAdapter.notifyDataSetChanged();

    }
}
