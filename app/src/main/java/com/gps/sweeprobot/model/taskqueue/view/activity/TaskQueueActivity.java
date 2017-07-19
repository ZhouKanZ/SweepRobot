package com.gps.sweeprobot.model.taskqueue.view.activity;

import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.bean.GpsMap;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/13 0013
 * @Descriptiong : xxx
 */

public class TaskQueueActivity extends BaseActivity {

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

    private List<GpsMap> tasks;

    @Override
    protected TextView getTitleTextView() {
        return title;
    }

    @Override
    protected String getTitleText() {
        return "任务队列";
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

        tasks = new ArrayList<>();
        tasks.add(new GpsMap(1,R.mipmap.map_test,"测试地图1","2017-7-18",1));
        tasks.add(new GpsMap(2,R.mipmap.map_test,"测试地图2","2017-7-18",2));
        tasks.add(new GpsMap(3,R.mipmap.map_test,"测试地图3","2017-7-18",3));
        tasks.add(new GpsMap(4,R.mipmap.map_test,"测试地图4","2017-7-18",4));

        setLeftVisiable(true);

        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(new CommonAdapter<GpsMap>(this,R.layout.item_task,tasks) {

            @Override
            protected void convert(ViewHolder holder, GpsMap gpsMap, int position) {
                GpsMap gps = tasks.get(position);
                holder.setText(R.id.tv_title,gps.getMapName());
                holder.setText(R.id.tv_date,gps.getCreateDate());
                holder.setImageBitmap(R.id.iv_map, BitmapFactory.decodeResource(TaskQueueActivity.this.getResources(),gps.getMapResId()));
                holder.setOnClickListener(R.id.layout_task_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TaskTypeActivity.startSelf(TaskQueueActivity.this,TaskTypeActivity.class,null);
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
    protected void otherViewClick(View view) {

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
