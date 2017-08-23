package com.maksimliu.mreader.gank.others;


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
import com.maksimliu.mreader.views.adapter.GankNewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankOthersFragment extends LazyLoadFragment implements GankCategoryContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    public static GankOthersFragment newInstance(){
        return new GankOthersFragment();
    }


    private GankCategoryContract.Presenter mPresenter;


    private GankNewsAdapter adapter;

    /**
     * 当前查询页数
     */
    private int page = 1;


    @Override
    protected int getLayoutId() {
        return R.layout.universal_list_card;
    }

    @Override
    protected void initVariable() {

        adapter = new GankNewsAdapter(this, new ArrayList<GankCategoryBean>());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }


    @Override
    protected void initListener() {

        adapter.setOnLoadMoreListener(this, recyclerView);
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
                mPresenter.fetchCategory(GankApi.OTHERS_CATEGORY_TYPE, page);
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
            adapter.setNewData(beanList);
        } else {
            adapter.addData(beanList);
        }
        if (beanList.isEmpty()) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }

    }

    @Override
    public void onLoadMoreRequested() {

        page++;
        mPresenter.fetchCategory(GankApi.OTHERS_CATEGORY_TYPE, page);

    }

    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.fetchCategory(GankApi.OTHERS_CATEGORY_TYPE, page);
        swipeRefresh.setRefreshing(false);
    }
}
