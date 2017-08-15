package com.gps.sweeprobot.model.taskqueue.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.http.Constant;
import com.gps.sweeprobot.model.taskqueue.contract.TaskQueueContract;
import com.gps.sweeprobot.model.taskqueue.presenter.TaskQuenePresenter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/13 0013
 * @Descriptiong : xxx
 */
public class TaskQueueActivity extends BaseActivity<TaskQuenePresenter, TaskQueueContract.View> {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_tasks)
    RecyclerView rvTasks;

    private List<GpsMapBean> tasks;

    @Override
    protected TextView getTitleTextView() {
        return title;
    }

    @Override
    protected String getTitleText() {
        return "任务队列";
    }

    @Override
    protected TaskQuenePresenter loadPresenter() {
        return new TaskQuenePresenter();
    }


    @Override
    protected void initData() {

        tasks = DataSupport.findAll(GpsMapBean.class);
        setLeftVisiable(true);

        rvTasks.setLayoutManager(new LinearLayoutManager(this));


        rvTasks.setAdapter(new CommonAdapter<GpsMapBean>(this,R.layout.item_task,tasks) {

            @Override
            protected void convert(ViewHolder holder, final GpsMapBean gpsMap, int position) {
                GpsMapBean gps = tasks.get(position);
                holder.setText(R.id.tv_title,gps.getName());
                holder.setText(R.id.tv_date,gps.getDate());

                Glide.with(mCtz)
                        .load(Constant.DOMAIN + gps.getCompletedMapUrl())
                        .into((ImageView) holder.getView(R.id.iv_map));

                holder.setOnClickListener(R.id.layout_task_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Bundle bundle = new Bundle();
                        bundle.putInt(TaskTypeActivity.ID,gpsMap.getId());
                        TaskTypeActivity.startSelf(TaskQueueActivity.this,TaskTypeActivity.class,bundle);

                    }
                });
            }
        });

    }

    @Override
    protected void initListener() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_taskqueue;
    }

    @Override
    protected void otherViewClick(View view) {}

    @Override
    public ImageView getLeftImageView() {
        return ivBack;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        this.finish();
    }
}
