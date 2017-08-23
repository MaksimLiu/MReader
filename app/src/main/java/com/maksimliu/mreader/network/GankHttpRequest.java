package com.maksimliu.mreader.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.maksimliu.mreader.BuildConfig;
import com.maksimliu.mreader.MReaderApplication;
import com.maksimliu.mreader.api.GankApi;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MaksimLiu on 2017/8/21.
 */

public class GankHttpRequest {

    private static Retrofit retrofit;

    private static OkHttpClient okHttpClient;


    private static Retrofit getInstance() {

        if (retrofit == null) {

            synchronized (GankHttpRequest.class) {

                if (retrofit == null) {
                    initOkHttpClient();
                    initRetrofit();

                }
            }
        }

        return retrofit;
    }

    private static void initRetrofit() {


        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(GankApi.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    private static void initOkHttpClient() {


        //10M缓存空间
        Cache cache = new Cache(MReaderApplication.getContext().getExternalCacheDir(), 10 * 1024 * 1024);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(new CacheInterceptor()).cache(cache)
                .addInterceptor(new CacheInterceptor()).cache(cache)
                .retryOnConnectionFailure(true)
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS);


        if (BuildConfig.DEBUG) {

            //请求日志
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        okHttpClient = builder.build();

    }

    public static <T> T create(Class<T> tClass) {

        return getInstance().create(tClass);
    }
}
