package com.maksimliu.mreader.zhihudaily;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.maksimliu.lib.utils.SpaceItemDecoration;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.base.BaseRxFragment;
import com.maksimliu.mreader.bean.ZhiHuNewsBean;
import com.maksimliu.mreader.bean.ZhiHuStories;
import com.maksimliu.mreader.utils.DateUtil;
import com.maksimliu.mreader.views.adapter.ZhiHuNewsAdapter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhiHuHomeFragment extends BaseRxFragment implements ZhiHuHomeContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_zhihu)
    RecyclerView rvZhihu;
    @BindView(R.id.srl_zhihu)
    SwipeRefreshLayout srlZhihu;
    @BindView(R.id.fab_zhihu)
    FloatingActionButton fabZhihu;


    private ZhiHuHomePresenter mPresenter;

    private ZhiHuNewsAdapter mAdapter;


    public ZhiHuHomeFragment() {
        // Required empty public constructor
    }

    public static ZhiHuHomeFragment newInstance() {
        return new ZhiHuHomeFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhi_hu_daily_home;
    }


    private Calendar calendar;


    public void setPresenter(ZhiHuHomeContract.Presenter mPresenter) {

    }

    /**
     * 当前选择的年份
     */
    private int mYear = 0;
    /**
     * 当前选择的月份
     */
    private int mMonth = 0;
    /**
     * 当前选择的天数
     */
    private int mDay = 0;

    Calendar now = Calendar.getInstance();

    @OnClick(R.id.fab_zhihu)
    public void onClick() {


        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                mYear = year;
                mDay = dayOfMonth;
                mMonth = monthOfYear;
                mPresenter.getOldNews(DateUtil.convertDateForZhiHuApi(year, monthOfYear, dayOfMonth), ZhiHuHomeContract.SET_OLD_NEWS);
                //时间重置到当前选择的时间
                now.set(mYear, mMonth, mDay);

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        //知乎日报API最早可获取到2013年5月20号的信息
        datePickerDialog.setMinDate(new GregorianCalendar(2013, 4, 20));


        datePickerDialog.setMaxDate(calendar);
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");


    }

    @Override
    public void showError(String msg) {

        Snackbar.make(rvZhihu, msg, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.getLatestNews();
                    }
                })
                .show();
    }

    @Override
    public void setIsLoading(boolean flag) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getLatestNews();
    }

    @Override
    protected void initVariable() {

        mPresenter = new ZhiHuHomePresenter(this);

        mAdapter = new ZhiHuNewsAdapter(R.layout.item_image_text);

    }


    @Override
    public void initView(Bundle savedInstanceState) {


        getActivity().findViewById(R.id.tab).setVisibility(View.GONE);
//        getActivity().getActionBar().setTitle("知乎日报");


        rvZhihu.addItemDecoration(new SpaceItemDecoration(getActivity()));
        rvZhihu.setHasFixedSize(true);
        rvZhihu.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvZhihu.setAdapter(mAdapter);


    }

    @Override
    public void initListener() {

        mAdapter.setOnLoadMoreListener(this, rvZhihu);
        srlZhihu.setOnRefreshListener(this);


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int i) {
                List<ZhiHuStories> stories = adapter.getData();
                startActivity(ZhiHuDetailActivity.newIntent(getActivity(), stories.get(i).getId() + ""));
            }
        });


    }

    @Override
    public void onLoadMoreRequested() {

        mPresenter.getOldNews(DateUtil.subDateForApi(now), ZhiHuHomeContract.ADD_OLD_NEWS);

    }

    @Override
    public void onRefresh() {


        mPresenter.getLatestNews();
        srlZhihu.setRefreshing(false);

        now = Calendar.getInstance(); //时间重置回今天

    }

    @Override
    public void bindData(ZhiHuNewsBean bean) {
        mAdapter.setNewData(bean.getStories());
    }

    @Override
    public void bindNewsData(ZhiHuNewsBean bean) {

        if (bean != null && !bean.getStories().isEmpty()) {
            mAdapter.addData(bean.getStories());
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
        }


    }
}
