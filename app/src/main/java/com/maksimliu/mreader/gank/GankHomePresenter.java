package com.maksimliu.mreader.gank;

import com.google.gson.Gson;
import com.maksimliu.mreader.MReaderApplication;
import com.maksimliu.mreader.api.GankApi;
import com.maksimliu.mreader.common.AppConfig;
import com.maksimliu.mreader.entity.GankHomeBean;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.network.CacheInterceptor;
import com.maksimliu.mreader.network.RetrofitHelper;
import com.maksimliu.mreader.utils.ACache;
import com.maksimliu.mreader.utils.CacheManager;
import com.maksimliu.mreader.utils.DateUtil;
import com.maksimliu.mreader.utils.MLog;

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
 * Created by MaksimLiu on 2017/3/9.
 */

public class GankHomePresenter implements GankHomeContract.Presenter {


    private GankHomeContract.View view;

    private GankApi gankApi;


    private CacheManager<GankHomeBean> cacheManager;


    public GankHomePresenter(GankHomeContract.View view) {

        this.view = view;
        view.setPresenter(this);


        cacheManager = new CacheManager<>(MReaderApplication.getContext(), AppConfig.GANK_CACHE_NAME, GankHomeBean.class);


        Retrofit retrofit = RetrofitHelper.create(GankApi.BASE_API_URL);

        gankApi = retrofit.create(GankApi.class);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


    /**
     * 根据发布时间进行查询
     * <p>若返回null，则本地没有今天的数据</p>
     *
     * @return GankHomeModel
     */
    @Override
    public void loadLocalData() {


        GankHomeBean homeBean = cacheManager.get(GankHomeContract.HOME + DateUtil.getToday());


        if (homeBean != null) {

            EventManager.GankHome event = EventManager.GankHome.GET_LATEST;


            event.setObject(homeBean);

            EventBus.getDefault().postSticky(event);
        } else {

            EventManager.GankHome event = EventManager.GankHome.ERROR;

            event.setObject(GankHomeContract.NO_HOME_CACHE);
            EventBus.getDefault().post(event);
        }

    }


    @Override
    public void fetchGankDaily(String year, String monthOfYear, String dayOfMonth) {


        final EventManager.GankHome gankEvent = EventManager.GankHome.GET_LATEST;

        Call<GankHomeBean> gankCall = gankApi.getEveryDayGank(year, monthOfYear, dayOfMonth);

        gankCall.enqueue(new Callback<GankHomeBean>() {
            @Override
            public void onResponse(Call<GankHomeBean> call, Response<GankHomeBean> response) {

                gankEvent.setObject(response.body());
                EventBus.getDefault().post(gankEvent);

            }

            @Override
            public void onFailure(Call<GankHomeBean> call, Throwable t) {


                view.showError("加载失败");
            }
        });
    }

}

