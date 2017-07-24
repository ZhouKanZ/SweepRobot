package com.gps.sweeprobot.model.mapmanager.view.activity;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.model.mapmanager.bean.MapListBean;
import com.gps.sweeprobot.model.mapmanager.presenter.MapEditPresenter;
import com.gps.sweeprobot.model.mapmanager.presenterimpl.MapEditPresenterImpl;
import com.gps.sweeprobot.utils.LogManager;
import com.gps.sweeprobot.utils.LogUtils;
import com.gps.sweeprobot.utils.ScreenUtils;
import com.gps.sweeprobot.utils.ToastManager;
import com.gps.sweeprobot.widget.GpsImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;

/**
 * Create by WangJun on 2017/7/19
 */

public class MapEditActivity extends BaseActivity<MapEditPresenter> {


    @BindView(R.id.activity_map_edit_giv)
    GpsImageView gpsImageView;

    @BindViews({R.id.activity_map_add,R.id.activity_map_sub,R.id.activity_map_info,R.id.activity_map_flag,R.id.activity_map_commit})
    List<ImageView> nameViews;

 /*   @BindViews({R.id.dialog_action_point,R.id.dialog_action_path,R.id.dialog_action_obstacle})
    List<TextView> dialogViews;*/
    TextView actionPoint,actionPath,actionObstacle;

    AlertDialog actionDialog;

    public static final int OPERATE_ADD_POINT = 0;
    public static final int OPERATE_SUB_POINT = 1;
    public static final int OPERATE_ADD_PATH = 2;
    public static final int OPERATE_SUB_PATH = 3;
    public static final int OPERATE_ADD_OBSTACLE = 4;
    public static final int OPERATE_SUB_OBSTACLE = 5;

    private int mAction;
    private boolean isAdd;

    @Override
    protected MapEditPresenter loadPresenter() {
        return new MapEditPresenterImpl();
    }

    @Override
    protected void initData() {

        mPresenter.setData();
    }

    @Override
    protected void initListener() {

        nameViews.get(0).setOnClickListener(this);
        nameViews.get(1).setOnClickListener(this);
        nameViews.get(2).setOnClickListener(this);
        nameViews.get(4).setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_edit;
    }

    private int getDialogLayoutId(){ return R.layout.dialog_action_list;}

    @Override
    protected void otherViewClick(View view) {

        switch (view.getId()) {

            /**
             * 侧边按钮
             */
            case R.id.activity_map_add:
                isAdd = true;
                if (actionDialog == null){
                    addAction(LayoutInflater.from(this).inflate(getDialogLayoutId(),null));
                }else {
                    actionDialog.show();
                }
                break;

            case R.id.activity_map_sub:
                isAdd = false;
                if (actionDialog == null){
                    addAction(LayoutInflater.from(this).inflate(getDialogLayoutId(),null));
                }else {
                    actionDialog.show();
                }
                break;

            case R.id.activity_map_info:

                break;

            case R.id.activity_map_commit:
//                ToastManager.showShort(this,"commit click");
                executeAction();
                break;

            /**
             * dialog
             */
            case R.id.dialog_action_point:
//                ToastManager.showShort(this,"point click");
                actionDialog.dismiss();
                nameViews.get(3).setVisibility(View.VISIBLE);
                nameViews.get(4).setVisibility(View.VISIBLE);
                if (isAdd){
                    mAction = OPERATE_ADD_POINT;
                }else {
                    mAction = OPERATE_SUB_POINT;
                }
                break;

            case R.id.dialog_action_path:
//                ToastManager.showShort(this,"path click");

                if (isAdd){
                    mAction = OPERATE_ADD_PATH;
                }else {
                    mAction = OPERATE_SUB_PATH;
                }
                break;

            case R.id.dialog_action_obstacle:
                ToastManager.showShort(this,"obstacle click");

                if (isAdd){
                    mAction = OPERATE_ADD_OBSTACLE;
                }else {
                    mAction = OPERATE_SUB_OBSTACLE;
                }
                break;
        }
    }

    public void getData(Drawable mapDrawable, List<MapListBean> data){

        LogUtils.d("123","=====================");
        gpsImageView.setImageView(mapDrawable);
    }

    public void getData(Bitmap map){

        LogManager.i("getData===========");
        gpsImageView.setImageView(map);
    }

    public void addAction(View contentView){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(contentView);
        actionDialog = builder.create();
        actionDialog.getWindow().setLayout(1500,800);
        actionDialog.show();

        initDialogView(contentView);
        initDialogListener();

    }

    public void initDialogView(View contentView){

        actionPoint = findView(contentView,R.id.dialog_action_point);
        actionPath = findView(contentView,R.id.dialog_action_path);
        actionObstacle = findView(contentView,R.id.dialog_action_obstacle);
    }

    public void initDialogListener(){

        actionPoint.setOnClickListener(this);
        actionPath.setOnClickListener(this);
        actionObstacle.setOnClickListener(this);
    }

    private void executeAction(){

        LogManager.i("maction ========"+mAction);
        switch (mAction) {

            case OPERATE_ADD_POINT:

                gpsImageView.addPointWrapper(ScreenUtils.getScreenWidth(this)/2,
                        ScreenUtils.getScreenHeight(this)/2,"1234");

                break;
            case OPERATE_ADD_PATH:
                break;
            case OPERATE_ADD_OBSTACLE:
                break;
            case OPERATE_SUB_POINT:
                gpsImageView.removePoint("1234");
                break;
            case OPERATE_SUB_PATH:
                break;
            case OPERATE_SUB_OBSTACLE:
                break;
        }
    }

    public  <T extends View>T findView(View rootView,int viewId){

        if (rootView == null){
            return null;
        }
        return (T) rootView.findViewById(viewId);

    }
}
