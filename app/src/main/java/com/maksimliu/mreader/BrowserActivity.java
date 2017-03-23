package com.maksimliu.mreader;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.maksimliu.mreader.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MaksimLiu on 2017/3/22.
 */

public class BrowserActivity extends BaseActivity {
    @BindView(R.id.wv_gank)
    WebView wvGank;
    @BindView(R.id.srl_gank)
    SwipeRefreshLayout srlGank;
    @BindView(R.id.pb_webview)
    ProgressBar pbWebview;
    @BindView(R.id.wv_toolbar)
    Toolbar wvToolbar;


    private String loadUrl;

    @Override
    protected void initListener() {

        srlGank.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                wvGank.loadUrl(loadUrl);
                srlGank.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        wvGank.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvGank.getSettings().setSupportZoom(false);//禁用放大缩小
        wvGank.getSettings().setJavaScriptEnabled(true);//禁用JS交互
        wvGank.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存

        setSupportActionBar(wvToolbar);
        wvToolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        wvToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        loadUrl = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");

        wvToolbar.setTitle(title);

        wvGank.loadUrl(loadUrl);

        wvGank.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbWebview.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbWebview.setVisibility(View.GONE);
            }
        });

        wvGank.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pbWebview.setProgress(newProgress);
            }


        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.webview_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
