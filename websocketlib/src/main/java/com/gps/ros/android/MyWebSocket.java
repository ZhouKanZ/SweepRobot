package com.gps.ros.android;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/12 0012
 * @Descriptiong : WebSocket的数据接收源,以及分发的源头
 */
public class MyWebSocket extends WebSocketClient  {

    private static final String TAG = "MyWebSocket";

    private WebsocketLifeCycle lifeCycle;
    
    public MyWebSocket(URI serverUri) {
        super(serverUri);
    }

    public void setLifeCycle(WebsocketLifeCycle lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

        Log.i(TAG, "onOpen: ");
        if (null != lifeCycle){
            lifeCycle.onOpen(handshakedata);
        }
    }

    @Override
    public void onMessage(String message) {
        if (null != lifeCycle){
            lifeCycle.onMessage(message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (null != lifeCycle){
            lifeCycle.onClose(code,reason,remote);
        }
    }

    @Override
    public void onError(Exception ex) {
        if (null != lifeCycle){
            lifeCycle.onError(ex);
        }
    }

    public void send(String msg){
        try {
            send(msg);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "send: " + "请检查当前网络连并确认ros端已经开启websocket服务！");
        }
    }

    public interface WebsocketLifeCycle{

        void onOpen(ServerHandshake shake);
        void onMessage(String message);
        void onError(Exception e);
        void onClose(int code, String reason, boolean remote);
    }
}
