package com.maksimliu.mreader.zhihudaily;

import com.maksimliu.mreader.MReaderApplication;
import com.maksimliu.mreader.api.ZhiHuDailyApi;
import com.maksimliu.mreader.common.AppConfig;
import com.maksimliu.mreader.entity.ZhiHuDetailBean;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.network.RetrofitHelper;
import com.maksimliu.mreader.utils.CacheManager;
import com.maksimliu.mreader.utils.MLog;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by MaksimLiu on 2017/3/19.
 */

public class ZhiHuDetailPresenter implements ZhiHuDetailContract.Presenter {


    private ZhiHuDetailContract.View view;

    private ZhiHuDailyApi zhiHuDailyApi;

    private CacheManager<ZhiHuDetailBean> cacheManager;


    public ZhiHuDetailPresenter(ZhiHuDetailContract.View view) {

        this.view = view;
        view.setPresenter(this);

        Retrofit retrofit = RetrofitHelper.create(ZhiHuDailyApi.BASE_API_4_URL);
        zhiHuDailyApi = retrofit.create(ZhiHuDailyApi.class);

        cacheManager = new CacheManager<>(MReaderApplication.getContext(), AppConfig.ZHIHU_CACHE_NAME, ZhiHuDetailBean.class);

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void fetchNewsDetail(String id) {

        final EventManager.ZhiHuNewsDetail event = EventManager.ZhiHuNewsDetail.GET_DETAIL;

        Call<ZhiHuDetailBean> latestCall = zhiHuDailyApi.getNewsDetail(id);

        latestCall.enqueue(new Callback<ZhiHuDetailBean>() {
            @Override
            public void onResponse(Call<ZhiHuDetailBean> call, Response<ZhiHuDetailBean> response) {

                event.setObject(response.body());
                EventBus.getDefault().post(event);
            }

            @Override
            public void onFailure(Call<ZhiHuDetailBean> call, Throwable t) {


                view.showError("加载失败 " + t.getMessage());

            }
        });
    }

    @Override
    public void loadNewsDetail(String id) {
        MLog.i("loadNewsDetail");

        ZhiHuDetailBean bean = cacheManager.get(ZhiHuDetailContract.ZHIHU_DETAIL_NEWS + id);
        if (bean != null) {

            EventManager.ZhiHuNewsDetail detailEvent = EventManager.ZhiHuNewsDetail.GET_DETAIL;
            detailEvent.setObject(bean);
            EventBus.getDefault().post(detailEvent);

        } else {

            MLog.i("Error");

            EventManager.ZhiHuNewsDetail errorEvent = EventManager.ZhiHuNewsDetail.ERROR;
            errorEvent.setObject(ZhiHuDetailContract.NO_DETAIL_CACHE);
            EventBus.getDefault().post(errorEvent);
        }
    }
}
