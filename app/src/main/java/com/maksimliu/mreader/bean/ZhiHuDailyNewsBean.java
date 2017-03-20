package com.maksimliu.mreader.bean;

import com.google.gson.annotations.SerializedName;
import com.maksimliu.mreader.db.model.ZhiHuCommonNewsModel;
import com.maksimliu.mreader.db.model.ZhiHuTopNewsModel;

import java.io.Serializable;
import java.util.List;

import io.realm.annotations.Ignore;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public class ZhiHuDailyNewsBean implements Serializable{


    /**
     * date : 20170304
     * stories : [{"images":["http://pic1.zhimg.com/9e2e2f963d8a959be5e3c06e2e9b8188.jpg"],"type":0,"id":9263837,"ga_prefix":"030415","title":"除了冬天的铁栏杆，不能舔的还有这盘世上最苦游戏卡带"},{"images":["http://pic1.zhimg.com/ff4d6cac8b78c7d6d30750bda952a708.jpg"],"type":0,"id":9263609,"ga_prefix":"030414","title":"同为会计，为什么有的人月薪三千，而有的人月薪上万？"},{"images":["http://pic1.zhimg.com/114d2b0e469851171f3665ca0c15ecfc.jpg"],"type":0,"id":9258770,"ga_prefix":"030413","title":"天使投资、VC 以及 PE 的区别是什么？"},{"images":["http://pic2.zhimg.com/db492fa8956758c337517c6f50c3f591.jpg"],"type":0,"id":9262437,"ga_prefix":"030412","title":"大误 · 互联网创业公司の黑话指南"},{"images":["http://pic1.zhimg.com/2ae8500ba719aa644a9e7136e9343570.jpg"],"type":0,"id":9261274,"ga_prefix":"030411","title":"「我就是我，你不舒服是你的事，气死你活该」"},{"title":"iPhone 上一个小功能，让你免费下载家人买的 app，还能在关键时省不少事","ga_prefix":"030410","images":["http://pic3.zhimg.com/f000e2d6beea7fdc7c0e49ab228bbdf6.jpg"],"multipic":true,"type":0,"id":9261457},{"images":["http://pic4.zhimg.com/32e4f52ae2999ca3958b5f816668634b.jpg"],"type":0,"id":9261378,"ga_prefix":"030409","title":"生孩子会影响女性收入吗？来自丹麦的证据"},{"images":["http://pic3.zhimg.com/4d56c8c1f5a240a0f1b75f14992a15de.jpg"],"type":0,"id":9263276,"ga_prefix":"030408","title":"都说经济不景气，但「消费升级」依然是个热门话题"},{"images":["http://pic4.zhimg.com/0f5d779d5bed5f9ef6b86f4d9cedcb07.jpg"],"type":0,"id":9260404,"ga_prefix":"030407","title":"要说把团体领袖打造成网红这事，怎么能不提特朗普呢？"},{"title":"春天到了，出去走走吧，不要浪费了这么美的山川","ga_prefix":"030407","images":["http://pic1.zhimg.com/db84d43ef5f0f354b0183ff20c355164.jpg"],"multipic":true,"type":0,"id":9263093},{"images":["http://pic2.zhimg.com/48f24810fe2ee9f4c17534f1a6baebbd.jpg"],"type":0,"id":9263190,"ga_prefix":"030407","title":"咦，运动品牌的代言人，怎么慢慢从运动员变成了明星？"},{"images":["http://pic4.zhimg.com/c107109e0c8b1ae4e853d389fbd3fbdf.jpg"],"type":0,"id":9263495,"ga_prefix":"030406","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic4.zhimg.com/58cd9cdfeb831a999fd569471a39cdcb.jpg","type":0,"id":9261274,"ga_prefix":"030411","title":"「我就是我，你不舒服是你的事，气死你活该」"},{"image":"http://pic2.zhimg.com/edde5b962b2dfbc696d141df5159c0ad.jpg","type":0,"id":9263190,"ga_prefix":"030407","title":"咦，运动品牌的代言人，怎么慢慢从运动员变成了明星？"},{"image":"http://pic4.zhimg.com/4fec3dbee2043a4d1b4e5c43021151df.jpg","type":0,"id":9260628,"ga_prefix":"030318","title":"还记得拿着文曲星玩《英雄坛说》的日子吗？"},{"image":"http://pic2.zhimg.com/085844dda675e1c248ef12bb92ab5cd5.jpg","type":0,"id":9262919,"ga_prefix":"030316","title":"为什么《比利 · 林恩的中场战事》没有获得奥斯卡提名？"},{"image":"http://pic3.zhimg.com/a4f456692c4fca8c703c459c1ea12bbe.jpg","type":0,"id":9262827,"ga_prefix":"030315","title":"Nintendo Switch 开售，不过先别急着买"}]
     */

    @SerializedName("date")
    private String date;
    @SerializedName("stories")
    private List<ZhiHuCommonNewsModel> commonNewsModels;
    @SerializedName("top_stories")
    private List<ZhiHuTopNewsModel> topNewsModels;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhiHuCommonNewsModel> getCommonNewsModels() {
        return commonNewsModels;
    }

    public void setCommonNewsModels(List<ZhiHuCommonNewsModel> stories) {
        this.commonNewsModels = stories;
    }

    public List<ZhiHuTopNewsModel> getTopNewsModels() {
        return topNewsModels;
    }

    public void setTopNewsModels(List<ZhiHuTopNewsModel> top_stories) {
        this.topNewsModels = top_stories;
    }


}
