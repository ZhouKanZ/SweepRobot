package com.gps.sweeprobot.model.mapmanager.presenterimpl;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.bean.IAction;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.database.VirtualObstacleBean;
import com.gps.sweeprobot.model.mapmanager.adaper.item.ActionItem;
import com.gps.sweeprobot.model.mapmanager.bean.MapListBean;
import com.gps.sweeprobot.model.mapmanager.contract.MapEditContract;
import com.gps.sweeprobot.model.mapmanager.model.ActionModel;
import com.gps.sweeprobot.model.mapmanager.model.MapListModel;
import com.gps.sweeprobot.model.mapmanager.presenter.MapEditPresenter;
import com.gps.sweeprobot.model.view.adapter.CommonRcvAdapter;
import com.gps.sweeprobot.model.view.adapter.item.AdapterItem;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.utils.LogManager;
import com.gps.sweeprobot.utils.ScreenUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Create by WangJun on 2017/7/19
 */

public class MapEditPresenterImpl extends MapEditPresenter {

    private static String MAP_KEY = "mapEdit";
    private static String ACTION_KEY = "action_key";

    private List<IAction> actionList;
    private List<MapListBean> mapList;
    private ActionItem.ActionOnItemListener listener;
    private MapEditContract.view mapEditView;

    //动作状态
    public static final int OPERATE_ADD_POINT = 0;
    public static final int OPERATE_SUB_POINT = 1;
    public static final int OPERATE_ADD_PATH = 2;
    public static final int OPERATE_SUB_PATH = 3;
    public static final int OPERATE_ADD_OBSTACLE = 4;
    public static final int OPERATE_SUB_OBSTACLE = 5;
    public static final int ACTION_RESET = 100;

    private int mAction;
    private boolean isAdd;
    private boolean isPoint;

    //dialog状态
    public static final int INPUT_DIALOG_ADD = 0;
    public static final int INPUT_DIALOG_RENAME = 1;
    public static final int INPUT_DIALOG_OBSTACLE = 2;
    public static final int DIALOG_STATUS_RESET = 101;

    private int dialogStatus;
    private int position;
    private int itemIconResid;

    //标记点数据，从数据库获取
    private List<PointBean> pointsList;
    //虚拟墙数据
    private List<VirtualObstacleBean> obstacleBeanList;

    public MapEditPresenterImpl(ActionItem.ActionOnItemListener listener, MapEditContract.view mapEditView) {

        mapList = new ArrayList<>();
        actionList = new ArrayList<>();
        pointsList = new ArrayList<>();
        obstacleBeanList = new ArrayList<>();

        this.listener = listener;
        this.mapEditView = mapEditView;
    }

    /**
     * 将数据库中的point数据展示出来，在刚进入活动的时候
     *
     * @param data
     */
    public void getPointDataFromSQL(List<PointBean> data) {

        pointsList.clear();
        pointsList.addAll(data);
        for (PointBean pointBean : pointsList) {

            mapEditView.addPoint(pointBean.getX(), pointBean.getY(), pointBean.getPointName());
        }

    }

    public void getObstacleDataFormSQL(List<VirtualObstacleBean> data) {

        obstacleBeanList.clear();
        obstacleBeanList.addAll(data);
        for (VirtualObstacleBean bean : obstacleBeanList) {

            mapEditView.addObstacle(bean.getMyPointFs(), bean.getName());
            LogManager.i("obstacle point list size============" + bean.getMyPointFs().size());
        }
    }


    @Override
    public HashMap<String, IModel> getiModelMap() {

        return loadModelMap(new MapListModel(), new ActionModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {

        HashMap<String, IModel> map = new HashMap<>();
        map.put(MAP_KEY, models[0]);
        map.put(ACTION_KEY, models[1]);
        return map;
    }

    @Override
    public RecyclerView.Adapter initAdapter() {

        return new CommonRcvAdapter<IAction>(actionList) {

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                ActionItem actionItem = new ActionItem(listener);
//                actionItem.setIcon(itemIconResid);
                return actionItem;
            }
        };
    }

    /**
     * 设置传入adapter中的数据
     */
    @Override
    public void setData() {

        //获取地图img
        setMapData();
        //从数据库获取标记点,虚拟墙数据
        setActionData();

    }

    private void setMapData() {

        Observable.just(getiModelMap().get(MAP_KEY))
                .subscribe(new Consumer<IModel>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull IModel iModel) throws Exception {

                        ((MapListModel) iModel).downMapImg(new MapListModel.InfoHint() {
                            @Override
                            public void successInfo(Drawable drawable) {
                                mapEditView.getData(drawable, null);
                            }

                            @Override
                            public void successInfo(Bitmap map) {
                                mapEditView.getData(map);
                            }

                            @Override
                            public void successMapListData(List<MapListBean> data) {
                                mapList.clear();
                                mapList.addAll(data);
                            }

                            @Override
                            public void failInfo(Throwable e) {

                                LogManager.e(e.getMessage());
                            }
                        });
                    }
                });
    }

    public void setActionData() {

        //获取action的数据
        Observable.just(((ActionModel) getiModelMap().get(ACTION_KEY)))
                .subscribe(new Consumer<ActionModel>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull final ActionModel actionModel) throws Exception {

                        //获取标记点数据
                        actionModel.getActionData(new ActionModel.InfoMessager() {
                            @Override
                            public void successInfo(List<PointBean> data) {

                                LogManager.i("sql size ====" + data.size());
                                getPointDataFromSQL(data);

                            }

                            @Override
                            public void failInfo(Throwable e) {
                                LogManager.e(e.getMessage());
                            }
                        });

                        //获取虚拟墙数据
                        actionModel.getObstacleData(new ActionModel.ObstacleInfo() {
                            @Override
                            public void successInfo(List<VirtualObstacleBean> data) {

                                LogManager.i("obstacle data size=========" + data.size());
                                getObstacleDataFormSQL(data);
                            }

                            @Override
                            public void errorInfo(Throwable e) {

                            }
                        });
                    }
                });
    }


    @Override
    public void addViewOnClick() {

        isAdd = true;
        mapEditView.inflateActionView();
    }

    @Override
    public void subViewOnClick() {

        isAdd = false;
        mapEditView.inflateActionView();
    }

    @Override
    public void pointActionOnClick() {

        mAction = isAdd ? OPERATE_ADD_POINT : OPERATE_SUB_POINT;
        if (isAdd) dialogStatus = INPUT_DIALOG_ADD;
        isPoint = true;
        actionList.clear();
        actionList.addAll(pointsList);
        itemIconResid = R.mipmap.point;
        mapEditView.setActionRecyclerView();

    }

    @Override
    public void obstacleActionOnClick() {

        mAction = isAdd ? OPERATE_ADD_OBSTACLE : OPERATE_SUB_OBSTACLE;
        if (isAdd) dialogStatus = INPUT_DIALOG_OBSTACLE;
        isPoint = false;
        actionList.clear();
        actionList.addAll(obstacleBeanList);
        itemIconResid = R.mipmap.wall;
        mapEditView.setActionRecyclerView();
    }

    @Override
    public void positionViewOnClick() {
        //在添加虚拟墙的状态下
        if (mAction == OPERATE_ADD_OBSTACLE){

            setObstacleRect();
        }
    }

    @Override
    public void commitViewOnClick() {

        switch (mAction) {

            case OPERATE_ADD_POINT:
                mapEditView.createInputNameDialog(getIView().getString(R.string.dialog_add_point_title));
                break;
            case OPERATE_ADD_PATH:
                break;
            case OPERATE_ADD_OBSTACLE:
                mapEditView.createInputNameDialog(getIView().getString(R.string.dialog_Obstacle));
                mAction = ACTION_RESET;
                break;
            case OPERATE_SUB_POINT:
                break;
            case OPERATE_SUB_PATH:
                break;
            case OPERATE_SUB_OBSTACLE:
                break;
        }

    }

    /**
     * dialog的sure点击事件
     *
     * @param name
     */
    @Override
    public void addPointViewOnClick(String name) {

        if (dialogStatus == INPUT_DIALOG_ADD) {

            operateAddPoint(name);

        } else if (dialogStatus == INPUT_DIALOG_RENAME) {
            rename(name, position);

        } else if (dialogStatus == INPUT_DIALOG_OBSTACLE) {

            addObstacleData(name);
        }
    }

    /**
     * 添加虚拟墙数据
     * @param name
     */
    private void addObstacleData(String name){

        VirtualObstacleBean obstacleBean = mapEditView.setObstacleName(name);
        obstacleBeanList.add(obstacleBean);
        mapEditView.updateAdapter(obstacleBeanList);
        ((ActionModel) getiModelMap().get(ACTION_KEY)).setObstacle2Ros(obstacleBean);
    }

    /**
     * 点击删除
     *
     * @param name
     * @param position
     */
    @Override
    public void itemOnClick(String name, int position) {

        mapEditView.setRemoveAnimation();
        remove(name, position);

    }

    /**
     * 长按重命名
     *
     * @param position
     */
    @Override
    public void itemLongClick(int position) {

        dialogStatus = INPUT_DIALOG_RENAME;
        this.position = position;
        mapEditView.createInputNameDialog(getIView().getString(R.string.dialog_point_rename));
    }

    @Override
    public void onDestroy() {
        pointsList.clear();
        actionList.clear();
        mapList.clear();
    }

    /**
     * 点击添加虚拟墙view
     */
    @Override
    public void obstacleViewOnClick() {
        mapEditView.createInputNameDialog(getIView().getString(R.string.dialog_Obstacle));
    }

    /**
     * 添加标记点
     */
    private void operateAddPoint(String pointName) {

        if (pointName != null) {

            //添加新加入数据库的pointBean
            PointBean pointBean = mapEditView.addPointWrapper(getFlagXY().x, getFlagXY().y, pointName);
            if (pointBean != null) {

                pointsList.add(pointBean);
            }
            //刷新adapter
            mapEditView.updateAdapter(pointsList);
        } else {
            mapEditView.showToast();
        }
    }

    /**
     * 获得屏幕的坐标
     *
     * @return
     */
    private PointF getFlagXY() {

        return new PointF(ScreenUtils.getFlagX(MainApplication.getContext()),
                ScreenUtils.getFlagY(MainApplication.getContext()) - iView.getToolBarHeight());
    }

    /**
     * 设置虚拟墙的顶点
     */
    private void setObstacleRect() {

        mapEditView.setObstacleRect(getFlagXY());
    }

    /**
     * 重命名
     *
     * @param name
     * @param position
     */
    public void rename(String name, int position) {

        if (isPoint){
            pointRename(name,position);
        }else {
            obstacleRename(name,position);
        }
    }

    private void pointRename(String name,int position){

        //更新数据库
        PointBean pointBean = DataSupport.find(PointBean.class, pointsList.get(position).getId());
        pointBean.setPointName(name);
        pointBean.save();

        //更新view
        mapEditView.updateName(name, position, MapEditContract.TYPE_POINT);
        //刷新adapter
        pointsList.set(position, pointBean);
        mapEditView.updateAdapter(pointsList);
        //通知服务器
    }

    private void obstacleRename(String name,int position){

        //update sql
        VirtualObstacleBean ob = obstacleBeanList.get(position);
        ob.setName(name);
        ob.setMyPointFs(ob.getMyPointFs());
        ob.save();

        //update view
        mapEditView.updateName(name,position,MapEditContract.TYPE_OBSTACLE);
        //notify adapter
        obstacleBeanList.set(position,ob);
        mapEditView.updateAdapter(obstacleBeanList);
        //notify server

    }

    private void remove(String name, int position) {

        if (isPoint) {

            removePoint(name, position);
        } else {
            removeObstacle(name, position);
        }

    }

    /**
     * 移除标记点
     *
     * @param pointName
     * @param position
     */
    private void removePoint(String pointName, int position) {

        //移除标记点view
        mapEditView.removePoint(pointName, position);
        //数据库删除数据
        DataSupport.delete(PointBean.class, pointsList.get(position).getId());
        //通知服务器
//        CommunicationUtil.sendPointData2Server(pointsList.get(position));
        //list删除数据
        pointsList.remove(position);
        //通知adapter刷新
        mapEditView.updateAdapter(pointsList);
    }

    /**
     * 移除虚拟墙
     *
     * @param name
     * @param position
     */
    private void removeObstacle(String name, int position) {

        mapEditView.removeObstacle(name, position);
        DataSupport.delete(VirtualObstacleBean.class, obstacleBeanList.get(position).getId());
        obstacleBeanList.remove(position);
        mapEditView.updateAdapter(obstacleBeanList);
        //通知服务器
    }
}
