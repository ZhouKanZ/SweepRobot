package com.gps.sweeprobot;

import android.app.Activity;
import android.os.Bundle;
import android.service.carrier.CarrierIdentifier;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gps.ros.android.RosApplication;
import com.gps.ros.android.RosService;
import com.gps.ros.android.TranslationManager;
import com.gps.ros.message.Message;
import com.gps.ros.rosbridge.operation.Advertise;
import com.gps.ros.rosbridge.operation.Publish;
import com.gps.ros.rosbridge.operation.Subscribe;
import com.gps.ros.rosbridge.rosWebsocketHelper.TopicsManager;
import com.gps.sweeprobot.utils.RosProtrocol;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/17 0017
 * @Descriptiong : xxx
 */

public class TestActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.connect)
    Button connect;
    @BindView(R.id.unconnect)
    Button unconnect;
    @BindView(R.id.rb_speed_adv)
    RadioButton rbSpeedAdv;
    @BindView(R.id.rb_position_adv)
    RadioButton rbPositionAdv;
    @BindView(R.id.rg_advertise)
    RadioGroup rgAdvertise;
    @BindView(R.id.btn_commit_adv)
    Button btnCommitAdv;
    @BindView(R.id.rb_speed_pub)
    RadioButton rbSpeedPub;
    @BindView(R.id.rb_position_pub)
    RadioButton rbPositionPub;
    @BindView(R.id.rg_publish)
    RadioGroup rgPublish;
    @BindView(R.id.btn_commit_publish)
    Button btnCommitPublish;
    @BindView(R.id.rb_speed_sub)
    RadioButton rbSpeedSub;
    @BindView(R.id.rb_position_sub)
    RadioButton rbPositionSub;
    @BindView(R.id.rg_subscribe)
    RadioGroup rgSubscribe;
    @BindView(R.id.btn_commit_sub)
    Button btnCommitSub;
    @BindView(R.id.screen)
    TextView screen;

    Advertise advertise = new Advertise();
    Publish publish = new Publish();
    Subscribe subscribe = new Subscribe();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        rgAdvertise.setOnCheckedChangeListener(this);
        rgPublish.setOnCheckedChangeListener(this);
        rgSubscribe.setOnCheckedChangeListener(this);

        advertise.id = "zktest";
        advertise.topic = RosProtrocol.Speed.TOPIC;
        advertise.type = RosProtrocol.Speed.TYPE;

        publish.id = "zktest";
        publish.topic = RosProtrocol.Speed.TOPIC;
        publish.msg = new Message() {
            @Override
            public void print() {
                super.print();
            }
        };

        subscribe.topic = RosProtrocol.Speed.TOPIC;
        subscribe.type = RosProtrocol.Speed.TYPE;
    }

    @OnClick({R.id.connect, R.id.unconnect, R.id.btn_commit_adv, R.id.btn_commit_publish, R.id.btn_commit_sub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.connect:

                Bundle bundle = new Bundle();
                bundle.putString(RosService.ROS_URI_KEY, "ws://192.168.2.142:9090");
                ((MainApplication) getApplication()).startService(getApplication(), bundle, RosService.class);
                break;
            case R.id.unconnect:
                ((MainApplication) getApplication()).stopService();
                break;
            case R.id.btn_commit_adv:
                TranslationManager.advertiseTopic(advertise);
                break;
            case R.id.btn_commit_publish:
                TranslationManager.publish(publish);
                break;
            case R.id.btn_commit_sub:
                TranslationManager.subscribe(subscribe);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

        switch (i) {
            case R.id.rb_position_adv:
                advertise.topic = RosProtrocol.Position.TOPIC;
                advertise.type  = RosProtrocol.Position.TYPE;
                break;
            case R.id.rb_position_pub:
                publish.topic = RosProtrocol.Position.TOPIC;
                break;
            case R.id.rb_position_sub:
                subscribe.topic = RosProtrocol.Position.TOPIC;
                subscribe.type  = RosProtrocol.Position.TYPE;
                break;
            case R.id.rb_speed_adv:
                advertise.topic = RosProtrocol.Speed.TOPIC;
                advertise.type  = RosProtrocol.Speed.TYPE;
                break;
            case R.id.rb_speed_pub:
                publish.topic = RosProtrocol.Speed.TOPIC;
                break;
            case R.id.rb_speed_sub:
                subscribe.topic = RosProtrocol.Speed.TOPIC;
                subscribe.type  = RosProtrocol.Speed.TYPE;
                break;
        }
    }
}
