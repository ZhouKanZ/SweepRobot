package com.gps.sweeprobot.utils;

import com.alibaba.fastjson.JSON;
import com.gps.sweeprobot.bean.NavPose;
import com.gps.sweeprobot.bean.NavPoseWrap;
import com.gps.sweeprobot.bean.ObstaclePose;
import com.gps.sweeprobot.bean.ObstaclePoseWrap;
import com.gps.sweeprobot.bean.WallPoseWrap;
import com.gps.sweeprobot.bean.WebSocketResult;
import com.gps.sweeprobot.database.MyPointF;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.database.VirtualObstacleBean;
import com.gps.sweeprobot.http.WebSocketHelper;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by WangJun on 2017/7/28
 * 发送数据给服务器
 */

public class CommunicationUtil {

    /**
     * 发送标记点信息给ros
     * @param pointBean
     * @param type
     */
    public static void sendPoint2Ros(PointBean pointBean,int type){

        NavPose navPose = new NavPose();
        navPose.setMapid(pointBean.getMapId());
        navPose.setMapname(pointBean.getMapName());
        navPose.setId(pointBean.getId());
        navPose.setName(pointBean.getPointName());
        navPose.setType(type);

        NavPose.Pose pose = new NavPose.Pose();
        NavPose.Pose.Point point = new NavPose.Pose.Point();
        NavPose.Pose.Quaternion quaternion = new NavPose.Pose.Quaternion();
        point.setX(pointBean.getX());
        point.setY(pointBean.getY());

        pose.setPosition(point);
        pose.setOrientation(quaternion);
        navPose.setWorldposition(pose);

        NavPoseWrap navPose_Wrap_ = new NavPoseWrap();
        navPose_Wrap_.setNav_pose(navPose);

        WebSocketResult<NavPoseWrap> webSocketResult = new WebSocketResult<>();
        webSocketResult.setOp(RosProtrocol.Point.OPERATE);
        webSocketResult.setService(RosProtrocol.Point.SERVICE);
        webSocketResult.setArgs(navPose_Wrap_);

        Flowable.just(JSON.toJSONString(webSocketResult))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {

                        // 向服务器发送数据
                        WebSocketHelper.send(s);
                        LogManager.i(s);
                    }
                });
    }

    /**
     * 发送虚拟墙数据给ros
     * @param bean
     * @param type
     */
    public static void sendObstacle2Ros(VirtualObstacleBean bean,int type){

        ObstaclePose mObstacle = new ObstaclePose();
        mObstacle.setMapid(bean.getMapId());
        mObstacle.setMapname(bean.getMapName());
        mObstacle.setId(bean.getId());
        mObstacle.setName(bean.getName());
        mObstacle.setType(type);

        final List<NavPose.Pose> poses = new ArrayList<>();

        Flowable.fromIterable(bean.getMyPointFs())
                .subscribe(new Consumer<MyPointF>() {
                    @Override
                    public void accept(@NonNull MyPointF myPointF) throws Exception {

                        NavPose.Pose.Point point = new NavPose.Pose.Point();
                        point.setX(myPointF.getX());
                        point.setY(myPointF.getY());

                        NavPose.Pose.Quaternion quaternion = new NavPose.Pose.Quaternion();

                        NavPose.Pose pose = new NavPose.Pose();

                        pose.setPosition(point);
                        pose.setOrientation(quaternion);

                        poses.add(pose);
                    }
                });

        mObstacle.setWorldposition(poses);

        ObstaclePoseWrap wrap = new ObstaclePoseWrap();
        wrap.setWall_pose(mObstacle);

        WebSocketResult<ObstaclePoseWrap> webSocketResult = new WebSocketResult<>();
        webSocketResult.setOp(RosProtrocol.Obstacle.OPERATE);
        webSocketResult.setService(RosProtrocol.Obstacle.SERVICE);
        webSocketResult.setArgs(wrap);

        WebSocketHelper.send(JSON.toJSONString(webSocketResult));
        LogManager.i(JSON.toJSONString(webSocketResult));
    }

    /**
     * 发送虚拟墙集合的id信息以及对应的mapid、mapname
     * @param mapid
     */
    public static void sendObstacleDatas2Ros(int mapid){

        Flowable.fromArray(DataSupport.where("mapId = ?", String.valueOf(mapid)).find(VirtualObstacleBean.class))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<List<VirtualObstacleBean>>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull List<VirtualObstacleBean> obstacleList) throws Exception {

                        if(obstacleList == null || obstacleList.size() == 0){
                            return;
                        }

                        List<Integer> mapIdList = new ArrayList<>();
                        for (VirtualObstacleBean obstacleBean : obstacleList) {
                            mapIdList.add(obstacleBean.getId());
                        }

                        WallPoseWrap wallPoseWrap = new WallPoseWrap();
                        WallPoseWrap.WallPose edit_wall = new WallPoseWrap.WallPose();

                        edit_wall.setMapid(obstacleList.get(0).getMapId());
                        edit_wall.setMapname(obstacleList.get(0).getMapName());
                        edit_wall.setWall_id(mapIdList);

                        wallPoseWrap.setEdit_wall(edit_wall);

                        WebSocketResult<WallPoseWrap> result = new WebSocketResult<>();
                        result.setOp(RosProtrocol.Wall.OPERATE);
                        result.setService(RosProtrocol.Wall.SERVICE);
                        result.setArgs(wallPoseWrap);

                        WebSocketHelper.send(JSON.toJSONString(result));
                        LogManager.i(JSON.toJSONString(result));
                    }
                });
    }

}
