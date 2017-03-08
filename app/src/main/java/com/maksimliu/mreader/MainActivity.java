package com.maksimliu.mreader;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.maksimliu.mreader.base.BaseActivity;
import com.maksimliu.mreader.bean.ZhiHuDailyNews;
import com.maksimliu.mreader.utils.FragmentUtil;
import com.maksimliu.mreader.zhihudaily.ZhiHuDailyHomeFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolbar;

    private ZhiHuDailyHomeFragment zhiHuDailyHomeFragment;


    private Fragment currentFragment;


    @Override
    protected void initView() {
        initDrawer();
        initFragment();
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {



        if (savedInstanceState == null) {


            FragmentUtil.addFragment(getFragmentManager(), zhiHuDailyHomeFragment);

            currentFragment = zhiHuDailyHomeFragment;
        }
    }

    private void initFragment() {

        zhiHuDailyHomeFragment = new ZhiHuDailyHomeFragment();
    }

    private void initDrawer() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.nav_zhihu:
                toolbar.setTitle(getString(R.string.zhihudaily));
                if (currentFragment == zhiHuDailyHomeFragment) {
                    break;
                }
                FragmentUtil.switchFragment(currentFragment, zhiHuDailyHomeFragment);
                currentFragment = zhiHuDailyHomeFragment;

                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(ZhiHuDailyNews latest) {

    }
}
