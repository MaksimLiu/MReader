package com.maksimliu.mreader.utils;



import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.maksimliu.mreader.R;

/**
 * Created by MaksimLiu on 2017/3/3.
 */

public class FragmentUtil {

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment) {

        if (fragment != null) {


            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.add(R.id.main_container, fragment);

            ft.addToBackStack(null);
            ft.commit();
        }


    }

    /**
     * Fragment每次切换都会重新实例化，通过show,hide方式避免重复加载数据
     *
     * @param currentFragment
     * @param targetFragment
     */
    public static void switchFragment(Fragment currentFragment, Fragment targetFragment) {


        FragmentManager fm = currentFragment.getFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();



        if (!targetFragment.isAdded()) {//是否已添加过

            ft.hide(currentFragment)
                    .add(R.id.main_container, targetFragment)
                    .commit();
        } else {

            ft.hide(currentFragment)
                    .show(targetFragment)
                    .commit();
        }


    }
}
