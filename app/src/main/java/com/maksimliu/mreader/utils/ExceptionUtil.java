package com.maksimliu.mreader.utils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

/**
 * Created by MaksimLiu on 2017/8/23.
 */

public class ExceptionUtil {


    public static String getNetworkExceptionMessage(Throwable e) {

        String errorMessage = "网络连接异常，请检查网络";

        if (e instanceof SocketTimeoutException) {
            errorMessage = "服务器响应超时";
        } else if (e instanceof ConnectException) {
            errorMessage = "网络连接异常，请检查网络";
        } else if (e instanceof UnknownHostException) {
            errorMessage = "请检查网络连接";
        } else if (e instanceof UnknownServiceException) {
            errorMessage = "未知的服务器错误";
        } else if (e instanceof RuntimeException) {
            errorMessage = "运行时错误";
        }

        return errorMessage;
    }
}
