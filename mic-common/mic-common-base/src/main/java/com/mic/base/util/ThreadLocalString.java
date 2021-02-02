package com.mic.base.util;


/**
 * @Description:  记录请求详情信息到当前线程中，可在任何地方获取
 * @author: pf
 * @create: 2021/1/14 10:34
 */
public class ThreadLocalString {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * 设置请求信息到当前线程中
     *
     * @param value
     */
    public static void set(String value) {
        threadLocal.set(value);
    }

    /**
     * 从当前线程中获取请求信息
     */
    public static String get() {
        return threadLocal.get();
    }

    /**
     * 销毁
     */
    public static void remove() {
        threadLocal.remove();
    }

}
