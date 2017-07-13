package com.gps.ros.android;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/12 0012
 * @Descriptiong : WebSocket的数据接收源,以及分发的源头
 */
public class MyWebSocket extends WebSocketClient {


    private static final String TAG = "MyWebSocket";
    
    public MyWebSocket(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG, "onOpen: ");
    }

    @Override
    public void onMessage(String message) {
        Log.d(TAG, "onMessage: ");
        // 用EventBus去分发，过来的消息
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG, "onClose: ");
    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG, "onError: ");
    }
}
