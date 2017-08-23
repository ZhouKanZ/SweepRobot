package com.gps.sweeprobot.model.taskqueue.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
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
        SwipeRefreshLayout.OnRefreshListener {

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
    private RecyclerView.Adapter adapter;

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
        AddTaskActivity.startSelf(this,AddTaskActivity.class,null);
    }

    @Override
    public void hidePopup() {}

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
}
