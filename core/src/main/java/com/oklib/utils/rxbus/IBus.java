package com.oklib.utils.rxbus;

/**
 * @Description: 事件总线接口
 * @author: <a href="http://xiaoyaoyou1212.360doc.com">DAWI</a>
 * @date: 2016-12-19 15:06
 */
public interface IBus {
    /**
     * 注册事件
     *
     * @param object
     */
    void register(Object object);

    /**
     * 解除事件注册
     *
     * @param object
     */
    void unregister(Object object);

    /**
     * 发送事件
     *
     * @param event
     */
    void post(IEvent event);

    /**
     * 发送粘性事件
     *
     * @param event
     */
    void postSticky(IEvent event);
}
