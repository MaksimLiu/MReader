package com.maksimliu.mreader.gank;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maksimliu.mreader.R;
import com.maksimliu.mreader.base.BaseFragment;
import com.maksimliu.mreader.views.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankFragment extends BaseFragment {


    @BindView(R.id.vp_gank)
    ViewPager vpGank;

    public GankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this, view);
        setupView();
        return view;
    }


    @Override
    protected void setupView() {


        List<String> tabs = new ArrayList<>();
        tabs.add("主页");
        tabs.add("Android");
        tabs.add("iOS");
        tabs.add("前端");
        tabs.add("拓展资源");
        tabs.add("瞎推荐");
        tabs.add("妹子");

        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager(), tabs);

        vpGank.setOffscreenPageLimit(4);
        vpGank.setAdapter(adapter);
        TabLayout tabLayout=((TabLayout)getActivity().findViewById(R.id.tab));
        tabLayout.setupWithViewPager(vpGank);


    }
}
