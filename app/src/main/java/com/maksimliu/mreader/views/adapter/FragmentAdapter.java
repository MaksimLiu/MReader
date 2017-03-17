package com.maksimliu.mreader.views.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.maksimliu.mreader.gank.GankAndroidFragment;
import com.maksimliu.mreader.gank.GankExtraResourceFragment;
import com.maksimliu.mreader.gank.GankFrontEndFragment;
import com.maksimliu.mreader.gank.GankFuLiFragment;
import com.maksimliu.mreader.gank.GankHomeFragment;
import com.maksimliu.mreader.gank.GankIOSFragment;
import com.maksimliu.mreader.gank.GankOthersFragment;

import java.util.List;

/**
 * Created by MaksimLiu on 2017/3/14.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<String> tabs;

    public FragmentAdapter(FragmentManager fm, List<String> tabs) {
        super(fm);
        this.tabs = tabs;
    }

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {


        Fragment fragment = null;

        switch (position) {

            case 0:
                fragment = new GankHomeFragment();
                break;
            case 1:
                fragment = new GankAndroidFragment();
                break;
            case 2:
                fragment = new GankIOSFragment();
                break;
            case 3:
                fragment = new GankFrontEndFragment();
                break;
            case 4:
                fragment = new GankExtraResourceFragment();
                break;
            case 5:
                fragment = new GankOthersFragment();
                break;
            case 6:
                fragment=new GankFuLiFragment();
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
