package com.gps.sweeprobot.model.taskqueue.view.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.ros.rosbridge.implementation.JSON;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.bean.NavPose;
import com.gps.sweeprobot.database.Task;
import com.gps.sweeprobot.model.taskqueue.adapter.TaskAdapter;
import com.gps.sweeprobot.model.taskqueue.contract.TaskQueueContract;
import com.gps.sweeprobot.model.taskqueue.presenter.TaskQuenePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * @Author : zhoukan
 * @CreateDate : 2017/7/13 0013
 * @Descriptiong : xxx
 */
public class TaskQueueActivity extends BaseActivity<TaskQuenePresenter, TaskQueueContract.View>
        implements TaskQueueContract.View,
        SwipeRefreshLayout.OnRefreshListener
        , TaskAdapter.OnTaskClickListener {

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
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;

    private List<Task> tasks;
    private TaskAdapter adapter;
    private AlertDialog.Builder builder;
    // dialog 产生的位置 默认为 -1 表示未选择
    private int position = -1;

    @Override
    protected TextView getTitleTextView() {
        return title;
    }

    public ImageView getIvRight() {
        return ivRight;
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

        initView();
        createData();

    }

    private void createData() {

        tasks = new ArrayList<>();
        adapter = new TaskAdapter(tasks, this);
        adapter.setOnClickListener(this);

        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(adapter);
        mPresenter.findAllTask();
    }

    /**
     * 初始化view
     */
    private void initView() {

        setLeftVisiable(true);
        setRightVisiable(true);

        Bitmap addBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.add_right);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageBitmap(addBitmap);

        swipeLayout.setOnRefreshListener(this);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("注意:")
                .setMessage("删除后任务将无法回复，请谨慎删除")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (position != -1){
                            adapter.removeItem(position);
                        }
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
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

    @OnClick({R.id.iv_back, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.iv_right:
                showCreateNewTaskPopup();
                break;
        }
    }

    @Override
    public void refresh() {
        swipeLayout.setRefreshing(true);
    }

    @Override
    public void hideRefresh() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void showCreateNewTaskPopup() {
        AddTaskActivity.startSelf(this, AddTaskActivity.class, null);
    }

    @Override
    public void hidePopup() {
    }

    @Override
    public void notifyData(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mPresenter.findAllTask();
    }

    @Override
    public void onTaskClick(int position, View view) {

        int id = view.getId();

        switch (id) {
            case R.id.btn_:
                // add页面
                AddTaskActivity.startSelf(this, AddTaskActivity.class, null);
                break;
            case R.id.layout_task:
                // 任务详情
                // 进入任务详情页面 -- TaskDetailActivity taskId

                Bundle bundle = new Bundle();
                bundle.putInt(TaskDetailActivity.TASK_ID_KEY,tasks.get(position).getId());

                TaskDetailActivity.startSelf(this, TaskDetailActivity.class, bundle);
                break;
            case R.id.tv_deleted:
                // 删除
                this.position = position;
                builder.show();
                break;
            case R.id.btn_check:
                // 查看当前状况

                Bundle b = new Bundle();
                b.putInt(TaskExecuteActivity.MAP_ID,tasks.get(position).getMapId());
                b.putInt(TaskExecuteActivity.TASK_ID,tasks.get(position).getId());
//                tasks.get(position).getMapId()
                Log.d("send", "onTaskClick: " + tasks.get(position).getMapId());
                TaskExecuteActivity.startSelf(this, TaskExecuteActivity.class, b);
                break;
            case R.id.btn_execute:
                // 执行
                Task task = tasks.get(position);
                Log.d("xxx", "onTaskClick: " + com.alibaba.fastjson.JSON.toJSONString(task));
                mPresenter.executeTask(task);
                break;
        }

    }
}
