package com.gps.sweeprobot.mvp;

import io.reactivex.functions.Consumer;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/4 0004
 * @Descriptiong : 需要用到rxbus的model
 */

public interface RxBusModel<T> extends IModel {

    /**
     *  注册rxbus需要向外部回调数据
     * @param consumer
     */
    void registerRxBus(Consumer<T> consumer);

    /**
     * 不需要回调数据
     */
    void registerRxBus();

    /**
     *  注销监听
     */
    void unregisterRxBus();
}
