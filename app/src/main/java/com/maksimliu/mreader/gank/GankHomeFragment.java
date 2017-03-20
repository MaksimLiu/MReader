package com.maksimliu.mreader.gank;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.maksimliu.mreader.MReaderApplication;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.base.LazyFragment;
import com.maksimliu.mreader.bean.GankHomeBean;
import com.maksimliu.mreader.db.model.GankHomeModel;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.utils.ACache;
import com.maksimliu.mreader.utils.DateUtil;
import com.maksimliu.mreader.utils.MLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankHomeFragment extends LazyFragment implements GankHomeContract.View {


    @BindView(R.id.wv_gank)
    WebView wvGank;
    @BindView(R.id.srl_gank)
    SwipeRefreshLayout srlGank;

    private GankHomeContract.Presenter presenter;

    private Bundle bundle;

    private ACache aCache;

    private Gson gson;

    //获取当前时间
    Calendar calendar = Calendar.getInstance();

    String year = DateUtil.convertDate(calendar.get(Calendar.YEAR));

    String month = DateUtil.convertDate(calendar.get(Calendar.MONTH) + 1);

    String day = DateUtil.convertDate(calendar.get(Calendar.DAY_OF_MONTH));

    public GankHomeFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gank_home, container, false);
        ButterKnife.bind(this, view);
        setupView();
        MLog.i("onCreateView");
        isPrepared = true;
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        lazyLoadData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bundle = outState;
    }

    @Override
    public void setPresenter(GankHomeContract.Presenter presnter) {


        this.presenter = presnter;

    }

    @Override
    public void showError(String errorMsg) {

        Snackbar.make(srlGank, errorMsg, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.fetchGankDaily(year, month, day);
                    }
                })
                .show();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGankEvent(EventManager.GankHome gankEvent) {



        MLog.i("onGankEvent");
        if (gankEvent == EventManager.GankHome.ERROR) {

            int error_code = (int) gankEvent.getObject();
            switch (error_code) {

                case GankHomeContract.NO_HOME_CACHE:
                    MLog.i("NO_HOME_CACHE");
                    presenter.fetchGankDaily(year, month, day);
                    break;

            }
        }


        GankHomeBean gankBean = (GankHomeBean) gankEvent.getObject();

        GankHomeModel gankDailyModel;

        MLog.i(gankBean.getResults().size() + "\tgankBean");

        //如果返回的result为空，说明今天API没有数据更新
        if (gankBean.getResults().isEmpty() || gankBean.getResults() == null) {

            MLog.i("今天API没有数据更新");

            day = Integer.valueOf(day) - 1 + "";
            presenter.fetchGankDaily(year, month, day);
            return;


        } else {

            gankDailyModel = gankBean.getResults().get(0);
        }


        aCache.put("gank_home" + year + "-" + month + "-" + day, gson.toJson(gankBean));

        MLog.i("gank_home" + year + "-" + month + "-" + day);

        showHtml(gankDailyModel);


    }

    private void showHtml(GankHomeModel gankDailyModel) {


        //补充完整HTML代码
        StringBuilder html = new StringBuilder();

        //去除<img />标签
        String body = gankDailyModel.getContent().replaceAll("<img(.*)/>", "");

        html.append("<!DOCTYPE HTML>\n")
                .append("<html>\n<head>\n <meta charset=\"utf-8\" />\n")
                .append("\n</head>\n<body")
                .append(body)
                .append("</body>\n<html>");


        wvGank.loadDataWithBaseURL("", html.toString(), "text/html;UTF-8", null, null);
    }


    @Override
    protected void setupView() {


        new GankHomePresenter(this);

        aCache = ACache.get(MReaderApplication.getContext());

        gson = new Gson();

        wvGank.setWebChromeClient(new WebChromeClient());
        wvGank.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvGank.getSettings().setSupportZoom(false);//禁用放大缩小
        wvGank.getSettings().setJavaScriptEnabled(false);//禁用JS交互
        wvGank.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存


        srlGank.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                presenter.fetchGankDaily(year, month, day);

                srlGank.setRefreshing(false);
            }
        });


    }

    @Override
    protected void lazyLoadData() {

        if (!isPrepared || !isVisible) {
            return;
        }


        MLog.i("lazyLoadData\t" + this.getClass().getSimpleName() + "\t" + isVisible);

        presenter.loadLocalData();


    }
}
