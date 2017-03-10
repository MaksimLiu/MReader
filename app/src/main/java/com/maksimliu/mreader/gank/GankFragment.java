package com.maksimliu.mreader.gank;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;

import com.maksimliu.mreader.R;
import com.maksimliu.mreader.base.BaseFragment;
import com.maksimliu.mreader.bean.GankBean;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.views.adapter.GankRvAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankFragment extends BaseFragment implements GankContract.View {


    @BindView(R.id.rv_zhihu)
    RecyclerView rvZhihu;
    @BindView(R.id.srl_zhihu)
    SwipeRefreshLayout srlZhihu;
    @BindView(R.id.fab_zhihu)
    FloatingActionButton fabZhihu;
    private GankContract.Presenter presenter;

    private Bundle bundle;

    public GankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zhi_hu_daily_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (bundle == null) {
            presenter.getEveryDayGank("2017", "03", "09");
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
        Snackbar.make(rvZhihu, errorMsg, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.getEveryDayGank("2017", "03", "09");
                    }
                })
                .show();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGankEvent(EventManager.Gank gankEvent) {


        if (gankEvent == null) {
            return;
        }

        GankRvAdapter rvAdapter = (GankRvAdapter) rvZhihu.getAdapter();

        GankBean.ResultsBean resultsBean = (GankBean.ResultsBean) gankEvent.getObject();


        rvAdapter.resetItems(resultsBean.getAndroid());


    }


    @Override
    protected void initView() {


        new GankPresenter(this);

//
//        wvZhihuDetail.setWebChromeClient(new WebChromeClient());
//        wvZhihuDetail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        wvZhihuDetail.getSettings().setSupportZoom(false);//禁用放大缩小
//        wvZhihuDetail.getSettings().setJavaScriptEnabled(false);//禁用JS交互
//        wvZhihuDetail.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存


        srlZhihu.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                presenter.getEveryDayGank("2017", "03", "09");
                srlZhihu.setRefreshing(false);
            }
        });


        rvZhihu.setHasFixedSize(true);
        rvZhihu.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        rvZhihu.setAdapter(new GankRvAdapter(getActivity(), new ArrayList<GankBean.ResultsBean.AndroidBean>()));
    }

    @OnClick(R.id.fab_zhihu)
    public void onClick() {


    }
}
