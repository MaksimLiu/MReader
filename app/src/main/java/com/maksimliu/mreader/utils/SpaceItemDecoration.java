package com.maksimliu.mreader.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by MaksimLiu on 2017/3/5.
 * <h3><RecyclerView Item间隔类/h3>
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 顶部间隔
     */
    private int top;
    /**
     * 左边间隔
     */
    private int left;
    /**
     * 右边间隔
     */
    private int right;

    public SpaceItemDecoration(int space) {
        this.top = space;
        this.left = space;
        this.right = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.top = top;
        outRect.left = left;
        outRect.right = right;

    }
}
