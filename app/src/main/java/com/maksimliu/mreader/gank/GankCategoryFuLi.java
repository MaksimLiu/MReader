package com.maksimliu.mreader.gank;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maksimliu.mreader.R;
import com.maksimliu.mreader.base.EventFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankCategoryFuLi extends EventFragment {


    public GankCategoryFuLi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.universal_list_card, container, false);
    }

    @Override
    protected void setupView() {

    }
}
