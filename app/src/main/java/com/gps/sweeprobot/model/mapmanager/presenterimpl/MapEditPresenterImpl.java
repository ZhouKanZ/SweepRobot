package com.gps.sweeprobot.model.mapmanager.presenterimpl;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.bean.IAction;
import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.database.MyPointF;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.database.VirtualObstacleBean;
import com.gps.sweeprobot.http.Constant;
import com.gps.sweeprobot.model.mapmanager.adaper.item.ActionItem;
import com.gps.sweeprobot.model.mapmanager.contract.MapEditContract;
import com.gps.sweeprobot.model.mapmanager.contract.MapManagerContract;
import com.gps.sweeprobot.model.mapmanager.model.ActionModel;
import com.gps.sweeprobot.model.mapmanager.model.MapInfoModel;
import com.gps.sweeprobot.model.mapmanager.presenter.MapEditPresenter;
import com.gps.sweeprobot.model.view.adapter.CommonRcvAdapter;
import com.gps.sweeprobot.model.view.adapter.item.AdapterItem;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.utils.CommunicationUtil;
import com.gps.sweeprobot.utils.LogManager;
import com.gps.sweeprobot.utils.ScreenUtils;
import com.gps.sweeprobot.utils.ToastManager;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import static com.gps.sweeprobot.model.mapmanager.presenterimpl.MapEditPresenterImpl.ActionEnum.ACTION_RESET;
import static com.gps.sweeprobot.model.mapmanager.presenterimpl.MapEditPresenterImpl.ActionEnum.OPERATE_ADD_OBSTACLE;
import static com.gps.sweeprobot.model.mapmanager.presenterimpl.MapEditPresenterImpl.ActionEnum.OPERATE_ADD_POINT;
import static com.gps.sweeprobot.model.mapmanager.presenterimpl.MapEditPresenterImpl.ActionEnum.OPERATE_SUB_OBSTACLE;
import static com.gps.sweeprobot.model.mapmanager.presenterimpl.MapEditPresenterImpl.ActionEnum.OPERATE_SUB_POINT;
import static com.gps.sweeprobot.model.mapmanager.presenterimpl.MapEditPresenterImpl.DialogStatus.INPUT_DIALOG_ADD;
import static com.gps.sweeprobot.model.mapmanager.presenterimpl.MapEditPresenterImpl.DialogStatus.INPUT_DIALOG_OBSTACLE;
import static com.gps.sweeprobot.model.mapmanager.presenterimpl.MapEditPresenterImpl.DialogStatus.INPUT_DIALOG_RENAME;

/**
 * Create by WangJun on 2017/7/19
 */

public class MapEditPresenterImpl extends MapEditPresenter {

    private static final String MAP_KEY = "mapEdit";
    private static final String ACTION_KEY = "action_key";

    private List<IAction> actionList;
    private List<GpsMapBean> mapList;
    private ActionItem.ActionOnItemListener listener;
    private MapEditContract.view mapEditView;

/*    //动作状态
    public static final int OPERATE_ADD_POINT = 0;
    public static final int OPERATE_SUB_POINT = 1;
    public static final int OPERATE_ADD_PATH = 2;
    public static final int OPERATE_SUB_PATH = 3;
    public static final int OPERATE_ADD_OBSTACLE = 4;
    public static final int OPERATE_SUB_OBSTACLE = 5;
    public static final int ACTION_RESET = 100;*/


    private ActionEnum mAction;
    private boolean isAdd;
    private boolean isPoint;

 /*   //dialog状态
    public static final int INPUT_DIALOG_ADD = 0;
    public static final int INPUT_DIALOG_RENAME = 1;
    public static final int INPUT_DIALOG_OBSTACLE = 2;
    public static final int DIALOG_STATUS_RESET = 101;*/

    private DialogStatus dialogStatus;
    private int position;

    //标记点数据，从数据库获取
    private List<PointBean> pointsList;
    //虚拟墙数据
    private List<VirtualObstacleBean> obstacleBeanList;
    //地图数据model
    private MapInfoModel mapInfoModel;

    private int mapid;

    private Map<String,ActionItem> itemMap = new HashMap<>();
    private String mapName;

    //动作状态
    enum ActionEnum{
        OPERATE_ADD_POINT,
        OPERATE_SUB_POINT,
        OPERATE_ADD_PATH,
        OPERATE_SUB_PATH,
        OPERATE_ADD_OBSTACLE,
        OPERATE_SUB_OBSTACLE,
        ACTION_RESET
    }

    //dialog状态
    enum DialogStatus{
        INPUT_DIALOG_ADD,
        INPUT_DIALOG_RENAME,
        INPUT_DIALOG_OBSTACLE
    }

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
    private void getPointDataFromSQL(List<PointBean> data) {

        pointsList.clear();
        pointsList.addAll(data);
        for (PointBean pointBean : pointsList) {

            mapEditView.addPoint(pointBean.getX(), pointBean.getY(), pointBean.getPointName());
        }

    }

    private void getObstacleDataFormSQL(List<VirtualObstacleBean> data) {

        obstacleBeanList.clear();
        obstacleBeanList.addAll(data);
        for (VirtualObstacleBean bean : obstacleBeanList) {

            mapEditView.addObstacle(bean.getMyPointFs(), bean.getName());
            LogManager.i("obstacle point list size============" + bean.getMyPointFs().size());
        }
    }


    @Override
    public HashMap<String, IModel> getiModelMap() {

        return loadModelMap(new MapInfoModel(), new ActionModel());
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
                itemMap.put("123",actionItem);
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
        setMapData(mapid);
        //从数据库获取标记点,虚拟墙数据
        setActionData();

    }

    private void setMapData(final int mapId) {

        mapInfoModel = (MapInfoModel) getiModelMap().get(MAP_KEY);

        Observable.just(mapInfoModel)
                .subscribe(new Consumer<IModel>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull IModel iModel) throws Exception {

                        ((MapInfoModel) iModel).downMapImg(mapId,new MapInfoModel.InfoHint() {

                            @Override
                            public void successInfo(Bitmap map) {
                                mapEditView.getData(map);
                            }


                            @Override
                            public void failInfo(Throwable e) {

                                LogManager.e(e.getMessage());
                            }
                        });
                    }
                });
    }

    private void setActionData() {

        //获取action的数据
        Observable.just(((ActionModel) getiModelMap().get(ACTION_KEY)))
                .subscribe(new Consumer<ActionModel>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull final ActionModel actionModel) throws Exception {

                        //获取标记点数据
                        actionModel.getActionData(mapid,new ActionModel.InfoMessager() {
                            @Override
                            public void successInfo(List<PointBean> data) {

                                LogManager.i("sql size ====" + data.size());
                                getPointDataFromSQL(data);

                            }

                            @Override
                            public void failInfo(Throwable e) {
                                ToastManager.showShort("point"+e.getMessage());
                            }
                        });

                        //获取虚拟墙数据
                        actionModel.getObstacleData(mapid,new ActionModel.ObstacleInfo() {
                            @Override
                            public void successInfo(List<VirtualObstacleBean> data) {

                                LogManager.i("obstacle data size=========" + data.size());
                                getObstacleDataFormSQL(data);
                            }

                            @Override
                            public void errorInfo(Throwable e) {

                                ToastManager.showShort("obstacle"+e.getMessage());
                            }
                        });
                    }
                });
    }


    /**
     * 添加按钮监听
     */
    @Override
    public void addViewOnClick() {

        isAdd = true;
        mapEditView.inflateActionView();
    }

    /**
     * 删除监听
     */
    @Override
    public void subViewOnClick() {

        isAdd = false;
        mapEditView.inflateActionView();
    }

    /**
     * 标记点选项点击监听
     */
    @Override
    public void pointActionOnClick() {

        mAction = isAdd ? OPERATE_ADD_POINT : OPERATE_SUB_POINT;
        if (isAdd) dialogStatus = INPUT_DIALOG_ADD;
        isPoint = true;
        actionList.clear();
        actionList.addAll(pointsList);
//        itemIconResid = point;
        mapEditView.setActionRecyclerView();

    }

    /**
     * 虚拟墙选项点击监听
     */
    @Override
    public void obstacleActionOnClick() {

        if (isAdd) {
            mapEditView.startAddWall();
            dialogStatus = INPUT_DIALOG_OBSTACLE;
        }
        mAction = isAdd ? OPERATE_ADD_OBSTACLE : OPERATE_SUB_OBSTACLE;
        isPoint = false;
        actionList.clear();
        actionList.addAll(obstacleBeanList);
        int itemIconResid = R.mipmap.wall;
        mapEditView.setActionRecyclerView();
    }

    /**
     * 设置虚拟墙多边形顶点
     */
    @Override
    public void positionViewOnClick() {
        //在添加虚拟墙的状态下
        if (mAction == OPERATE_ADD_OBSTACLE){

            setObstacleRect();
        }
    }

    /**
     * 确认弹出dialog
     */
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

        mapEditView.setObstacleName(name);

    }

    /**
     * 点击删除
     *
     * @param name
     * @param position
     */
    @Override
    public void itemOnClick(String name, int position) {

        itemMap.get("123").startAnim();
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
        CommunicationUtil.sendObstacleDatas2Ros(mapid);
    }

    /**
     * 点击添加虚拟墙view
     */
    @Override
    public void obstacleViewOnClick() {
        mapEditView.createInputNameDialog(getIView().getString(R.string.dialog_Obstacle));
    }

    /**
     * 获取mapid
     * @param bundle
     */
    @Override
    public void setBundle(Bundle bundle) {
        this.mapid = bundle.getInt(MapManagerContract.MAP_ID_KEY);
        mapName = bundle.getString(MapManagerContract.MAP_NAME_KEY);

        LogManager.i("set bundle mapid======="+mapid+"/////////mapname========"+mapName);

    }

    @Override
    public void savePoint(PointF pointF, String pointName) {

        mapInfoModel.savePoint(pointF, pointName, mapid,mapName, new MapInfoModel.PointSaveListener() {
            @Override
            public void onPointSave(final PointBean pointBean) {

                getIView().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pointsList.add(pointBean);
                        //刷新adapter
                        mapEditView.updateAdapter(pointsList);
                    }
                });
            }
        });

//        itemMap.get("123").startAnim();

    }


    @Override
    public void saveObstacle(List<MyPointF> myPointFs, String name) {

        mapInfoModel.saveObstacle(myPointFs,name,mapid,mapName);

        VirtualObstacleBean virtualObstacleBean = new VirtualObstacleBean();
        virtualObstacleBean.setName(name);
        virtualObstacleBean.setMyPointFs(myPointFs);

        //刷新adapter
        obstacleBeanList.add(virtualObstacleBean);
        mapEditView.updateAdapter(obstacleBeanList);
//        itemMap.get("123").startAnim();

    }

    /**
     * 添加标记点
     */
    private void operateAddPoint(String pointName) {

        if (pointName != null) {

            //添加新加入数据库的pointBean
            mapEditView.addPointWrapper(getFlagXY().x, getFlagXY().y, pointName);

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
     * @param name 新名字
     * @param position item列表的index
     */
    private void rename(String name, int position) {

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
        CommunicationUtil.sendPoint2Ros(pointBean, Constant.UPDATE);
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
        CommunicationUtil.sendObstacle2Ros(ob,Constant.UPDATE);
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
        CommunicationUtil.sendPoint2Ros(pointsList.get(position),Constant.DELETE);
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
        //通知服务器
        CommunicationUtil.sendObstacle2Ros(obstacleBeanList.get(position),Constant.DELETE);
        obstacleBeanList.remove(position);
        mapEditView.updateAdapter(obstacleBeanList);

    }
}
