package com.example.common_tools.utils;


import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class LogUtils {
    public static void init(String tag, boolean isShowThreadInfo) {
        PrettyFormatStrategy builder = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(isShowThreadInfo)
                .tag(tag)
                .build();
        AndroidLogAdapter logAdapter = new AndroidLogAdapter(builder);
        Logger.addLogAdapter(logAdapter);
    }

    public static void setTag(String tag) {
        Logger.t(tag);
    }

    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

    public static void d(Object object) {
        Logger.d(object);
    }


    public static void e(String message, Object... args) {
        Logger.e(message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        Logger.e(throwable, message, args);
    }

    public static void w(String message, Object... args) {
        Logger.w(message, args);
    }

    public static void i(String message, Object... args) {
        Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        Logger.v(message, args);
    }

    public static void wtf(String message, Object... args) {
        Logger.wtf(message, args);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void xml(String xml) {
        Logger.xml(xml);
    }
}
