package com.maksimliu.mreader.gank;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maksimliu.mreader.R;
import com.maksimliu.mreader.api.GankApi;
import com.maksimliu.mreader.base.LazyFragment;
import com.maksimliu.mreader.common.AppConfig;
import com.maksimliu.mreader.entity.GankCategoryBean;
import com.maksimliu.mreader.entity.GankCategoryModel;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.utils.CacheManager;
import com.maksimliu.mreader.utils.MLog;
import com.maksimliu.mreader.views.adapter.GankRvAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankAndroidFragment extends LazyFragment implements GankCategoryContract.View {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;


    /**
     * 获取最后一个可见Item位置
     * 用于判断RecyclerView是否已经到达底部
     */
    private int lastVisibleItemPosition;

    private GankCategoryContract.Presenter presenter;

    private GankRvAdapter adapter;

    /**
     * 当前查询页数
     */
    private int page = 1;

    /**
     * 缓存管理器实例
     */
    private CacheManager<GankCategoryBean> cacheManager;



    public GankAndroidFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.universal_list_card, container, false);
        ButterKnife.bind(this, view);
        isPrepared = true;
        return view;
    }


    /**
     * 注意：错误事件必须要在判断是否为符合自身事件之前，
     * 否则当加载不了本地数据，发布错误事件时接受不到信息，从而无法执行错误处理即从网络中获取数据
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGankCategoryEvent(EventManager.GankCategory event) {


        if (event == EventManager.GankCategory.ERROR) {

            int error_code = (int) event.getObject();
            switch (error_code) {

                case GankCategoryContract.NO_ANDROID_CACHE:
                    MLog.i("NO_Android_CATEGORY_CACHE");
                    presenter.fetchCategory(GankApi.ANDROID_CATEGORY_TYPE, page + "");
                    break;
            }
            return;
        }


        if (!EventManager.GankCategory.ANDROID.equals(event)) {
            MLog.i("is not Android Event");
            return;
        }

        MLog.i("is  Android Event");


        GankCategoryBean bean = (GankCategoryBean) event.getObject();
        cacheManager.put(GankApi.ANDROID_CATEGORY_TYPE,bean);

        adapter.addData(bean.getResults());
    }

    @Override
    protected void setupView() {


        new GankCategoryPresenter(this);

        cacheManager=new CacheManager<>(getActivity(), AppConfig.GANK_CACHE_NAME,GankCategoryBean.class);

        adapter = new GankRvAdapter(this, new ArrayList<GankCategoryModel>());

        adapter.setEnableLoadMore(true);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

        //上拉刷新
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //判断是否处于滑动状态并且已经到达了最底部
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition + 1 == recyclerView.getAdapter().getItemCount()) {

                    //加载更多
                    loadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取最后一个可见Item的位置
                lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

            }
        });

        //下拉刷新
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.getData().clear();
                presenter.fetchCategory(GankApi.ANDROID_CATEGORY_TYPE,1+"");
                swipeRefresh.setRefreshing(false);
            }
        });

    }

    private void loadMore() {

        MLog.i("loadMore");
        page++;//查询下一页

        presenter.fetchCategory(GankApi.ANDROID_CATEGORY_TYPE, page + "");

        adapter.setLoading(false);
    }


    @Override
    public void setPresenter(GankCategoryContract.Presenter presnter) {

        this.presenter = presnter;
    }

    @Override
    public void showError(String errorMsg) {

        Snackbar.make(recyclerView, errorMsg, Snackbar.LENGTH_LONG).setAction(getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.fetchCategory(GankApi.ANDROID_CATEGORY_TYPE, page + "");
            }
        });

    }

    @Override
    protected void lazyLoadData() {

        if (!isPrepared || !isVisible) {
            return;
        }
        //加载本地最新数据
        presenter.loadCategory(GankApi.ANDROID_CATEGORY_TYPE);

    }
}
