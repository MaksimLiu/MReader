package com.maksimliu.mreader.views.adapter;

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
import com.maksimliu.mreader.event.EventManager;
import com.maksimliu.mreader.utils.MLog;
import com.maksimliu.mreader.zhihudaily.ZhiHuDailyDetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public class NewsRvAdapter extends BaseRvAdapter<com.maksimliu.mreader.bean.ZhiHuDailyNews.StoriesBean> {


    private List<com.maksimliu.mreader.bean.ZhiHuDailyNews.StoriesBean> storiesBeanList;

    private Context context;


    public NewsRvAdapter(Context context, List<com.maksimliu.mreader.bean.ZhiHuDailyNews.StoriesBean> items) {
        super(context, items);
        this.storiesBeanList = items;
        this.context = context;

    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_zhihudaily, parent, false);

        return new NewsViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {

        NewsViewHolder newsViewHolder = (NewsViewHolder) holder;

        newsViewHolder.tvHomeTitle.setText(storiesBeanList.get(position).getTitle());
        newsViewHolder.cardZhihuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //绑定获取具体内容的事件
                EventManager.ZhiHuDailyNews event = EventManager.ZhiHuDailyNews.POST_NEWS_ID;
                event.setObject(storiesBeanList.get(position));


                EventBus.getDefault().post(event);
                MLog.i(storiesBeanList.get(position).getTitle());
                context.startActivity(new Intent(context, ZhiHuDailyDetailActivity.class));
            }
        });
        Glide.with(context).load(storiesBeanList.get(position).getImages().get(0)).into(newsViewHolder.ivHome);
    }


    /**
     * @param latest
     */
    @Override
    public void addItems(List<com.maksimliu.mreader.bean.ZhiHuDailyNews.StoriesBean> latest) {


        storiesBeanList.addAll(latest);

        notifyDataSetChanged();
    }

    @Override
    public void resetItems(List<com.maksimliu.mreader.bean.ZhiHuDailyNews.StoriesBean> items) {

        storiesBeanList.clear();
        storiesBeanList.addAll(items);

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
