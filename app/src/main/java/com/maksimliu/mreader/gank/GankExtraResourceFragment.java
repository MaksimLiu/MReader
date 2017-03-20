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
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.maksimliu.mreader.MReaderApplication;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.base.LazyFragment;
import com.maksimliu.mreader.bean.GankCategoryBean;
import com.maksimliu.mreader.db.model.GankCategoryModel;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.utils.ACache;
import com.maksimliu.mreader.utils.MLog;
import com.maksimliu.mreader.views.adapter.GankRvAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankExtraResourceFragment extends LazyFragment implements GankCategoryContract.View {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.pb_gank)
    ProgressBar pbGank;

    public GankExtraResourceFragment() {
        // Required empty public constructor
    }


    /**
     * 获取最后一个可见Item位置
     * 用于判断RecyclerView是否已经到达底部
     */
    private int lastVisibleItemPosition;


    private Bundle bundle;

    private GankCategoryContract.Presenter presenter;

    private List<GankCategoryModel> items;

    private GankRvAdapter adapter;

    private int page = 1;

    private ACache aCache;

    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.universal_list_card, container, false);
        ButterKnife.bind(this, view);
        setupView();
        isPrepared = true;
        lazyLoadData();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bundle = outState;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGankCategoryEvent(EventManager.GankCategory androidEvent) {


        if (androidEvent == EventManager.GankCategory.ERROR) {

            int error_code = (int) androidEvent.getObject();
            switch (error_code) {

                case GankCategoryContract.NO_CATEGORY_CACHE:
                    presenter.fetchCategory(GankHomeContract.EXTRA_RESOURCE, page + "");
                    break;
            }
            return;
        }


        if (!EventManager.GankCategory.EXTRA_RESOURCE.equals(androidEvent)) {
            MLog.i("is not EXTRA_RESOURCE Event");
            return;
        }
        MLog.i("is  EXTRA_RESOURCE Event");

        GankCategoryBean bean = (GankCategoryBean) androidEvent.getObject();
//        ((GankAdapter)recyclerView.getAdapter()).setShowFooter(false);
//        ((GankAdapter)recyclerView.getAdapter()).addItems(bean.getResults());

        aCache.put("gank_category"+GankHomeContract.EXTRA_RESOURCE,gson.toJson(bean));

        ((GankRvAdapter) recyclerView.getAdapter()).addData(bean.getResults());
//        adapter.loadMoreComplete();
    }

    @Override
    protected void setupView() {


        new GankCategoryPresenter(this);

        aCache = ACache.get(MReaderApplication.getContext());

        gson = new Gson();

        adapter = new GankRvAdapter(this, new ArrayList<GankCategoryModel>());

        adapter.setEnableLoadMore(true);

        recyclerView.setAdapter(adapter);


        //上拉刷新
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //判断是否处于滑动状态并且已经到达了最底部
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition + 1 == recyclerView.getAdapter().getItemCount()) {

                    MLog.i("lastVisibleItemPosition:    " + lastVisibleItemPosition);
                    MLog.i("rvZhihu.getAdapter().getItemCount() :" + recyclerView.getAdapter().getItemCount());
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

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.fetchCategory(GankHomeContract.EXTRA_RESOURCE,page+"");
                swipeRefresh.setRefreshing(false);
            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(new GankAdapter(getActivity(), new ArrayList<GankCategoryModel>()));

    }

    private void loadMore() {


        page++;
        presenter.fetchCategory(GankHomeContract.EXTRA_RESOURCE, page + "");

        adapter.setLoading(false);
    }


    @Override
    public void setPresenter(GankCategoryContract.Presenter presnter) {

        this.presenter = presnter;
    }

    @Override
    public void showError(String errorMsg) {

        Snackbar.make(recyclerView, errorMsg, Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.fetchCategory(GankHomeContract.EXTRA_RESOURCE, page + "");
            }
        });

    }

    @Override
    protected void lazyLoadData() {
        if (!isPrepared || !isVisible) {
            return;
        }
        MLog.i("lazyLoadData\t" + this.getClass().getSimpleName() + "\t" + isVisible);
        presenter.loadCategory(GankHomeContract.EXTRA_RESOURCE);
    }
}
