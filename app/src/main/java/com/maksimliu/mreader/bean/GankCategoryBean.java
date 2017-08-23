package com.maksimliu.mreader.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MaksimLiu on 2017/3/15.
 */


public class GankCategoryBean implements MultiItemEntity {

    /**
     * _id : 58c259ce421aa90f03345158
     * createdAt : 2017-03-10T15:46:22.219Z
     * desc : 编码中陌生单词不认识？是时候打造一款AndroidStudio单词备忘插件了
     * images : ["http://img.gank.io/b24f270b-ea5c-4e6b-87bb-628c329c01cf"]
     * publishedAt : 2017-03-15T11:47:17.825Z
     * source : web
     * type : Android
     * url : http://www.jianshu.com/p/760c98f682ea
     * used : true
     * who : bolex
     */

    @SerializedName("_id")
    private String _id;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("desc")
    private String desc;
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("source")
    private String source;
    @SerializedName("type")
    private String type;
    @SerializedName("url")
    private String url;
    @SerializedName("used")
    private boolean used;
    @SerializedName("who")
    private String who;
    @SerializedName("images")
    private List<String> images;


    public static final int TEXT_VIEW_TYPE = 1;

    public static final int IMAGE_TEXT_VIEW_TYPE = 2;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public int getItemType() {

        if (images == null) {
            return TEXT_VIEW_TYPE;
        } else {
            return IMAGE_TEXT_VIEW_TYPE;
        }
    }


}
