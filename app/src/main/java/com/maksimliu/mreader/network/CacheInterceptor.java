package com.maksimliu.mreader.network;


import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public class CacheInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        if (!NetworkState.isConnected()) {

            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();

        }
        Response response = chain.proceed(request);
        if (NetworkState.isConnected()) {


            int maxAge = 60 * 60 * 24;//缓存一天失效
            response.newBuilder().
                    removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)

                    .build();
        } else {

            int maxStale = 60 * 60 * 24 * 28; // // 无网络缓存保存四周
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }


        return response;


    }
}
