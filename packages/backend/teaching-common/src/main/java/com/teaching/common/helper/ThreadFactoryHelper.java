package com.teaching.common.helper;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/** 线程工具类 **/
public final class ThreadFactoryHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadFactoryHelper.class);

    private ThreadFactoryHelper() {
    }

    public static void waiting(long time, TimeUnit unit) {
        try {
            unit.sleep(time);
        } catch (InterruptedException e) {
            // ignore interrupted exception
        }
    }

    /** 设置线程名称 **/
    public static ThreadFactory threadFactoryOf(String name) {
        String nameFormat = StringHelper.defaultIfBlank(name, "ES") + "@t%d";
        Thread.UncaughtExceptionHandler eh = (t, e) -> LOG.warn("Thread {} has unexpected error ", t.getName(), e);
        return new ThreadFactoryBuilder().setNameFormat(nameFormat).setUncaughtExceptionHandler(eh).build();
    }

}
