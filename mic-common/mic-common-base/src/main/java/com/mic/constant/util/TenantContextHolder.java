package com.mic.constant.util;

import com.alibaba.ttl.TransmittableThreadLocal;


/**
 * @Description:  租户holder
 * @author: pf
 * @create: 2021/1/14 10:36
 */
public class TenantContextHolder {
    /**
     * 支持父子线程之间的数据传递
     */
    private static final ThreadLocal<String> CONTEXT = new TransmittableThreadLocal<>();

    public static void setTenant(String tenant) {
        CONTEXT.set(tenant);
    }

    public static String getTenant() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
