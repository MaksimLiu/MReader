package com.maksimliu.mreader.views.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.db.model.ZhiHuCommonNewsModel;
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.utils.MLog;
import com.maksimliu.mreader.zhihudaily.ZhiHuDetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public class ZhiHuAdapter extends BaseRvAdapter<ZhiHuCommonNewsModel> {


    private List<ZhiHuCommonNewsModel> commonNewsModels;

    private Context context;


    public ZhiHuAdapter(Context context, List<ZhiHuCommonNewsModel> items) {
        super(context, items);
        this.commonNewsModels = items;
        this.context = context;

    }


    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_text, parent, false);

        return new NewsViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {

        NewsViewHolder newsViewHolder = (NewsViewHolder) holder;

        newsViewHolder.tvHomeTitle.setText(commonNewsModels.get(position).getTitle());
        newsViewHolder.cardZhihuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //绑定获取具体内容的事件

                Intent intent=new Intent(context,ZhiHuDetailActivity.class);
                intent.putExtra("newsId",commonNewsModels.get(position).getId()+"");
                context.startActivity(intent);

            }
        });
        Glide.with(context).load(commonNewsModels.get(position).getImages().get(0)).into(newsViewHolder.ivHome);
    }

    @Override
    public void addItems(List<ZhiHuCommonNewsModel> items) {
        commonNewsModels.addAll(items);

        notifyDataSetChanged();
    }

    @Override
    public void resetItems(List<ZhiHuCommonNewsModel> items) {
        commonNewsModels.clear();
        commonNewsModels.addAll(items);

        notifyDataSetChanged();
    }



    static class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_home)
        ImageView ivHome;
        @BindView(R.id.tv_home_title)
        TextView tvHomeTitle;
        @BindView(R.id.rl_home)
        RelativeLayout rlHome;
        @BindView(R.id.card_zhihu_home)
        CardView cardZhihuHome;

        NewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
