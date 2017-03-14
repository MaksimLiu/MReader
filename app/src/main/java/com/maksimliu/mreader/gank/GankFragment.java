package com.maksimliu.mreader.gank;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.maksimliu.mreader.R;
import com.maksimliu.mreader.base.EventFragment;
import com.maksimliu.mreader.bean.GankBean;
import com.maksimliu.mreader.db.DbHelper;
import com.maksimliu.mreader.db.model.GankDailyModel;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.utils.DateUtil;
import com.maksimliu.mreader.utils.MLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankFragment extends EventFragment implements GankContract.View {


    @BindView(R.id.wv_gank)
    WebView wvGank;
    @BindView(R.id.srl_gank)
    SwipeRefreshLayout srlGank;
    private GankContract.Presenter presenter;

    private Bundle bundle;

    private DbHelper dbHelper;

    //获取当前时间
    Calendar calendar = Calendar.getInstance();

    String year = DateUtil.convertDate(calendar.get(Calendar.YEAR));

    String month = DateUtil.convertDate(calendar.get(Calendar.MONTH) + 1);

    String day = DateUtil.convertDate(calendar.get(Calendar.DAY_OF_MONTH));

    public GankFragment() {
        // Required empty public constructor
        dbHelper = DbHelper.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (bundle == null) {


            GankDailyModel dailyModel = presenter.loadLocalTodayData();

            if (dailyModel == null) {
                MLog.i("is null");
                presenter.getEveryDayGank(year, month, day);
                return;

            }

            showHtml(dailyModel);

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bundle = outState;
    }

    @Override
    public void setPresenter(GankContract.Presenter presnter) {


        this.presenter = presnter;

    }

    @Override
    public void showError(String errorMsg) {

        Snackbar.make(srlGank, errorMsg, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.getEveryDayGank(year, month, day);
                    }
                })
                .show();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGankEvent(EventManager.Gank gankEvent) {


        boolean isSave = true;

        if (gankEvent == null) {
            return;
        }


        GankBean gankBean = (GankBean) gankEvent.getObject();

        GankDailyModel gankDailyModel;

        MLog.i(gankBean.getResults().size() + "\tgankBean");

        //如果返回的result为空，说明今天API没有数据更新，则加载本地最新的数据
        if (gankBean.getResults().size() == 0 || gankBean.getResults() == null) {

            MLog.i("今天API没有数据更新");
            isSave = false;
            gankDailyModel = (GankDailyModel) presenter.loadLocalRecentData();

        } else {

            gankDailyModel = gankBean.getResults().get(0);
        }


        //如果加载的是本地数据，则不需要重复保存
        if (isSave) {
            String publicDate = gankDailyModel.getPublishedAt();
            //截取时间 保留到必要的时间信息 2017-03-10
            gankDailyModel.setPublishedAt(publicDate.substring(0, 10));
            dbHelper.save(gankDailyModel);
        }


        showHtml(gankDailyModel);


    }

    private void showHtml(GankDailyModel gankDailyModel) {


        //补充完整HTML代码
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE HTML>\n")
                .append("<html>\n<head>\n <meta charset=\"utf-8\" />\n")
                .append("\n</head>\n<body")
                .append(gankDailyModel.getContent())
                .append("</body>\n<html>");
        wvGank.loadDataWithBaseURL("", html.toString(), "text/html;UTF-8", null, null);
    }


    @Override
    protected void setupView() {


        new GankPresenter(this);


        wvGank.setWebChromeClient(new WebChromeClient());
        wvGank.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvGank.getSettings().setSupportZoom(false);//禁用放大缩小
        wvGank.getSettings().setJavaScriptEnabled(false);//禁用JS交互
        wvGank.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存


        srlGank.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                GankDailyModel dailyModel = presenter.loadLocalTodayData();

                if (dailyModel == null) {

                    presenter.getEveryDayGank(year, month, day);
                    srlGank.setRefreshing(false);
                    return;

                }

                showHtml(dailyModel);

                srlGank.setRefreshing(false);
            }
        });


    }

}
