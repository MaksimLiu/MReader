package com.maksimliu.mreader.views.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.bean.ZhiHuNewsBean;
import com.maksimliu.mreader.bean.ZhiHuStories;

/**
 * Created by MaksimLiu on 2017/3/18.
 */

public class ZhiHuNewsAdapter extends BaseQuickAdapter<ZhiHuStories, BaseViewHolder> {


    public ZhiHuNewsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhiHuStories item) {

        helper.setText(R.id.tv_home_title, item.getTitle());
        Glide.with(mContext).load(item.getImages().get(0)).into((ImageView) helper.getView(R.id.iv_home));





    }
}
