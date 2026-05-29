package com.itheima.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户上下文工具，基于 ThreadLocal。
 * 一次请求一个线程，线程内任意位置均可取出当前登录用户信息。
 * <p>
 * 用法：
 * Map&lt;String, Object&gt; user = BaseContext.get();
 * Integer id = (Integer) user.get("id");
 */
public class BaseContext {
    //java的web服务器请求是一个请求对应一个线程，threadLocal就是这个线程的私人储物柜，只要在同一个线程内，这个储物柜是共享的
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(Map<String, Object> user) {
        THREAD_LOCAL.set(user);
    }

    public static Map<String, Object> get() {
        return THREAD_LOCAL.get();
    }

    /** 请求结束后必须调用，防止内存泄漏 */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}