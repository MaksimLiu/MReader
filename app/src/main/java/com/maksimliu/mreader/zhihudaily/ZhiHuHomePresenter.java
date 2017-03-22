package com.maksimliu.mreader.zhihudaily;

import com.maksimliu.mreader.MReaderApplication;
import com.maksimliu.mreader.api.ZhiHuDailyApi;
import com.maksimliu.mreader.common.AppConfig;
import com.maksimliu.mreader.entity.ZhiHuNewsBean;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.gank.GankHomeContract;
import com.maksimliu.mreader.network.RetrofitHelper;
import com.maksimliu.mreader.utils.CacheManager;
import com.maksimliu.mreader.utils.DateUtil;
import com.maksimliu.mreader.utils.MLog;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public class ZhiHuHomePresenter implements ZhiHuHomeContract.Presenter {

    private ZhiHuHomeContract.View view;

    private ZhiHuDailyApi zhiHuDailyApi;

    private CacheManager<ZhiHuNewsBean> cacheManager;

    public ZhiHuHomePresenter(ZhiHuHomeContract.View view) {

        this.view = view;
        view.setPresenter(this);

        Retrofit retrofit= RetrofitHelper.create(ZhiHuDailyApi.BASE_API_4_URL);

        zhiHuDailyApi = retrofit.create(ZhiHuDailyApi.class);

        cacheManager = new CacheManager<>(MReaderApplication.getContext(), AppConfig.ZHIHU_CACHE_NAME, ZhiHuNewsBean.class);


    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void fetchLatestNews() {


        final EventManager.ZhiHuDailyNews event = EventManager.ZhiHuDailyNews.GET_LATEST;

        Call<ZhiHuNewsBean> latestCall = zhiHuDailyApi.getLatestNews();

        latestCall.enqueue(new Callback<ZhiHuNewsBean>() {
            @Override
            public void onResponse(Call<ZhiHuNewsBean> call, Response<ZhiHuNewsBean> response) {

                event.setObject(response.body());
                EventBus.getDefault().post(event);

            }

            @Override
            public void onFailure(Call<ZhiHuNewsBean> call, Throwable t) {


                view.showError("加载失败");

            }
        });

    }

    @Override
    public void loadLatestNews() {

        EventManager.ZhiHuDailyNews event;
        MLog.i("loadLatestNews");

        ZhiHuNewsBean bean=cacheManager.get(GankHomeContract.HOME+ DateUtil.getToday());
        if (bean != null) {
            event = EventManager.ZhiHuDailyNews.GET_LATEST;
            event.setObject(bean);
            EventBus.getDefault().post(event);

        } else {
            MLog.i("Error");
            event = EventManager.ZhiHuDailyNews.ERROR;
            event.setObject(ZhiHuHomeContract.NO_LATEST_NEWS_CACHE);
            EventBus.getDefault().post(event);
        }


    }

    @Override
    public void fetchOldNews(String date, int type) {

        final EventManager.ZhiHuDailyNews event;

        if (type == ZhiHuHomeContract.ADD_OLD_NEWS) {
            event = EventManager.ZhiHuDailyNews.ADD_OLD_NEWS;
        } else {
            event = EventManager.ZhiHuDailyNews.SET_OLD_NEWS;
        }

        Call<ZhiHuNewsBean> latestCall = zhiHuDailyApi.getOldeNews(date);

        latestCall.enqueue(new Callback<ZhiHuNewsBean>() {
            @Override
            public void onResponse(Call<ZhiHuNewsBean> call, Response<ZhiHuNewsBean> response) {


                event.setObject(response.body());
                EventBus.getDefault().post(event);

            }

            @Override
            public void onFailure(Call<ZhiHuNewsBean> call, Throwable t) {


                view.showError("加载失败");

            }
        });
    }

    @Override
    public void loadOldNews(Calendar calendar) {

        EventManager.ZhiHuDailyNews event;
        MLog.i("loadOldNews");

        ZhiHuNewsBean bean = cacheManager.get(GankHomeContract.HOME+ DateUtil.getCurrentDate(calendar));
        if (bean != null) {
            event = EventManager.ZhiHuDailyNews.ADD_OLD_NEWS;
            event.setObject(bean);
            EventBus.getDefault().post(event);

        } else {
            MLog.i("Error");
            event = EventManager.ZhiHuDailyNews.ERROR;
            event.setObject(ZhiHuHomeContract.NO_OLD_NEWS_CACHE);
            EventBus.getDefault().post(event);
        }


    }


}

