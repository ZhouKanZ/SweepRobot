package com.gps.sweeprobot.utils;

import com.alibaba.fastjson.JSON;
import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.bean.Test;
import com.gps.sweeprobot.bean.WebSocketResult;
import com.gps.sweeprobot.database.PointBean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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
        webSocketResult.setMsg(test);
        webSocketResult.setOp(RosProtrocol.Point.OPERATE);
        webSocketResult.setTopic(RosProtrocol.Point.TOPIC);

        Observable.just(JSON.toJSONString(webSocketResult))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {

                        LogManager.i(MainApplication.getContext().getThreadName()+"\njson========="+s);
//                        RosService.getRosBridgeClient().send(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

//                        LogManager.e(e.getMessage());
                        throw new RuntimeException(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
