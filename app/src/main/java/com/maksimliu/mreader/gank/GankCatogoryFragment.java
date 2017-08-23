package com.maksimliu.mreader.gank;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.maksimliu.mreader.R;
import com.maksimliu.mreader.base.BaseFragment;
import com.maksimliu.mreader.gank.android.GankAndroidContract;
import com.maksimliu.mreader.gank.android.GankAndroidFragment;
import com.maksimliu.mreader.gank.android.GankAndroidPresenter;
import com.maksimliu.mreader.gank.extras.GankExtrasContract;
import com.maksimliu.mreader.gank.extras.GankExtrasFragment;
import com.maksimliu.mreader.gank.extras.GankExtrasPresenter;
import com.maksimliu.mreader.gank.frontend.GankFrontEndContract;
import com.maksimliu.mreader.gank.frontend.GankFrontEndFragment;
import com.maksimliu.mreader.gank.frontend.GankFrontEndPresenter;
import com.maksimliu.mreader.gank.ios.GankIOSFragment;
import com.maksimliu.mreader.gank.ios.GankiOSPresenter;
import com.maksimliu.mreader.gank.meizhi.GankFuLiFragment;
import com.maksimliu.mreader.gank.meizhi.GankFuliPresenter;
import com.maksimliu.mreader.gank.others.GankOthersFragment;
import com.maksimliu.mreader.gank.others.GankOthersPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankCatogoryFragment extends BaseFragment {


    @BindView(R.id.vp_gank)
    ViewPager vpGank;

    private GankAndroidFragment androidFragment;

    private GankIOSFragment iosFragment;

    private GankFrontEndFragment frontEndFragment;

    private GankExtrasFragment extrasFragment;

    private GankOthersFragment othersFragment;

    private GankFrontEndPresenter frontEndPresenter;

    private GankExtrasPresenter extrasPresenter;

    private GankFuLiFragment fuLiFragment;

    private GankFuliPresenter fuliPresenter;

    private GankAndroidPresenter androidPresenter;

    private GankOthersPresenter othersPresenter;

    private GankiOSPresenter iosPresenter;


    public GankCatogoryFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }


    @Override
    protected void initVariable() {

        androidFragment = GankAndroidFragment.newInstance();
        androidPresenter = new GankAndroidPresenter(androidFragment);

        frontEndFragment = GankFrontEndFragment.newInstance();
        frontEndPresenter = new GankFrontEndPresenter(frontEndFragment);

        extrasFragment = GankExtrasFragment.newInstance();
        extrasPresenter = new GankExtrasPresenter(extrasFragment);

        iosFragment = GankIOSFragment.newInstance();
        iosPresenter = new GankiOSPresenter(iosFragment);

        fuLiFragment = GankFuLiFragment.newInstance();
        fuliPresenter = new GankFuliPresenter(fuLiFragment);

        othersFragment = GankOthersFragment.newInstance();
        othersPresenter = new GankOthersPresenter(othersFragment);


    }

    @Override
    public void initView(Bundle savedInstanceState) {

        List<String> tabs = new ArrayList<>();

//        tabs.add("主页");
        tabs.add("Android");
        tabs.add("iOS");
        tabs.add("前端");
        tabs.add("拓展资源");
        tabs.add("瞎推荐");
        tabs.add("妹子");


        vpGank.setAdapter(new GankFragmentAdapter(getChildFragmentManager(), tabs));
        TabLayout tabLayout = ((TabLayout) getActivity().findViewById(R.id.tab));
        tabLayout.setupWithViewPager(vpGank);

    }

    @Override
    public void initListener() {

    }

    private class GankFragmentAdapter extends FragmentPagerAdapter {

        private List<String> tabs;

        public GankFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public GankFragmentAdapter(FragmentManager fm, @NonNull List<String> tabs) {
            super(fm);
            this.tabs = tabs;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            switch (position) {

                case 0:
                    fragment = androidFragment;
                    break;

                case 1:
                    fragment = iosFragment;
                    break;

                case 2:
                    fragment = frontEndFragment;
                    break;
                case 3:
                    fragment = extrasFragment;
                    break;
                case 4:
                    fragment = othersFragment;
                    break;
                case 5:
                    fragment = fuLiFragment;
                    break;
            }
            return fragment;
        }


        @Override
        public int getCount() {
            return tabs.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }
}
