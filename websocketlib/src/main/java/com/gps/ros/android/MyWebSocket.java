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
public class MyWebSocket extends WebSocketClient {

    public final static Observable webSocketObservable = Observable.create(new ObservableOnSubscribe() {
        @Override
        public void subscribe(@NonNull ObservableEmitter e) throws Exception {

        }
    });

    private static final String TAG = "MyWebSocket";
    
    public MyWebSocket(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.i(TAG, "onOpen: ");
    }

    @Override
    public void onMessage(String message) {

        Log.d(TAG, "onMessage: ");
        // 根据string来得到message -- 将message转成
        webSocketObservable.map(new Function<String,Observable>() {
            @Override
            public Observable apply(@NonNull String s) throws Exception {
                return null;
            }
        });

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
