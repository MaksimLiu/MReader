package com.maksimliu.mreader.views.adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maksimliu.mreader.PhotoViewerActivity;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.bean.GankCategoryBean;

import java.util.List;

import static com.maksimliu.mreader.bean.GankCategoryBean.IMAGE_TEXT_VIEW_TYPE;
import static com.maksimliu.mreader.bean.GankCategoryBean.TEXT_VIEW_TYPE;

/**
 * Created by MaksimLiu on 2017/3/16.
 */

public class GankNewsAdapter extends BaseMultiItemQuickAdapter<GankCategoryBean, BaseViewHolder> {


    private Context context;


    private List<GankCategoryBean> models;

    private Fragment fragment;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GankNewsAdapter(Fragment fragment, List<GankCategoryBean> data) {
        super(data);
        this.fragment = fragment;
        this.context = fragment.getActivity();
        this.models = data;
        addItemType(TEXT_VIEW_TYPE, R.layout.item_gank_category_text);
        addItemType(IMAGE_TEXT_VIEW_TYPE, R.layout.item_gank_category_image_text);

    }


    @Override
    protected void convert(final BaseViewHolder helper, final GankCategoryBean item) {

        switch (helper.getItemViewType()) {
            case TEXT_VIEW_TYPE:
                helper.setText(R.id.tv_gank_category_title, item.getDesc());
                helper.setText(R.id.tv_gank_category_author, item.getWho());

                helper.setOnClickListener(R.id.card_gank_category_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startCustomTabs(item.getUrl());
                    }
                });

                break;
            case IMAGE_TEXT_VIEW_TYPE:
                helper.setText(R.id.tv_gank_category_title, item.getDesc());
                helper.setText(R.id.tv_gank_category_author, item.getWho());


                helper.setOnClickListener(R.id.iv_gank_category, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, PhotoViewerActivity.class);
                        intent.putExtra("imgUrl", item.getImages().get(0));
                        intent.putExtra("desc", item.getDesc());
                        context.startActivity(intent);
                    }
                });

                helper.setOnClickListener(R.id.card_gank_category_image, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startCustomTabs(item.getUrl());
                    }
                });

                Glide.with(context).load(item.getImages().get(0)).into((ImageView) helper.getView(R.id.iv_gank_category));
                break;
            default:
                break;
        }



    }

    private void startCustomTabs(String url) {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setShowTitle(true);
        builder.setToolbarColor(mContext.getResources().getColor(R.color.colorPrimary));
        builder.addDefaultShareMenuItem();
        builder.setStartAnimations(mContext, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(mContext, Uri.parse(url));
    }
}




