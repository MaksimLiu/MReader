package com.maksimliu.mreader.zhihudaily;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.base.BaseRxActivity;
import com.maksimliu.mreader.bean.ZhiHuDetailBean;

import butterknife.BindView;

public class ZhiHuDetailActivity extends BaseRxActivity implements ZhiHuDetailContract.View, SwipeRefreshLayout.OnRefreshListener {

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

    /**
     * 当前阅读的新闻ID
     */
    private String newsId;


    private ZhiHuDetailPresenter mPresenter;


    public static Intent newIntent(Context context, String newsId) {

        Intent intent = new Intent(context, ZhiHuDetailActivity.class);
        intent.putExtra("newsId", newsId);
        return intent;
    }


    @Override
    protected void initListener() {

        srlZhihuDetail.setOnRefreshListener(this);
    }

    @Override
    protected void initVariable() {

        newsId = getIntent().getStringExtra("newsId");

        mPresenter = new ZhiHuDetailPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setSupportActionBar(toolbarZhihuDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        wvZhihuDetail.setWebChromeClient(new WebChromeClient());
        wvZhihuDetail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvZhihuDetail.getSettings().setSupportZoom(false);//禁用放大缩小
        wvZhihuDetail.getSettings().setJavaScriptEnabled(false);//禁用JS交互
        wvZhihuDetail.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存


    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhihu_daily_detail;
    }


    @Override
    protected void loadData() {
        super.loadData();
        onRefresh();
    }

    public void setPresenter(ZhiHuDetailContract.Presenter mPresenter) {
    }

    @Override
    public void showError(String msg) {

        Snackbar.make(toolbarZhihuDetail, msg, Snackbar.LENGTH_LONG)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.getNewsDetail(newsId);
                    }
                }).show();

    }

    @Override
    public void setIsLoading(boolean flag) {

    }


    @Override
    public void showHTML(String body) {

        //  wvZhihuDetail.loadData("","text/html","utf-8");
        //加载本地HTML内容，PS:以上的处理方式，在4.0以上加载中文会出现乱码
        wvZhihuDetail.loadDataWithBaseURL("file:///android_asset/", body, "text/html;utf-8", null, null);
    }

    @Override
    public void bindData(ZhiHuDetailBean bean) {

        showHTML(mPresenter.processHtmlContent(bean.getBody()));

        tvImageSource.setText(bean.getImage_source());
        Glide.with(this).load(bean.getImages().get(0)).into(ivZhihuDetail);
        toolbarZhihuDetail.setTitle(bean.getTitle());
        toolbarZhihuDetailLayout.setTitle(bean.getTitle());


    }


    @Override
    public void onRefresh() {

        mPresenter.getNewsDetail(newsId);
        srlZhihuDetail.setRefreshing(false);

    }
}
