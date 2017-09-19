//package com.gps.sweeprobot.utils;
//
//import java.util.LinkedList;
//import java.util.Queue;
//
///**
// * Create by WangJun on 2017/8/29
// */
//
//public abstract class ObstaclePool<T> {
//
//    /**
//     * 对象池最大容量
//     */
//    private int mSize;
//
//    /**
//     * 对象池
//     */
//    private Queue<T> mQueue;
//
//    /**
//     * 创建一个对象池
//     *
//     * @param size 对象池最大容量
//     */
//    public ObstaclePool(int size){
//
//        mSize = size;
//        mQueue = new LinkedList<>();
//    }
//
//    /**
//     * 获取一个空闲的对象
//     *
//     * 如果对象池为空,则对象池自己会new一个返回.
//     * 如果对象池内有对象,则取一个已存在的返回.
//     * take出来的对象用完要记得调用given归还.
//     * 如果不归还,让然会发生内存抖动,但不会引起泄漏.
//     *
//     * @return 可用的对象
//     *
//     * @see #given(Object)
//     */
//    public T take(){
//
//        if (mQueue.size() == 0){
//            //如果池内为空就新建一个返回
//            return newInstance();
//        }else {
//            //对象池里面有就从顶端拿出来一个返回
//            return resetInstance(mQueue.poll());
//        }
//    }
//
//    /**
//     * 归还对象池内申请的对象
//     *
//     * 如果归还的对象数量超过对象池容量,那么归还的对象就会被丢弃.
//     *
//     * @param obj 归还的对象
//     *
//     * @see #take()
//     */
//    public void given(T obj){
//
//        if (obj != null && mQueue.size() < mSize){
//            //如果对象池还有空位置就归还对象
//            mQueue.offer(obj);
//        }
//    }
//
//    /**
//     * 实例化对象
//     *
//     * @return 创建的对象
//     */
//    abstract protected T newInstance();
//
//    /**
//     * 重置对象
//     *
//     * 把对象数据清空到就像刚创建的一样.
//     *
//     * @param obj 需要被重置的对象
//     * @return 被重置之后的对象
//     */
//    abstract protected T resetInstance(T obj);
//}
