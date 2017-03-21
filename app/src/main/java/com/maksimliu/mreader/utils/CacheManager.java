package com.maksimliu.mreader.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by MaksimLiu on 2017/3/21.
 * <h3>缓存管理</h3>
 */

public class CacheManager<T> {

    /**
     * Gson实例
     */
    private Gson gson;

    /**
     * ACache实例
     */
    private ACache aCache;

    /**
     * 缓存文件名
     */
    private String cacheName;

    private Class beanClass;

    /**
     * 默认缓存一个月
     */
    private static final int DEFAULT_SAVE_TIME = ACache.TIME_DAY * 30;


    public CacheManager(Context context, String cacheName, Class beanClass) {

        gson = new Gson();
        aCache = ACache.get(context, cacheName);
        this.cacheName = cacheName;
        this.beanClass = beanClass;

    }

    /**
     * 将bean的信息转换成JSON存储,默认缓存一个月
     *
     * @param key       key
     * @param beanClass bean类
     */
    public void put(String key, T beanClass) {

        aCache.put(key, gson.toJson(beanClass), DEFAULT_SAVE_TIME);
    }

    /**
     * 将bean的信息转换成JSON存储,时间过期，缓存删除
     *
     * @param key       key
     * @param beanClass bean类
     * @param saveTime  缓存时间
     */
    public void put(String key, T beanClass, int saveTime) {

        aCache.put(key, gson.toJson(beanClass), saveTime);
    }

    /**
     * 根据key获取JSON信息，进一步获取对应的bean
     *
     * @param key key
     * @return T
     */
    public T get(String key) {

        String value=aCache.getAsString(key);
        MLog.i(value);
        if (value!= null && beanClass != null) {
            return (T) gson.fromJson(value, beanClass);
        }

        return null;


    }

}
