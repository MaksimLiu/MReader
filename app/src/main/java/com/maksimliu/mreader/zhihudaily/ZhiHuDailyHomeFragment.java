package com.maksimliu.mreader.zhihudaily;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maksimliu.mreader.R;
import com.maksimliu.mreader.base.EventFragment;
import com.maksimliu.mreader.bean.ZhiHuDailyNewsBean;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.utils.DateUtil;
import com.maksimliu.mreader.utils.MLog;
import com.maksimliu.mreader.utils.SpaceItemDecoration;
import com.maksimliu.mreader.views.adapter.NewsRvAdapter;
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
public class ZhiHuDailyHomeFragment extends EventFragment implements ZhiHuDailyContract.View {


    @BindView(R.id.rv_zhihu)
    RecyclerView rvZhihu;
    @BindView(R.id.fab_zhihu)
    FloatingActionButton fabZhihu;
    @BindView(R.id.srl_zhihu)
    SwipeRefreshLayout srlZhihu;


    /**
     * 获取最后一个可见Item位置
     * 用于判断RecyclerView是否已经到达底部
     */
    private int lastVisibleItemPosition;


    private ZhiHuDailyContract.Presenter presenter;


    private Bundle bundle;




    public ZhiHuDailyHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onPause() {
        super.onPause();
        MLog.i("onPause");
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
        MLog.i("onCreateView");
        View view = inflater.inflate(R.layout.fragment_zhi_hu_daily_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (bundle == null) {

            presenter.getLatestNews();
        }

    }


    private Calendar calendar;

    private void loadMore() {

        MLog.i("LoadMore");
        //每次请求在当前日期减一天
        presenter.getOldNews(DateUtil.subDateForApi(calendar), 1);
        ((NewsRvAdapter) rvZhihu.getAdapter()).setLoading(false);

       MLog.i("isLoading"+ ((NewsRvAdapter) rvZhihu.getAdapter()).isLoading());
    }


    @Override
    public void setPresenter(ZhiHuDailyContract.Presenter presenter) {


        this.presenter = presenter;


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onZhiHuDailyNewsEvent(EventManager.ZhiHuDailyNews event) {


        if (event == null) {
            return;
        }

        MLog.i("onZhiHuDailyNewsEvent");


        if (event == EventManager.ZhiHuDailyNews.POST_NEWS_ID) {

            MLog.i("POST_NEWS_ID");
            ZhiHuDailyNewsBean.StoriesBean stories = (ZhiHuDailyNewsBean.StoriesBean) event.getObject();
            presenter.getNewsDetail(stories.getId() + "");
            return;
        }

        ZhiHuDailyNewsBean news = (ZhiHuDailyNewsBean) event.getObject();

        NewsRvAdapter adapter = (NewsRvAdapter) rvZhihu.getAdapter();

        if (event == EventManager.ZhiHuDailyNews.GET_LATEST) {


            MLog.i("GET_LATEST");

            adapter.resetItems(news.getStories());
            calendar = Calendar.getInstance();//时间重置回今天

        } else if (event == EventManager.ZhiHuDailyNews.ADD_OLD_NEWS) {

            MLog.i("ADD_OLD_NEWS");
            adapter.addItems(news.getStories());

        } else if (event == EventManager.ZhiHuDailyNews.SET_OLD_NEWS) {
            MLog.i("SET_OLD_NEWS");

            adapter.resetItems(news.getStories());
            MLog.i("mYear:   "+ mYear +"\n"+"mMonth:  "+ mMonth +"\n"+"mDay: "+ mDay);
            calendar.set(mYear, mMonth, mDay);//时间重置当前选择的时间
        }


    }


    /**
     * 当前选择的年份
     */
    private  int mYear =0;
    /**
     * 当前选择的月份
     */
    private  int mMonth =0;
    /**
     * 当前选择的天数
     */
    private  int mDay =0;

    @OnClick(R.id.fab_zhihu)
    public void onClick() {


        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {



                mYear=year;
                mDay=dayOfMonth;
                mMonth=monthOfYear;

                presenter.getOldNews(DateUtil.convertDateForZhiHuApi(year, monthOfYear, dayOfMonth), 2);

            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

        //知乎日报API最早可获取到2013年5月20号的信息
        datePickerDialog.setMinDate(new GregorianCalendar(2013, 4, 20));

        //最多可查看到昨天的信息n
        now.add(Calendar.DAY_OF_MONTH,-1);
        datePickerDialog.setMaxDate(now);

        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");


    }

    @Override
    public void showError(String msg) {

        Snackbar.make(rvZhihu, msg, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.getLatestNews();
                    }
                })
                .show();
    }

    @Override
    protected void setupView() {


        //给RecyclerView item设置间距
        int space = getResources().getDimensionPixelOffset(R.dimen.card_spacing);
        rvZhihu.addItemDecoration(new SpaceItemDecoration(space));


        rvZhihu.setHasFixedSize(true);

        rvZhihu.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvZhihu.setAdapter(new NewsRvAdapter(getActivity(), new ArrayList<ZhiHuDailyNewsBean.StoriesBean>()));


        //下拉刷新
        srlZhihu.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                presenter.getLatestNews();
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

                    MLog.i("lastVisibleItemPosition:    " + lastVisibleItemPosition);
                    MLog.i("rvZhihu.getAdapter().getItemCount() :" + rvZhihu.getAdapter().getItemCount());
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


        //获取当前时间
        calendar = Calendar.getInstance();

        new ZhiHuDailyPresenter(this);

    }
}
