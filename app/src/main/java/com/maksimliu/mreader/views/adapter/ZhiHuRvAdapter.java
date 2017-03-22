package com.maksimliu.mreader.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.entity.ZhiHuCommonNewsModel;
import com.maksimliu.mreader.zhihudaily.ZhiHuDetailActivity;

import java.util.List;

/**
 * Created by MaksimLiu on 2017/3/18.
 */

public class ZhiHuRvAdapter extends BaseMultiItemQuickAdapter<ZhiHuCommonNewsModel, BaseViewHolder> implements RvAdapter {


    private boolean isShowLoader;

    private Context context;


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ZhiHuRvAdapter(Context context, List<ZhiHuCommonNewsModel> data) {
        super(data);
        this.context = context;
        addItemType(IMAGE_TEXT_VIEW_TYPE, R.layout.item_image_text);
        addItemType(FOOT_LOADER_VIEW_TYPE, R.layout.view_footer_recyclerview);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ZhiHuCommonNewsModel item) {

        switch (helper.getItemViewType()) {

            case IMAGE_TEXT_VIEW_TYPE:

                helper.setText(R.id.tv_home_title, item.getTitle());
                Glide.with(context).load(item.getImages().get(0)).into((ImageView) helper.getView(R.id.iv_home));

                helper.setOnClickListener(R.id.card_zhihu_home, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //绑定获取具体内容的事件

                        Intent intent = new Intent(context, ZhiHuDetailActivity.class);
                        intent.putExtra("newsId", item.getId() + "");
                        context.startActivity(intent);
                    }
                });
                break;

            case FOOT_LOADER_VIEW_TYPE:

                if (isShowLoader) {
                    helper.setVisible(R.id.pb_footer, false);
                } else {
                    helper.setVisible(R.id.pb_footer, true);

                }

                break;

        }

    }

    @Override
    public int getItemViewType(int position) {

        return position + 1 == getItemCount() ? FOOT_LOADER_VIEW_TYPE : IMAGE_TEXT_VIEW_TYPE;
    }

    @Override
    public void setLoading(boolean flag) {
        this.isShowLoader = flag;
    }
}
