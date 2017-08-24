package com.maksimliu.lib.utils;

import android.util.Log;

import com.maksimliu.lib.BuildConfig;


/**
 * Created by MaksimLiu on 2017/3/4.
 * <h3>日志输出工具类</h3>
 *
 * <p>
 *     1.默认Debug状态才输出日志
 *     2.对NULL值进行校验输出
 * </p>
 */

public class LogUtil {

    public static final String TAG = "LogUtil";

    public static void i(String msg) {

        if (BuildConfig.DEBUG) {
            if (msg != null) {
                Log.i(TAG, msg);
            } else {
                Log.i(TAG, "null value");
            }
        }
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            if (msg != null) {
                Log.e(TAG, msg);
            } else {
                Log.e(TAG, "null value");
            }
        }
    }


    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            if (msg != null) {
                Log.e(tag, msg);
            } else {
                Log.e(tag, "null value");
            }
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            if (msg != null) {
                Log.i(tag, msg);
            } else {
                Log.i(tag, "null value");
            }
        }
    }
}
