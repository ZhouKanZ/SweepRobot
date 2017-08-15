package com.gps.sweeprobot.model.taskqueue.view.activity;

import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.taskqueue.bean.TaskPoint;
import com.gps.sweeprobot.widget.GpsImageView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/18 0018
 * @Descriptiong : 任务详情页面，获取启动得bundle getType 来指明activity的类型
 */

public class TaskDetailActivity extends BaseActivity {

    public static final String TYPE_KEY = "type_key";
    // 标记点
    public static final String TYPE_MARKPOINT = "type_markpoint";
    // 路径
    public static final String TYPE_PATH = "type_path";
    // 障碍物
    public static final String TYPE_OBSTACLE = "type_obstacle";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;

    private List<TaskPoint> points;

    private CommonAdapter adapter;

    @Override
    protected TextView getTitleTextView() {
        return title;
    }

    @Override
    protected String getTitleText() {
        return "标记点任务";
    }

    @Override
    public ImageView getLeftImageView() {
        return ivBack;
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    protected void initData() {
        setLeftVisiable(true);
        points = new ArrayList<>();
        points.add(new TaskPoint(100f,200f,"第一个点"));
        points.add(new TaskPoint(200f,200f,"第二个点"));
        points.add(new TaskPoint(300f,200f,"第三个点"));
        points.add(new TaskPoint(200f,300f,"第四个点"));

    }

    @Override
    protected void initListener() {}

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
