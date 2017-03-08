package com.maksimliu.mreader.zhihudaily;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.base.BaseActivity;
import com.maksimliu.mreader.bean.ZhiHuDailyDetail;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.utils.MLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhiHuDailyDetailActivity extends BaseActivity implements ZhiHuDailyContract.View {

    @BindView(R.id.toolbar_zhihu_detail)
    Toolbar toolbarZhihuDetail;
    @BindView(R.id.wv_zhihu_detail)
    WebView wvZhihuDetail;
    @BindView(R.id.srl_zhihu_detail)
    SwipeRefreshLayout srlZhihuDetail;
    @BindView(R.id.iv_zhihu_detail)
    ImageView ivZhihuDetail;
    @BindView(R.id.toolbar_zhihu_detail_layout)
    CollapsingToolbarLayout toolbarZhihuDetailLayout;
    @BindView(R.id.tv_image_source)
    TextView tvImageSource;

    private ZhiHuDailyContract.Presenter presenter;

    /**
     * 当前阅读的新闻ID
     */
    private String newsId;

    @Override
    protected void initView() {

        ButterKnife.bind(this);

        setSupportActionBar(toolbarZhihuDetail);

        wvZhihuDetail.setWebChromeClient(new WebChromeClient());
        wvZhihuDetail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvZhihuDetail.getSettings().setSupportZoom(false);//禁用放大缩小
        wvZhihuDetail.getSettings().setJavaScriptEnabled(false);//禁用JS交互
        wvZhihuDetail.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存


        srlZhihuDetail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                presenter.getNewsDetail(newsId);
                srlZhihuDetail.setRefreshing(false);
            }
        });

        new ZhiHuDailyPresenter(this);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhihu_daily_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onZhiHuNewsDetail(EventManager.ZhiHuDailyNewsDetail event) {

        MLog.i("onZhiHuNewsDetail");
        if (event==null){
            return;
        }

        ZhiHuDailyDetail detail= (ZhiHuDailyDetail) event.getObject();


        Glide.with(this).load(detail.getImage()).into(ivZhihuDetail); //更新顶部图片

        toolbarZhihuDetailLayout.setTitle(detail.getTitle());//新闻标题

        tvImageSource.setText(detail.getImage_source());//图片来源

        newsId = String.valueOf(detail.getId());//新闻ID

        StringBuilder css = new StringBuilder();

        //添加引用本地CSS文件
        css.append("<link rel=\"stylesheet\" href=\"");
        css.append("file:///android_asset/zhihudaily_detail.css");
        css.append("\" type=\"text/css\" />");


        //补充完整HTML代码
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE HTML>\n")
                .append("<html>\n<head>\n <meta charset=\"utf-8\" />\n")
                .append(css.toString())
                .append("\n</head>\n<body")
                .append(detail.getBody())
                .append("</body>\n<html>");

        //  wvZhihuDetail.loadData("","text/html","utf-8");
        //加载本地HTML内容，PS:以上的处理方式，在4.0以上加载中文会出现乱码
        wvZhihuDetail.loadDataWithBaseURL("file:///android_asset/", html.toString(), "text/html;utf-8", null, null);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setPresenter(ZhiHuDailyContract.Presenter presnter) {

        this.presenter = presnter;
    }

    @Override
    public void showError(String msg) {

    }
}
