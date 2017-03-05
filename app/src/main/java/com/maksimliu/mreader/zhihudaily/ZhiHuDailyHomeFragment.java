package com.maksimliu.mreader.zhihudaily;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maksimliu.mreader.R;
import com.maksimliu.mreader.adapter.NewsRvAdapter;
import com.maksimliu.mreader.base.BaseFragment;
import com.maksimliu.mreader.bean.ZhiHuDailyLatest;
import com.maksimliu.mreader.utils.MLog;
import com.maksimliu.mreader.utils.SpaceItemDecoration;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhiHuDailyHomeFragment extends BaseFragment implements ZhiHuDailyContract.View {


    @BindView(R.id.rv_zhihu)
    RecyclerView rvZhihu;
    @BindView(R.id.fab_zhihu)
    FloatingActionButton fabZhihu;

    private ZhiHuDailyContract.Presenter presenter;

    public ZhiHuDailyHomeFragment() {
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvZhihu.setLayoutManager(new LinearLayoutManager(getActivity()));
        int space = getResources().getDimensionPixelOffset(R.dimen.card_spacing);
        //给RecyclerView item设置间距
        rvZhihu.addItemDecoration(new SpaceItemDecoration(space, space, space, space));

        new ZhiHuDailyPresenter(this);

        presenter.getLatestNews();


    }


    @Override
    public void setPresenter(ZhiHuDailyContract.Presenter presenter) {


        this.presenter = presenter;


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onZhiHuDailyHomeEvent(ZhiHuDailyLatest latest) {


        rvZhihu.setAdapter(new NewsRvAdapter(getActivity(), latest.getStories()));

    }

    @OnClick(R.id.fab_zhihu)
    public void onClick() {


        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                monthOfYear++;
                String month;
                String day;

                // API 时间格式为20170305
                // 少于10时拼接0
                if (monthOfYear < 10) {
                    month = "0" + monthOfYear;
                } else {
                    month = monthOfYear + "";
                }
                if (dayOfMonth < 10) {
                    day = "0" + dayOfMonth;
                } else {
                    day = dayOfMonth + "";
                }

                presenter.getOldNews(year + "" + month + day);

            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.setMinDate(new GregorianCalendar(2013, 4, 20));
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
}
