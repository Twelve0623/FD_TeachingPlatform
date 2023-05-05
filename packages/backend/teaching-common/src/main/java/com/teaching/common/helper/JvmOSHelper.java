package com.teaching.common.helper;

import java.util.function.Supplier;

/**
 * JVM信息工具类
 *
 * @author sacher
 * */
public final class JvmOSHelper {
    private JvmOSHelper() {
    }

    @SuppressWarnings("unused")
    private static volatile String OS_NAME, OS_ARCH, FILE_SEPARATOR, PROJECT_DIR;

    /**
     * 是否Window操作系统
     **/
    public static boolean isWindows() {
        return ofValue(OS_NAME, () -> System.getProperty("os.name")).toUpperCase().startsWith("WIN");
    }

    /**
     * 获取操作系统版本
     **/
    public static boolean isV64() {
        return ofValue(OS_ARCH, () -> System.getProperty("os.arch")).endsWith("64");
    }

    /**
     * 不同系统的文件分隔符
     **/
    public static String fileSeparator() {
        return ofValue(FILE_SEPARATOR, () -> System.getProperty("file.separator"));
    }

    /**
     * 获取工程所在目录
     **/
    public static String projectDir() {
        return ofValue(PROJECT_DIR, () -> System.getProperty("user.dir"));
    }

    private static String ofValue(String rs, Supplier<String> supplier) {
        if (StringHelper.isBlank(rs)) {
            rs = supplier.get();
        }
        return rs;
    }
}
