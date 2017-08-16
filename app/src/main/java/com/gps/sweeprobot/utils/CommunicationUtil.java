package com.gps.sweeprobot.utils;

import com.alibaba.fastjson.JSON;
import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.bean.NavPose;
import com.gps.sweeprobot.bean.NavPoseWrap;
import com.gps.sweeprobot.bean.ObstaclePose;
import com.gps.sweeprobot.bean.ObstaclePoseWrap;
import com.gps.sweeprobot.bean.Test;
import com.gps.sweeprobot.bean.WebSocketResult;
import com.gps.sweeprobot.database.MyPointF;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.database.VirtualObstacleBean;
import com.gps.sweeprobot.http.WebSocketHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by WangJun on 2017/7/28
 * 发送数据给服务器
 */

public class CommunicationUtil {

    public static void sendPointData2Server(PointBean bean) {

        Test test = new Test();

        Test.HeaderBean header = new Test.HeaderBean();

        header.setFrame_id(RosProtrocol.Point.FRAME_ID);
        test.setHeader(header);
        //
        Test.GoalBean goal = new Test.GoalBean();

        Test.GoalBean.
                TargetPoseBean target_pose = new Test.GoalBean.TargetPoseBean();

        Test.GoalBean.
                TargetPoseBean.
                HeaderBeanX header1 = new Test.GoalBean.TargetPoseBean.HeaderBeanX();

        header1.setFrame_id(RosProtrocol.Point.FRAME_ID);

        target_pose.setHeader(header1);

        Test.
                GoalBean.
                TargetPoseBean.
                PoseBean pose = new Test.GoalBean.TargetPoseBean.PoseBean();
        Test.
                GoalBean.
                TargetPoseBean.
                PoseBean.
                PositionBean positionBean = new Test.GoalBean.TargetPoseBean.PoseBean.PositionBean();

        positionBean.setX(0);
        positionBean.setY(0);
        positionBean.setZ(0);

        Test.
                GoalBean.
                TargetPoseBean.
                PoseBean.
                OrientationBean orientation = new Test.GoalBean.TargetPoseBean.PoseBean.OrientationBean();

        orientation.setX(0);
        orientation.setY(0);
        orientation.setY(0);
        orientation.setZ(1);

        pose.setOrientation(orientation);
        pose.setPosition(positionBean);
        target_pose.setPose(pose);
        goal.setTarget_pose(target_pose);
        test.setGoal(goal);

        WebSocketResult<Test> webSocketResult = new WebSocketResult<>();
        webSocketResult.setArgs(test);
//        webSocketResult.op = RosProtrocol.Point.OPERATE;
//        webSocketResult.setTopic(RosProtrocol.Point.TOPIC);

        Observable.just(webSocketResult)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<WebSocketResult<Test>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull WebSocketResult<Test> testWebSocketResult) {

                        LogManager.i(MainApplication.getContext().getThreadName());
//                        MainApplication.getContext().getRosBridgeClient().send(testWebSocketResult);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static void sendPoint2Ros(PointBean pointBean){

        NavPose navPose = new NavPose();
        navPose.setName(pointBean.getPointName());
        navPose.setMapname(pointBean.getMapName());
        navPose.setId(pointBean.getId());
        navPose.setMapid(pointBean.getMapId());

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
//        webSocketResult.setTopic(RosProtrocol.Point.TOPIC);
//        webSocketResult.setType(RosProtrocol.Point.TYPE);
        webSocketResult.setArgs(navPose_Wrap_);

        // 向服务器发送数据
//        ROSBridgeWebSocketClient.create(Constant.JiaoJian).send(JSON.toJSONString(webSocketResult));
        WebSocketHelper.send(JSON.toJSONString(webSocketResult));
        LogManager.i(JSON.toJSONString(webSocketResult));

 /*       Flowable.just(JSON.toJSONString(webSocketResult))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {

                        // 向服务器发送数据
                        ROSBridgeWebSocketClient.create(Constant.JiaoJian).send(JSON.toJSONString(webSocketResult));
                        LogManager.i(MainApplication.getContext().getThreadName()+"send point to ros");
                    }
                });*/
    }

    public static void sendObstacle2Ros(VirtualObstacleBean bean){

        ObstaclePose mObstacle = new ObstaclePose();
        mObstacle.setName(bean.getName());
        mObstacle.setId(bean.getId());

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

}
