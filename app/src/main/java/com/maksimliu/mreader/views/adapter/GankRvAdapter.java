package com.maksimliu.mreader.views.adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.db.model.GankCategoryModel;
import com.maksimliu.mreader.gank.GankFuLiFragment;

import java.util.List;

/**
 * Created by MaksimLiu on 2017/3/16.
 */

public class GankRvAdapter extends BaseMultiItemQuickAdapter<GankCategoryModel, BaseViewHolder> implements RvAdapter {


    private Context context;

    private static final int TEXT_VIEW_TYPE = 1;
    private static final int IMAGE_TEXT_VIEW_TYPE = 2;
    private static final int IMAGE_VIEW_TYPE = 3;
    private static final int FOOT_LOADER_VIEW_TYPE = 4;

    private boolean isLoader;

    private List<GankCategoryModel> models;

    private Fragment fragment;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GankRvAdapter(Fragment fragment, List<GankCategoryModel> data) {
        super(data);
        this.fragment = fragment;
        this.context = fragment.getActivity();
        this.models = data;
        addItemType(TEXT_VIEW_TYPE, R.layout.item_gank_category_text);
        addItemType(IMAGE_TEXT_VIEW_TYPE, R.layout.item_gank_category_image_text);
        addItemType(IMAGE_VIEW_TYPE,R.layout.item_image_card);
        addItemType(FOOT_LOADER_VIEW_TYPE, R.layout.view_footer_recyclerview);


    }

    @Override
    protected void convert(final BaseViewHolder helper, final GankCategoryModel item) {

        switch (helper.getItemViewType()) {
            case TEXT_VIEW_TYPE:
                helper.setText(R.id.tv_gank_category_title, item.getDesc());
                helper.setText(R.id.tv_gank_category_author, item.getWho());

                helper.setOnClickListener(R.id.card_gank_category_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(item.getUrl()));
                        context.startActivity(intent);
                    }
                });
                break;
            case IMAGE_TEXT_VIEW_TYPE:
                helper.setText(R.id.tv_gank_category_title, item.getDesc());
                helper.setText(R.id.tv_gank_category_author, item.getWho());

                helper.setOnClickListener(R.id.card_gank_category_image, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(item.getUrl()));
                        context.startActivity(intent);

                    }
                });
                Glide.with(context).load(item.getImages().get(0)).into((ImageView) helper.getView(R.id.iv_gank_category));
                break;
            case FOOT_LOADER_VIEW_TYPE:
                if (isLoader) {
                    helper.setVisible(R.id.pb_footer, false);
                } else {
                    helper.setVisible(R.id.pb_footer, true);

                }
                break;
            case IMAGE_VIEW_TYPE:

                Glide.with(context).load(item.getUrl()).into((ImageView) helper.getView(R.id.iv_item_card));
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {

        super.getItemViewType(position);

        if (fragment instanceof GankFuLiFragment) {
            return IMAGE_VIEW_TYPE;
        } else {

            if (position + 1 == getItemCount()) {
                return FOOT_LOADER_VIEW_TYPE;
            }

            if (models.get(position).getImages() == null ||
                    models.get(position).getImages().isEmpty()) {
                return TEXT_VIEW_TYPE;
            }
            return IMAGE_TEXT_VIEW_TYPE;
        }



    }


    @Override
    public void setLoading(boolean flag) {
        this.isLoader = flag;
    }
}




