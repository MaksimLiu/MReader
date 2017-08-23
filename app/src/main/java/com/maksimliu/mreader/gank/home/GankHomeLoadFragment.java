package com.maksimliu.mreader.gank.home;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.maksimliu.mreader.R;
import com.maksimliu.mreader.base.LazyLoadFragment;
import com.maksimliu.mreader.common.AppConfig;
import com.maksimliu.mreader.bean.GankCategoryModels;
import com.maksimliu.mreader.bean.GankHomeBean;
import com.maksimliu.mreader.bean.GankHomeModel;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.utils.CacheManager;
import com.maksimliu.mreader.utils.DateUtil;
import com.maksimliu.mreader.utils.MLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankHomeLoadFragment extends LazyLoadFragment implements GankHomeContract.View {


    @BindView(R.id.wv_gank)
    WebView wvGank;
    @BindView(R.id.srl_gank)
    SwipeRefreshLayout srlGank;
    @BindView(R.id.pb_webview)
    ProgressBar pbWebview;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.wv_toolbar)
    Toolbar wvToolbar;

    private GankHomeContract.Presenter presenter;

    private CacheManager<GankHomeBean> cacheManager;

    //获取当前时间
    Calendar calendar = Calendar.getInstance();

    String year = DateUtil.convertDate(calendar.get(Calendar.YEAR));

    String month = DateUtil.convertDate(calendar.get(Calendar.MONTH) + 1);

    String day = DateUtil.convertDate(calendar.get(Calendar.DAY_OF_MONTH));

    public GankHomeLoadFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.universal_list_card;
    }

    @Override
    protected void initVariable() {

    }

    public void setPresenter(GankHomeContract.Presenter mPresenter) {

        this.presenter = mPresenter;
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

    @Override
    public void setIsLoading(boolean flag) {
        loadingView.setVisibility(flag ? View.VISIBLE : View.GONE);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGankEvent(EventManager.GankHome gankEvent) {

        if (gankEvent == EventManager.GankHome.ERROR) {

            int error_code = (int) gankEvent.getObject();
            switch (error_code) {

                case GankHomeContract.NO_HOME_CACHE:
                    MLog.i("NO_HOME_CACHE");
                    presenter.fetchGankDaily(year, month, day);
                    break;
            }

            return;
        }

        GankHomeBean gankBean = (GankHomeBean) gankEvent.getObject();

        GankHomeModel gankDailyModel;


        //如果返回的result为空，说明今天API没有数据更新
        if (gankBean.getResults().isEmpty() || gankBean.getResults() == null) {

            MLog.i("今天API没有数据更新");

            day = Integer.valueOf(day) - 1 + "";
            presenter.fetchGankDaily(year, month, day);
            return;


        } else {

            gankDailyModel = gankBean.getResults().get(0);
        }


        cacheManager.put(GankHomeContract.HOME + year + "-" + month + "-" + day, gankBean);

        showHTML(presenter.handleHTML(gankDailyModel.getContent()));



    }


    @Override
    protected void initView(Bundle savedInstanceState) {

        wvToolbar.setVisibility(View.GONE);
        appBar.setVisibility(View.GONE);

        new GankHomePresenter(this);

        cacheManager = new CacheManager<>(getActivity(), AppConfig.GANK_CACHE_NAME, GankCategoryModels.class);

        wvGank.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pbWebview.setProgress(newProgress);
                if (newProgress == 100) {
                    pbWebview.setVisibility(View.GONE);
                }

            }
        });
        wvGank.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvGank.getSettings().setSupportZoom(false);//禁用放大缩小
        wvGank.getSettings().setJavaScriptEnabled(false);//禁用JS交互
        wvGank.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存


    }

    @Override
    protected void initListener() {

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
        presenter.loadLocalData();
    }

    @Override
    public void showHTML(String body) {
        //补充完整HTML代码
        StringBuilder css = new StringBuilder();

        //添加引用本地CSS文件
        css.append("<link rel=\"stylesheet\" href=\"");
        css.append("file:///android_asset/gank_home.css");
        css.append("\" type=\"text/css\" />");



        //补充完整HTML代码
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE HTML>\n")
                .append("<html>\n<head>\n <meta charset=\"utf-8\" />\n")
                .append(css.toString())
                .append("\n</head>\n<body")
                .append(body)
                .append("</body>\n<html>");


        wvGank.loadDataWithBaseURL("", html.toString(), "text/html;UTF-8", null, null);
    }
}
