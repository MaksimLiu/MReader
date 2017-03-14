package com.maksimliu.mreader.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.maksimliu.mreader.R;
import com.maksimliu.mreader.utils.MLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MaksimLiu on 2017/3/7.
 * <h3>RecyclerView Adapter 基类</h3>
 */

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter {


    /**
     * Footer View Type
     */
    private static final int VIEW_TYPE_FOOTER = 1;

    /**
     * Item View Type
     */
    private static final int VIEW_TYPE_ITEM = 2;


    /**
     * 是否处于加载状态
     */
    private boolean isLoading;

    /**
     * 是否展示页尾加载Progressbar,默认开启
     */
    private boolean isShowFooter = true;

    private Context context;

    protected List<T> items;

    protected T tItems;


    public BaseRvAdapter(Context context,List<T>... items1) {

        this.context = context;
    }



    public BaseRvAdapter(Context context, List<T> items) {

        this.items = items;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == VIEW_TYPE_FOOTER && isShowFooter) {

            View view = LayoutInflater.from(context).inflate(R.layout.view_footer_recyclerview, parent, false);

            return new FooterViewHolder(view);

        } else {
            return onCreateItemViewHolder(parent, viewType);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        switch (getItemViewType(position)) {

            case VIEW_TYPE_FOOTER:

                if (isShowFooter) {
                    onBindFooterViewHolder(holder, position, isLoading);
                }
                break;

            case VIEW_TYPE_ITEM:
                onBindItemViewHolder(holder, position);
                break;
        }


    }

    private void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position, boolean isLoading) {

        FooterViewHolder viewHolder = (FooterViewHolder) holder;
        if (isLoading) {

            MLog.i("Progressbar isLoading   " + isLoading);
            viewHolder.pbFooter.setVisibility(View.GONE);


        } else {
            MLog.i("Progressbar isLoading   " + isLoading);
            viewHolder.pbFooter.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {

        if (items == null || items.size() == 0) {
            return 0;
        }
        return isShowFooter ? items.size() + 1 : items.size(); //+1 留空间给Footer
    }

    @Override
    public int getItemViewType(int position) {


        return position + 1 == getItemCount() ? VIEW_TYPE_FOOTER : VIEW_TYPE_ITEM;
    }

    public void setLoading(boolean flag) {

        isLoading = flag;
    }

    public void setShowFooter(boolean flag) {
        this.isShowFooter = flag;
    }


    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pb_footer)
        ProgressBar pbFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    protected abstract RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parentm, int viewType);

    protected abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position);

    public abstract void addItems(List<T> items);

    public abstract void resetItems(List<T> items);


}
