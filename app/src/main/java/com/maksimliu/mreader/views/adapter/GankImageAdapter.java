package com.maksimliu.mreader.views.adapter;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.bean.GankCategoryBean;

/**
 * Created by MaksimLiu on 2017/8/24.
 */

public class GankImageAdapter extends BaseQuickAdapter<GankCategoryBean, BaseViewHolder> {

    public GankImageAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankCategoryBean item) {

        Glide.with(mContext).load(item.getUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_item_card));


    }
}
