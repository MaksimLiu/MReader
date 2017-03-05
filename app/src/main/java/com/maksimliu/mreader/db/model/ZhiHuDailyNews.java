package com.maksimliu.mreader.db.model;

import io.realm.RealmObject;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public class ZhiHuDailyNews extends RealmObject {


    /**
     * 新闻ID
     */
    private int id;

    /**
     * 新闻日期
     */
    private String date;
    /**
     * 新闻标题
     */
    private String title;
    /**
     * 新闻图片地址
     */
    private String image_url;

    public String getImage() {
        return image_url;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
