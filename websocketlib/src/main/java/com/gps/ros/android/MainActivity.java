package com.gps.ros.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.gps.ros.R;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private WebSocketClient ws;
    private static final String TAG = "MainActivity";

    private TextView tv;
    private ImageView iv;

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildConnect();

        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
    }

    public void connectSocket(View view) {

        if (ws != null && ws.isConnecting()) {
            ws.close();
        }
        buildConnect();
        ws.connect();
    }

    public void unconnectSocket(View view) {
        ws.close();
    }

    public void accept(View view) {

        Glide.get(MainActivity.this).clearMemory();

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10; i++) {

                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(MainActivity.this)
                                    .load("http://192.168.2.121:8080/" + (finalI + 1) + ".jpg")
                                    .into(iv);
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }

                }
            }

        }).start();
    }

    /**
     * 绑定 service
     *
     * @param view
     */
    public void bindService(View view) {
        Log.d(TAG, "bindService: ");

        Bundle bundle = new Bundle();
        bundle.putString(RosService.ROS_URI_KEY,"ws://192.168.2.142:9090");
        ((RosApplication)getApplication()).startService(getApplicationContext(),bundle,RosService.class);

    }

    public void subscribe(View view) {
        if (ws != null && ws.isOpen()) {
            JSONObject json1 = new JSONObject();
            json1.put("op", "subscribe");
            json1.put("id", "fred1");
            json1.put("topic", "map");
            json1.put("type", "nav_msgs/OccupancyGrid");
            ws.send(json1.toJSONString());
        } else {
            Toast.makeText(MainActivity.this, "链接为开启，请线连接", Toast.LENGTH_LONG).show();
        }
    }

    public void decoder(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = CoderHelper.generateImage(CoderHelper.str);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();

    }

    /**
     * 创建
     *
     * @param view
     */
    public void send(View view) {

        if (ws != null && ws.isOpen()) {
            JSONObject json1 = new JSONObject();
            json1.put("op", "advertise");
            json1.put("id", "fred1");
            json1.put("topic", "map");
            json1.put("type", "nav_msgs/OccupancyGrid");

            ws.send(json1.toJSONString());
        } else {
            Toast.makeText(MainActivity.this, "链接为开启，请线连接", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 发布话题
     */
    public void publish(View view) {

        if (ws != null && ws.isOpen()) {
            JSONObject cood1 = new JSONObject();
            cood1.put("x", 2.0);
            cood1.put("y", 0.0);
            cood1.put("z", 2.0);

            JSONObject cood2 = new JSONObject();
            cood2.put("x", 2.0);
            cood2.put("y", 0.0);
            cood2.put("z", 2.0);

            JSONObject s = new JSONObject();
            s.put("linear", cood1);
            s.put("angular", cood2);

            JSONObject json = new JSONObject();
            json.put("op", "publish");
            json.put("id", "fred");
            json.put("topic", "cmd_vel");
            json.put("msg", s);

            ws.send(json.toJSONString());
        }
    }


    /**
     * 开启链接
     */
    private void buildConnect() {

        URI uri = null;
        try {
            uri = new URI("ws://192.168.2.142:9090");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.d(TAG, "buildConnect: ");
        }

        ws = new WebSocketClient(uri) {

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d(TAG, "onOpen: ");
            }

            @Override
            public void onMessage(String message) {
                Log.d(TAG, "onMessage: " + message);

                tv.setText(message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.d(TAG, "onClose: ");
                tv.setText(reason + "code :" + code);
            }

            @Override
            public void onError(Exception ex) {
                Log.d(TAG, "onError: ");
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        if (serviceIntent != null) {
            stopService(serviceIntent);
        }
    }

}
