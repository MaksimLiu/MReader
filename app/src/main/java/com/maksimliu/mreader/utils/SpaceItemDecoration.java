package com.maksimliu.mreader.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by MaksimLiu on 2017/3/5.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int top;
    private int bottom;
    private int left;
    private int right;

    public SpaceItemDecoration(int start, int end, int top, int bottom) {
        this.bottom = bottom;
        this.top = top;
        this.left = start;
        this.right = end;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {


            outRect.bottom = bottom;
            outRect.top = top;
            outRect.left = left;
            outRect.right = right;

    }
}
