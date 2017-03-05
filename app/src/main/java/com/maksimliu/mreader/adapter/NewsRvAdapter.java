package com.maksimliu.mreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.bean.ZhiHuDailyLatest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public class NewsRvAdapter extends RecyclerView.Adapter<NewsRvAdapter.NewsViewHolder> {


    private List<ZhiHuDailyLatest.StoriesBean> items;

    private Context context;

    public NewsRvAdapter(Context context, List<ZhiHuDailyLatest.StoriesBean> items) {


        this.items = items;
        this.context = context;

    }


    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_zhihudaily, null);

        return new NewsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        holder.tvHomeTitle.setText(items.get(position).getTitle());
        Glide.with(context).load(items.get(position).getImages().get(0)).into(holder.ivHome);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    static class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_home)
        ImageView ivHome;
        @BindView(R.id.tv_home_title)
        TextView tvHomeTitle;

        NewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
