package com.maksimliu.mreader.event;

import java.util.Objects;

/**
 * Created by MaksimLiu on 2017/3/8.
 * <h3>EventBus 事件统一管理类</h3>
 */

public class EventManager {


    /**
     * 知乎日报新闻简要事件
     */
    public enum ZhiHuDailyNews implements BaseEvent {

        GET_LATEST,//获取最新信息
        ADD_OLD_NEWS,//添加过往信息
        SET_OLD_NEWS,//查看具体某天的信息
        POST_NEWS_ID,//发送某个信息的ID,用来获取该信息的具体内容
        ;

        private Object object;

        @Override
        public void setObject(Object object) {

            this.object = object;
        }

        @Override
        public Object getObject() {
            return object;
        }
    }

    /**
     * 知乎日报详细事件
     */
    public enum ZhiHuDailyNewsDetail implements BaseEvent {

        GET_DETAIL //查看信息的具体内容
        ;

        private Object object;

        @Override
        public void setObject(Object object) {

            this.object = object;
        }

        @Override
        public Object getObject() {
            return object;
        }
    }


}
