package com.gps.sweeprobot.model.taskqueue.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.taskqueue.bean.TaskTab;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/18 0018
 * @Descriptiong : xxx
 */

public class TaskTypeActivity extends BaseActivity {

    private static final String TAG = "TaskTypeActivity";
    public static final String ID = "tasktypeactivity_id";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_task_list)
    RecyclerView rvTaskList;

    List<TaskTab>  tabs ;

    int mapId;

    @Override
    protected TextView getTitleTextView() {
        return title;
    }

    @Override
    protected String getTitleText() {
        return "任务";
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

        mapId = getIntent().getBundleExtra(TaskTypeActivity.class.getSimpleName()).getInt(ID);

        tabs = new ArrayList<>();
        tabs.add(new TaskTab("导航任务"));
        tabs.add(new TaskTab("路径任务"));
        tabs.add(new TaskTab("清洁任务"));

        setLeftVisiable(true);

        rvTaskList.setLayoutManager(new LinearLayoutManager(this));
        rvTaskList.setAdapter(new CommonAdapter<TaskTab>(this,R.layout.item_task_tab,tabs) {
            @Override
            protected void convert(ViewHolder holder, TaskTab taskTab, final int position) {
                TaskTab tab = tabs.get(position);
                holder.setText(R.id.tv_tab_name,tab.getTab_name());
                holder.setOnClickListener(R.id.layouot_task_tab, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        if (position == 0){
                            // 导航任务
                            bundle.putString("type","navi");
                            TaskDetailActivity.startSelf(TaskTypeActivity.this,TaskDetailActivity.class,bundle);
                        }else if (position == 1){
                            // 路径任务
                            bundle.putString("type","path");
                            TaskDetailActivity.startSelf(TaskTypeActivity.this,TaskDetailActivity.class,bundle);
                        }else {
                            // 清洁任务
                            bundle.putString("type","clear");
                            TaskDetailActivity.startSelf(TaskTypeActivity.this,TaskDetailActivity.class,bundle);
                        }
                    }
                });

                if (position == 2){
                    holder.setVisible(R.id.line,false);
                }

            }
        });
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_type;
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        this.finish();
    }
}
