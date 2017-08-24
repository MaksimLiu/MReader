package com.maksimliu.mreader.gank.extras;


import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.api.GankApi;
import com.maksimliu.mreader.base.LazyLoadFragment;
import com.maksimliu.mreader.bean.GankCategoryBean;
import com.maksimliu.mreader.gank.GankCategoryContract;
import com.maksimliu.mreader.gank.GankCategoryPresenter;
import com.maksimliu.mreader.utils.SpaceItemDecoration;
import com.maksimliu.mreader.views.adapter.GankNewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankExtrasFragment extends LazyLoadFragment implements GankCategoryContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;


    public GankExtrasFragment() {
        // Required empty public constructor
    }

    public static GankExtrasFragment newInstance() {
        return new GankExtrasFragment();
    }

    private GankCategoryContract.Presenter mPresenter;


    private GankNewsAdapter mAdapter;

    private int page = 1;


    @Override
    protected int getLayoutId() {
        return R.layout.universal_list_card;
    }

    @Override
    protected void initVariable() {
        mAdapter = new GankNewsAdapter(this, new ArrayList<GankCategoryBean>());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(getActivity()));


        mPresenter=new GankCategoryPresenter(this);


    }


    @Override
    protected void initListener() {

        mAdapter.setOnLoadMoreListener(this, recyclerView);
        swipeRefresh.setOnRefreshListener(this);
    }


    public void setPresenter(GankCategoryContract.Presenter mPresenter) {

        this.mPresenter = mPresenter;
    }

    @Override
    public void showError(String errorMsg) {

        Snackbar.make(recyclerView, errorMsg, Snackbar.LENGTH_LONG).setAction(getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.fetchCategory(GankApi.EXTRAS_CATEGORY_TYPE, page);
            }
        });

    }

    @Override
    public void setIsLoading(boolean flag) {
        loadingView.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void lazyLoadData() {
        onRefresh();
    }

    @Override
    public void bindData(List<GankCategoryBean> beanList) {
        if (page == 1) {
            mAdapter.setNewData(beanList);
        } else {
            mAdapter.addData(beanList);
        }
        if (beanList.isEmpty()) {
            mAdapter.loadMoreEnd();
        } else {
            mAdapter.loadMoreComplete();
        }

    }

    @Override
    public void onLoadMoreRequested() {

        page++;
        mPresenter.fetchCategory(GankApi.EXTRAS_CATEGORY_TYPE, page);
    }


    @Override
    public void onRefresh() {

        page = 1;
        mPresenter.fetchCategory(GankApi.EXTRAS_CATEGORY_TYPE, page);
        swipeRefresh.setRefreshing(false);
    }
}
