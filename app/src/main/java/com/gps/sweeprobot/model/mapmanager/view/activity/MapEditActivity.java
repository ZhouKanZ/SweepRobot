package com.gps.sweeprobot.model.mapmanager.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.database.MyPointF;
import com.gps.sweeprobot.model.main.bean.ToolbarOptions;
import com.gps.sweeprobot.model.mapmanager.adaper.item.ActionItem;
import com.gps.sweeprobot.model.mapmanager.bean.MapListBean;
import com.gps.sweeprobot.model.mapmanager.contract.MapEditContract;
import com.gps.sweeprobot.model.mapmanager.presenter.MapEditPresenter;
import com.gps.sweeprobot.model.mapmanager.presenterimpl.MapEditPresenterImpl;
import com.gps.sweeprobot.model.view.adapter.CommonRcvAdapter;
import com.gps.sweeprobot.mvp.IView;
import com.gps.sweeprobot.utils.LogManager;
import com.gps.sweeprobot.utils.LogUtils;
import com.gps.sweeprobot.utils.ToastManager;
import com.gps.sweeprobot.widget.GpsImageView;
import com.gps.sweeprobot.widget.VirtualObstacleView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 地图编辑
 * Create by WangJun on 2017/7/19
 */

public class MapEditActivity extends BaseActivity<MapEditPresenter, IView> implements
        ActionItem.ActionOnItemListener,
        MapEditContract.view,
        GpsImageView.SavePointDataListener,
        VirtualObstacleView.SaveObstacleListener {


    @BindView(R.id.activity_map_edit_giv)
    GpsImageView gpsImageView;

    @BindView(R.id.activity_map_edit_rv)
    RecyclerView actionRecyclerView;

    @BindViews({R.id.activity_map_add, R.id.activity_map_sub, R.id.activity_map_info,
            R.id.activity_map_flag, R.id.activity_map_position, R.id.activity_map_commit})
    List<ImageView> nameViews;

    //动作列表的dialog
    private AlertDialog actionDialog;

    private TextInputEditText inputName;

    //命名的dialog
    private DialogPlus dialogPlus;

    @Override
    protected TextView getTitleTextView() {
        return null;
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Override
    protected MapEditPresenter loadPresenter() {
        return new MapEditPresenterImpl(this, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {

        ToolbarOptions options = new ToolbarOptions();
        options.titleId = R.string.activity_edit_map_title;
        setToolBar(R.id.toolbar, options);

        Bundle bundle = getIntent().getBundleExtra(this.getClass().getSimpleName());
        mPresenter.setBundle(bundle);
        mPresenter.setData();

    }

    @Override
    protected void initListener() {

        for (ImageView imageView : nameViews) {
            imageView.setOnClickListener(this);
        }

        gpsImageView.setOnSaveListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_edit;
    }

    private int getDialogLayoutId() {
        return R.layout.dialog_action_list;
    }

    @Override
    protected void otherViewClick(final View view) {

        switch (view.getId()) {
            /**
             * 侧边按钮
             */
            case R.id.activity_map_add:

                mPresenter.addViewOnClick();
                break;

            case R.id.activity_map_sub:

                mPresenter.subViewOnClick();
                break;

            case R.id.activity_map_info:
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        // get the center for the clipping circle
                        int cx = (view.getLeft() + view.getRight()) / 2;
                        int cy = (view.getTop() + view.getBottom()) / 2;

// get the initial radius for the clipping circle
                        int initialRadius = view.getWidth();

// create the animation (the final radius is zero)
                        Animator anim =
                                ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

// make the view invisible when the animation is done
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                view.setVisibility(View.INVISIBLE);
                            }
                        });

                        anim.start();
                    }
                });
//                view.setVisibility(View.INVISIBLE);
                break;

            case R.id.activity_map_position:

                mPresenter.positionViewOnClick();
                break;

            case R.id.activity_map_commit:

                mPresenter.commitViewOnClick();
                break;

            /**
             * dialog
             */
            case R.id.dialog_action_point:

                actionDialog.dismiss();
                setViewVisibility();
                mPresenter.pointActionOnClick();
                break;

            case R.id.dialog_action_path:
                break;

            case R.id.dialog_action_obstacle:

                actionDialog.dismiss();
                nameViews.get(4).setVisibility(View.VISIBLE);
                setViewVisibility();
                mPresenter.obstacleActionOnClick();
                break;

            /**
             * input name dialog
             */
            case R.id.dialog_add_point_cancel:

                dismissInputNameDialog();
                gpsImageView.cancelAddObstacleView();
                break;

            case R.id.dialog_add_point_sure:

                mPresenter.addPointViewOnClick(inputName.getText().toString().trim());
                dismissInputNameDialog();
                break;
        }
    }

    private void setViewVisibility() {
        nameViews.get(3).setVisibility(View.VISIBLE);
        nameViews.get(5).setVisibility(View.VISIBLE);
    }

    @Override
    public void getData(Drawable mapDrawable, List<MapListBean> data) {

        LogUtils.d("123", "=====================");
        gpsImageView.setImageView(mapDrawable);
    }

    /**
     * 获得地图的bitmap
     *
     * @param map
     */
    @Override
    public void getData(Bitmap map) {

        LogManager.i("getData===========");
        gpsImageView.setImageView(map);

    }

    /**
     * 添加新的标记点，通知adapter刷新
     *
     * @param data
     */
    @Override
    public void updateAdapter(List data) {

        ((CommonRcvAdapter) actionRecyclerView.getAdapter()).setData(data);
    }

    /**
     * 加载dialog的contentView
     */
    @Override
    public void inflateActionView() {
        if (actionDialog == null) {

            addAction(LayoutInflater.from(this).inflate(getDialogLayoutId(), null));
        } else {
            actionDialog.show();
        }
    }

    /**
     * 创建标记点，路径，虚拟墙的选项dialog
     *
     * @param contentView
     */
    private void addAction(View contentView) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(contentView);
        actionDialog = builder.create();
        actionDialog.getWindow().setLayout(1200, 800);
        actionDialog.show();

        initDialogView(contentView, R.id.dialog_action_point, this);
        initDialogView(contentView, R.id.dialog_action_path, this);
        initDialogView(contentView, R.id.dialog_action_obstacle, this);

    }

    /**
     * 设置dialog view 的点击监听
     *
     * @param contentView
     * @param viewId
     * @param listener
     */
    private void initDialogView(View contentView, int viewId, final View.OnClickListener listener) {

        Observable.just(findView(contentView, viewId))
                .subscribe(new Consumer<View>() {
                    @Override
                    public void accept(@NonNull View view) throws Exception {

                        if (view.getId() == R.id.dialog_add_point_name) {
                            inputName = (TextInputEditText) view;
                        }
                        view.setOnClickListener(listener);
                    }
                });
    }

    /**
     * 添加标记点
     *
     * @param x         相对于图片的像素点位置x
     * @param y         相对于图片的像素点位置y
     * @param pointName
     */
    @Override
    public void addPoint(float x, float y, String pointName) {

        gpsImageView.addPoint(x, y, pointName);
    }

    /**
     * 添加标记点外层方法
     *
     * @param screenX 相对于屏幕的坐标点
     * @param screenY 相对于屏幕的坐标点
     * @param name
     * @return
     */
    @Override
    public void addPointWrapper(float screenX, float screenY, String name) {

        gpsImageView.addPointWrapper(screenX, screenY, name);
    }

    @Override
    public void updateName(String newName, int position, int type) {

        gpsImageView.updateName(newName, position, type);
    }

    @Override
    public void removePoint(String pointName, int index) {

        gpsImageView.removePoint(pointName, index);
    }

    @Override
    public void showToast() {

        ToastManager.showShort(getString(R.string.input_name_must_not_null));
    }

    /**
     * 创建输入名字的dialog
     *
     * @param titleText title
     */
    @Override
    public void createInputNameDialog(String titleText) {

        dialogPlus = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.dialog_add_point))
                .setExpanded(true,900)
                .setGravity(Gravity.CENTER)
                .setHeader(R.layout.dialog_add_point_head)
                .setFooter(R.layout.dialog_add_point_foot)
                .setContentWidth(1500)
                .setInAnimation(R.anim.fade_in_center)
                .setOutAnimation(R.anim.fade_out_center)
                .create();

        showInputNameDialog();
        TextView title = findView(dialogPlus.getHeaderView(), R.id.dialog_add_point_title);
        title.setText(titleText);
        initDialogView(dialogPlus.getHolderView(), R.id.dialog_add_point_name, this);
        initDialogView(dialogPlus.getFooterView(), R.id.dialog_add_point_cancel, this);
        initDialogView(dialogPlus.getFooterView(), R.id.dialog_add_point_sure, this);

    }

    private void showInputNameDialog() {
        dialogPlus.show();
    }

    private void dismissInputNameDialog() {
        dialogPlus.dismiss();
    }

    /**
     * 设置虚拟墙多边形顶点
     */
    @Override
    public void setObstacleRect(PointF pointF) {
        gpsImageView.setObstacleRect(pointF);
    }

    /**
     * 设置虚拟墙名字
     *
     * @param name
     */
    @Override
    public void setObstacleName(String name) {
        gpsImageView.setObstacleName(name, this);
    }

    /**
     * 添加虚拟墙
     *
     * @param data
     * @param name
     */
    @Override
    public void addObstacle(List<MyPointF> data, String name) {
        gpsImageView.addObstacleView(data, name);
    }

    @Override
    public void removeObstacle(String name, int position) {
        gpsImageView.removeObstacleView(name, position);
    }

    @Override
    public void startAddWall() {
        gpsImageView.startAddWall();
    }

    /**
     * 设置左边recycler的item
     */
    @Override
    public void setActionRecyclerView() {

        Animation inAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        actionRecyclerView.setHasFixedSize(true);
        actionRecyclerView.setVisibility(View.VISIBLE);
        actionRecyclerView.setAnimation(inAnim);
        actionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        actionRecyclerView.setAdapter(mPresenter.initAdapter());

    }



    /**
     * 左边recycler item 的点击事件
     *
     * @param view
     * @param name
     */
    @Override
    public void onItemClick(View view, String name, int position) {

        mPresenter.itemOnClick(name, position);
    }

    @Override
    public void setRemoveAnimation() {

    }

    /**
     * 长按重命名
     *
     * @param item
     * @param position
     */
    @Override
    public void onItemLongClick(View item, int position) {

        mPresenter.itemLongClick(position);
    }

    private <T extends View> T findView(View rootView, int viewId) {

        if (rootView == null) {
            return null;
        }
        return (T) rootView.findViewById(viewId);
    }

    @Override
    protected void onDestroy() {

        nameViews = null;
        actionDialog = null;
        dialogPlus = null;
        mPresenter.onDestroy();
        super.onDestroy();
    }

    /**
     * 保存标记点的监听
     *
     * @param pointF
     * @param name
     */
    @Override
    public void onSavePoint(PointF pointF, String name) {
        mPresenter.savePoint(pointF, name);
    }

    /**
     * 保存虚拟墙的监听
     *
     * @param myPointFs
     * @param name
     */
    @Override
    public void onSaveObstacle(List<MyPointF> myPointFs, String name) {
        mPresenter.saveObstacle(myPointFs, name);
    }
}
