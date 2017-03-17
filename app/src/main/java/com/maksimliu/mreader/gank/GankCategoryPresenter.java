package com.maksimliu.mreader.gank;

import com.maksimliu.mreader.MReaderApplication;
import com.maksimliu.mreader.api.GankApi;
import com.maksimliu.mreader.bean.GankCategoryBean;
import com.maksimliu.mreader.db.DbHelper;
import com.maksimliu.mreader.db.model.GankCategoryModel;
import com.maksimliu.mreader.db.model.GankDailyModel;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.network.CacheInterceptor;

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

    private DbHelper<GankCategoryModel> dbHelper;


    public GankCategoryPresenter(GankCategoryContract.View view) {


        this.view = view;
        view.setPresenter(this);


        dbHelper = DbHelper.getInstance();

        File cacheFile = new File(MReaderApplication.getContext().getExternalCacheDir(), "gank");

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addNetworkInterceptor(new CacheInterceptor())
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
    public GankCategoryModel getLocalTodayData(String category) {
        return null;
    }

    @Override
    public GankCategoryModel getLocalRecentData(String category) {
        return null;
    }

    @Override
    public void getGankCategoryDaily(String category,String page) {
        Call<GankCategoryBean> call = gankApi.getCategoryGank(category, page);

        final EventManager.GankCategory gankCategory;

        switch (category) {

            case GankContract.ANDROID_CATEGORY:
                gankCategory = EventManager.GankCategory.ANDROID;
                break;
            case GankContract.IOS_CATEGORY:
                gankCategory = EventManager.GankCategory.IOS;
                break;
            case GankContract.FRONT_END_CATEGORY:
                gankCategory = EventManager.GankCategory.FRONT_END;
                break;
            case GankContract.OTHERS_CATEGORY:
                gankCategory = EventManager.GankCategory.OTHERS;
                break;
            case GankContract.FULI_CATEGORY:
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

            }
        });
    }
}
