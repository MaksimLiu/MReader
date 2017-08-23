package com.maksimliu.mreader;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.maksimliu.mreader.base.BaseRxActivity;
import com.maksimliu.mreader.gank.GankCatogoryFragment;
import com.maksimliu.mreader.utils.FragmentUtil;
import com.maksimliu.mreader.zhihudaily.ZhiHuHomeFragment;

public class MainActivity extends BaseRxActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolbar;


    private ZhiHuHomeFragment zhiHuDailyHomeFragment;

    private GankCatogoryFragment gankCatogoryFragment;

    private DrawerLayout drawer;

    private Fragment currentFragment;

    private MenuItem menuItem;


    @Override
    protected void initVariable() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initDrawer();
        initFragment();
        if (savedInstanceState == null) {

            FragmentUtil.addFragment(getFragmentManager(), zhiHuDailyHomeFragment);
            currentFragment = zhiHuDailyHomeFragment;
        }
    }


    private void initFragment() {

        zhiHuDailyHomeFragment = new ZhiHuHomeFragment();
        gankCatogoryFragment = new GankCatogoryFragment();

    }

    private void initDrawer() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerMenuToggle toggle = new DrawerMenuToggle(
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
        menuItem = item;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleNavItemSelected(MenuItem item) {
        TabLayout tabLayout = ((TabLayout) findViewById(R.id.tab));

        switch (item.getItemId()) {

            case R.id.nav_zhihu:

                tabLayout.setVisibility(View.GONE);
                toolbar.setTitle(getString(R.string.zhihudaily));

                if (currentFragment == zhiHuDailyHomeFragment) {
                    break;
                }

                FragmentUtil.switchFragment(currentFragment, zhiHuDailyHomeFragment);
                currentFragment = zhiHuDailyHomeFragment;

                break;

            case R.id.nav_gank:

                tabLayout.setVisibility(View.VISIBLE);
                toolbar.setTitle(R.string.gank);

                if (currentFragment == gankCatogoryFragment) {
                    break;
                }
                FragmentUtil.switchFragment(currentFragment, gankCatogoryFragment);
                currentFragment = gankCatogoryFragment;

                break;

            case R.id.nav_setting:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;

            default:
                break;
        }
    }

    public class DrawerMenuToggle extends ActionBarDrawerToggle {

        public DrawerMenuToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
          if (menuItem!=null){
              handleNavItemSelected(menuItem);
          }

        }
    }

}
