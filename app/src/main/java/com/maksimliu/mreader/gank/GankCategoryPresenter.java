package com.maksimliu.mreader.gank;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.maksimliu.mreader.MReaderApplication;
import com.maksimliu.mreader.api.GankApi;
import com.maksimliu.mreader.common.AppConfig;
import com.maksimliu.mreader.entity.GankCategoryBean;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.utils.CacheManager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MaksimLiu on 2017/3/16.
 */

public class GankCategoryPresenter implements GankCategoryContract.Presenter {


    private GankCategoryContract.View view;

    private GankApi gankApi;

    private CacheManager<GankCategoryBean> cacheManager;

    public GankCategoryPresenter(GankCategoryContract.View view) {


        this.view = view;
        view.setPresenter(this);

        cacheManager = new CacheManager<>(MReaderApplication.getContext(), AppConfig.GANK_CACHE_NAME,GankCategoryBean.class);

        File cacheFile = new File(MReaderApplication.getContext().getExternalCacheDir(), "gank");

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .cache(new Cache(cacheFile, 1024 * 1024 * 10)) //10M缓存空间
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(GankApi.BASE_API_URL)
                .build();

        gankApi = retrofit.create(GankApi.class);

    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


    @Override
    public void loadCategory(String category) {

        final EventManager.GankCategory gankCategory;

        int eventType;

        switch (category) {

            case GankApi.ANDROID_CATEGORY_TYPE:
                gankCategory = EventManager.GankCategory.ANDROID;
                eventType = GankCategoryContract.NO_ANDROID_CACHE;
                break;
            case GankApi.IOS_CATEGORY_TYPE:
                gankCategory = EventManager.GankCategory.IOS;
                eventType = GankCategoryContract.NO_IOS_CACHE;
                break;
            case GankApi.FRONT_END_CATEGORY_TYPE:
                gankCategory = EventManager.GankCategory.FRONT_END;
                eventType = GankCategoryContract.NO_FRONT_END_CACHE;
                break;
            case GankApi.OTHERS_CATEGORY_TYPE:
                gankCategory = EventManager.GankCategory.OTHERS;
                eventType = GankCategoryContract.NO_OTHERS_CACHE;
                break;
            case GankApi.FULI_CATEGORY_TYPE:
                gankCategory = EventManager.GankCategory.FULI;
                eventType = GankCategoryContract.NO_FULI_CACHE;
                break;
            default:
                gankCategory = EventManager.GankCategory.EXTRA_RESOURCE;
                eventType = GankCategoryContract.NO_EXTRA_RESOURCE;
                break;
        }


        GankCategoryBean bean = cacheManager.get(category);

        if (bean != null) {

            gankCategory.setObject(bean);
            EventBus.getDefault().post(gankCategory);
        } else {

            EventManager.GankCategory event = EventManager.GankCategory.ERROR;

            event.setObject(eventType);

            EventBus.getDefault().post(event);
        }


    }


    @Override
    public void fetchCategory(String category, String page) {


        final EventManager.GankCategory gankCategory;

        Call<GankCategoryBean> call = gankApi.getCategoryGank(category, page);

        switch (category) {

            case GankApi.ANDROID_CATEGORY_TYPE:
                gankCategory = EventManager.GankCategory.ANDROID;
                break;
            case GankApi.IOS_CATEGORY_TYPE:
                gankCategory = EventManager.GankCategory.IOS;
                break;
            case GankApi.FRONT_END_CATEGORY_TYPE:
                gankCategory = EventManager.GankCategory.FRONT_END;
                break;
            case GankApi.OTHERS_CATEGORY_TYPE:
                gankCategory = EventManager.GankCategory.OTHERS;
                break;
            case GankApi.FULI_CATEGORY_TYPE:
                gankCategory = EventManager.GankCategory.FULI;
                break;
            default:
                gankCategory = EventManager.GankCategory.EXTRA_RESOURCE;
                break;
        }

        call.enqueue(new Callback<GankCategoryBean>() {
            @Override
            public void onResponse(Call<GankCategoryBean> call, Response<GankCategoryBean> response) {


                gankCategory.setObject(response.body());
                EventBus.getDefault().post(gankCategory);

            }

            @Override
            public void onFailure(Call<GankCategoryBean> call, Throwable t) {

                view.showError("加载失败");
            }
        });
    }
}
