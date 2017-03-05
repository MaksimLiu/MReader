package com.maksimliu.mreader.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.maksimliu.mreader.MReaderApplication;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public class NetworkState {


    /**
     * 获取活动网络信息
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @return NetworkInfo
     */
    public static NetworkInfo getNetworkInfo() {

        return ((ConnectivityManager) MReaderApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

    /**
     * 判断网络是否连接
     *
     * @return boolean
     */
    public static boolean isConnected() {


        NetworkInfo info = getNetworkInfo();
        return info != null && info.isConnected();
    }
}
