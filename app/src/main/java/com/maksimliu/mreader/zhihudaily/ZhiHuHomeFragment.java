package com.maksimliu.mreader.zhihudaily;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maksimliu.mreader.R;
import com.maksimliu.mreader.base.EventFragment;
import com.maksimliu.mreader.common.AppConfig;
import com.maksimliu.mreader.entity.ZhiHuNewsBean;
import com.maksimliu.mreader.entity.ZhiHuCommonNewsModel;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.gank.GankHomeContract;
import com.maksimliu.mreader.utils.CacheManager;
import com.maksimliu.mreader.utils.DateUtil;
import com.maksimliu.mreader.utils.MLog;
import com.maksimliu.mreader.utils.SpaceItemDecoration;
import com.maksimliu.mreader.views.adapter.ZhiHuRvAdapter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhiHuHomeFragment extends EventFragment implements ZhiHuHomeContract.View {


    @BindView(R.id.rv_zhihu)
    RecyclerView rvZhihu;
    @BindView(R.id.srl_zhihu)
    SwipeRefreshLayout srlZhihu;
    @BindView(R.id.fab_zhihu)
    FloatingActionButton fabZhihu;
    /**
     * 获取最后一个可见Item位置
     * 用于判断RecyclerView是否已经到达底部
     */
    private int lastVisibleItemPosition;


    private ZhiHuHomeContract.Presenter presenter;


    private Bundle bundle;

    private ZhiHuRvAdapter adapter;


    private CacheManager<ZhiHuNewsBean> cacheManager;

    public ZhiHuHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        bundle = outState;

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

            presenter.loadLatestNews();
        }

    }


    private Calendar calendar;

    private void loadMore() {

        MLog.i("LoadMore");
        //每次请求在当前日期减一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        presenter.loadOldNews(calendar);
        adapter.setLoading(false);

    }


    @Override
    public void setPresenter(ZhiHuHomeContract.Presenter presenter) {


        this.presenter = presenter;


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onZhiHuDailyNewsEvent(EventManager.ZhiHuDailyNews event) {


        if (event == null) {
            return;
        }

        MLog.i("onZhiHuDailyNewsEvent");

        if (event == EventManager.ZhiHuDailyNews.ERROR) {

            int error_code = (int) event.getObject();
            switch (error_code) {

                case ZhiHuHomeContract.NO_LATEST_NEWS_CACHE:

                    presenter.fetchLatestNews();
                    break;
                case ZhiHuHomeContract.NO_OLD_NEWS_CACHE:
                    MLog.i("NO_OLD_NEWS_CACHE");
                    presenter.fetchOldNews(DateFormat.format("yyyyMMdd", calendar).toString(), ZhiHuHomeContract.ADD_OLD_NEWS);
                    break;
            }


            return;

        }

        ZhiHuNewsBean bean = (ZhiHuNewsBean) event.getObject();

        if (event == EventManager.ZhiHuDailyNews.GET_LATEST) {


            MLog.i("GET_LATEST");


            adapter.addData(bean.getCommonNewsModels());


            cacheManager.put(GankHomeContract.HOME+DateUtil.getToday(),bean);
            calendar = Calendar.getInstance();//时间重置回今天

        } else if (event == EventManager.ZhiHuDailyNews.ADD_OLD_NEWS) {

            MLog.i("ADD_OLD_NEWS");

            cacheManager.put(GankHomeContract.HOME+DateUtil.getCurrentDate(calendar),bean);
            adapter.addData(bean.getCommonNewsModels());

        } else if (event == EventManager.ZhiHuDailyNews.SET_OLD_NEWS) {
            MLog.i("SET_OLD_NEWS");

            adapter.setNewData(bean.getCommonNewsModels());
            MLog.i("mYear:   " + mYear + "\n" + "mMonth:  " + mMonth + "\n" + "mDay: " + mDay);
            calendar.set(mYear, mMonth, mDay);//时间重置当前选择的时间
        }

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

    @OnClick(R.id.fab_zhihu)
    public void onClick() {


        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                mYear = year;
                mDay = dayOfMonth;
                mMonth = monthOfYear;

                presenter.fetchOldNews(DateUtil.convertDateForZhiHuApi(year, monthOfYear, dayOfMonth), ZhiHuHomeContract.SET_OLD_NEWS);

            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

        //知乎日报API最早可获取到2013年5月20号的信息
        datePickerDialog.setMinDate(new GregorianCalendar(2013, 4, 20));

        //最多可查看到昨天的信息n
        now.add(Calendar.DAY_OF_MONTH, -1);
        datePickerDialog.setMaxDate(now);

        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");

    }

    @Override
    public void showError(String msg) {

        Snackbar.make(rvZhihu, msg, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.fetchLatestNews();
                    }
                })
                .show();
    }

    @Override
    public void setupView() {

        new ZhiHuHomePresenter(this);

        getActivity().findViewById(R.id.tab).setVisibility(View.GONE);
        adapter =new ZhiHuRvAdapter(getActivity(),new ArrayList<ZhiHuCommonNewsModel>());

        cacheManager = new CacheManager<>(getActivity(), AppConfig.ZHIHU_CACHE_NAME, ZhiHuNewsBean.class);

        //获取当前时间
        calendar = Calendar.getInstance();

        //给RecyclerView item设置间距
        int space = getResources().getDimensionPixelOffset(R.dimen.card_spacing);
        rvZhihu.addItemDecoration(new SpaceItemDecoration(space));
        rvZhihu.setHasFixedSize(true);
        rvZhihu.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvZhihu.setAdapter(adapter);


    }

    @Override
    public void initListener() {


        //下拉刷新
        srlZhihu.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                presenter.fetchLatestNews();
                srlZhihu.setRefreshing(false);
            }
        });


        //上拉刷新
        rvZhihu.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //判断是否处于滑动状态并且已经到达了最底部
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition + 1 == rvZhihu.getAdapter().getItemCount()) {

                    //加载更多
                    loadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取最后一个可见Item的位置
                lastVisibleItemPosition = ((LinearLayoutManager) rvZhihu.getLayoutManager()).findLastVisibleItemPosition();

            }
        });

    }
}
