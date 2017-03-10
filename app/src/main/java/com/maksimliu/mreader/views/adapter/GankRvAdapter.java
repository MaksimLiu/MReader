package com.maksimliu.mreader.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.bean.GankBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MaksimLiu on 2017/3/10.
 */

public class GankRvAdapter extends BaseRvAdapter<GankBean.ResultsBean.AndroidBean> {

    private List<GankBean.ResultsBean.AndroidBean> items;

    private Context context;

    private static final int VIEW_TYPE_IMAGE = 1;
    private static final int VIEW_TYPE_TEXT = 2;


    public GankRvAdapter(Context context, List<GankBean.ResultsBean.AndroidBean> items) {
        super(context, items);
        this.context = context;
        this.items = items;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {


        View view;
//        if (viewType == VIEW_TYPE_IMAGE) {
//
//            view = LayoutInflater.from(context).inflate(R.layout.item_gank_home_image, parent, false);
//            return new ImageViewHolder(view);
//        } else {

            view = LayoutInflater.from(context).inflate(R.layout.item_gank_home_text, parent, false);
            return new TextViewHolder(view);
//        }

    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//        if (VIEW_TYPE_IMAGE == getItemViewType(position)) {
//
//            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
//
//
//            Glide.with(context).load(items.get(0).get福利().get(0).getUrl()).into(imageViewHolder.tvGankHome);
//        } else {

           if (position<items.size()){
               TextViewHolder textViewHolder = (TextViewHolder) holder;
               textViewHolder.tvGankTitle.setText(items.get(position).getDesc());
               textViewHolder.tvGankAuthor.setText(items.get(position).getWho());
           }
        }



    @Override
    public void addItems(List<GankBean.ResultsBean.AndroidBean> items) {

        items.addAll(items);
        notifyDataSetChanged();

    }

    @Override
    public void resetItems(List<GankBean.ResultsBean.AndroidBean> items) {
        this.items.clear();

        this.items.addAll(items);

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);

        return position == 0 ? VIEW_TYPE_IMAGE : VIEW_TYPE_TEXT;

    }


//    static class ImageViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_gank_home)
//        ImageView tvGankHome;
//
//        ImageViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//    }

    static class TextViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_gank_title)
        TextView tvGankTitle;
        @BindView(R.id.tv_gank_author)
        TextView tvGankAuthor;

        TextViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
