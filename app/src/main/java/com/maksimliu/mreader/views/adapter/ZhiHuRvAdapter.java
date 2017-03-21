package com.maksimliu.mreader.views.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maksimliu.mreader.R;
import com.maksimliu.mreader.entity.ZhiHuNewsModel;

import java.util.List;

/**
 * Created by MaksimLiu on 2017/3/18.
 */

public class ZhiHuRvAdapter extends BaseMultiItemQuickAdapter<ZhiHuNewsModel,BaseViewHolder> implements RvAdapter{


    private static final int IMAGE_TEXT_VIEW_TYPE=1;

    private static final int IMAGE_VIEW_TYPE=2;

    private static final int FOOT_LOADER_VIEW_TYPE =3;

    private boolean isShowLoader;


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ZhiHuRvAdapter(List<ZhiHuNewsModel> data) {
        super(data);
        addItemType(IMAGE_TEXT_VIEW_TYPE,R.layout.item_image_text);
        addItemType(FOOT_LOADER_VIEW_TYPE,R.layout.view_footer_recyclerview);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhiHuNewsModel item) {

        switch (helper.getItemViewType()){

            case IMAGE_TEXT_VIEW_TYPE:


                break;

            case FOOT_LOADER_VIEW_TYPE:

                if (isShowLoader){
                    helper.setVisible(R.layout.view_footer_recyclerview,false);
                }else {
                    helper.setVisible(R.layout.view_footer_recyclerview,true);
                }


                break;

        }

    }

    @Override
    public void setLoading(boolean flag) {
        this.isShowLoader=flag;
    }
}
