package com.gps.sweeprobot.model.taskqueue.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.http.Http;
import com.gps.sweeprobot.url.UrlHelper;
import com.gps.sweeprobot.widget.GpsImage;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/18 0018
 * @Descriptiong : 任务详情页面，获取启动得bundle getType 来指明activity的类型
 */

public class TaskDetailActivity extends BaseActivity {

    private static final String TAG = "TaskDetailActivity";

    public static final String TYPE_KEY = "type_key";
    // 标记点
    public static final String TYPE_MARKPOINT = "type_markpoint";
    // 路径
    public static final String TYPE_PATH = "type_path";
    // 障碍物
    public static final String TYPE_OBSTACLE = "type_obstacle";

    public static final String ID = "mapid_key";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.gps_image)
    GpsImage gpsImage;
    @BindView(R.id.layout_gesture)
    GestureFrameLayout layoutGesture;
    @BindView(R.id.rv_task)
    RecyclerView rvTask;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv)
    ImageView iv;

    private CommonAdapter adapter;
    /* 传过来的id,用于后面的查询 */
    private int mapId = -1;

    private List<PointBean> pointBeans = new ArrayList<>();

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

        initView();

        /* 是否能准确的获取到intent,在横竖屏切换的情况下 */
        Intent intent = getIntent();
        if (intent != null) {
            mapId = intent.getBundleExtra(TaskDetailActivity.class.getSimpleName()).getInt(ID);
            Log.d(TAG, "initData: " + mapId);
        }
        queryTaskData();

    }

    private void initView() {

        setLeftVisiable(true);

        layoutGesture
                .getController()
                .getSettings()
                .setMaxZoom(10)
                .setRotationEnabled(true);

        adapter = new CommonAdapter<PointBean>(this, R.layout.item_task_detail, pointBeans) {

            @Override
            protected void convert(ViewHolder holder, PointBean pointBean, int position) {
                holder.setText(R.id.tv_task, pointBean.getPointName());
                holder.setOnClickListener(R.id.tv_task, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 执行任务

                    }
                });
            }
        };

        rvTask.setLayoutManager(new LinearLayoutManager(this));
        rvTask.setAdapter(adapter);

        Http.getHttpService()
                .downImage()
                .map(new Function<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull ResponseBody responseBody) throws Exception {
                        Bitmap map = BitmapFactory.decodeStream(responseBody.byteStream());
                        return map;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(@NonNull Bitmap bitmap) throws Exception {
                        gpsImage.setMap(bitmap);
                    }
                });


    }

    /**
     * 查询数据库数据
     */
    private void queryTaskData() {

        /**
         *  异步查询数据
         */
        if (mapId != -1)
            Observable.just(mapId)
                    .map(new Function<Integer, List<PointBean>>() {
                        @Override
                        public List<PointBean> apply(@NonNull Integer integer) throws Exception {
                            List<PointBean> pointbeans = DataSupport.where("mapId = ?", mapId + "").find(PointBean.class);
                            return pointbeans;
                        }
                    })
                    .subscribeOn(Schedulers.io())  // 1
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<PointBean>>() {
                        @Override
                        public void accept(@NonNull List<PointBean> pointBeen) throws Exception {
                            pointBeans.clear();
                            pointBeans.addAll(pointBeen);
                            adapter.notifyDataSetChanged();
                            if (null != pointBeans && pointBeans.size() > 0) {
                                gpsImage.setPointBeanList(pointBeans);
                            }
                        }
                    });
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
