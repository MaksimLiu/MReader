package com.maksimliu.lib.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.maksimliu.lib.R;

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

    private int DEFAULT_SPACE_SIZE;

    public SpaceItemDecoration(int space) {
        this.top = space;
        this.left = space;
        this.right = space;
    }

    public SpaceItemDecoration(Context context) {
        DEFAULT_SPACE_SIZE = context.getResources().getDimensionPixelSize(R.dimen.card_spacing);
        this.top = DEFAULT_SPACE_SIZE;
        this.left = DEFAULT_SPACE_SIZE;
        this.right = DEFAULT_SPACE_SIZE;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.top = top;
        outRect.left = left;
        outRect.right = right;

    }
}
