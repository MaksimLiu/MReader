package com.maksimliu.mreader.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by MaksimLiu on 2017/3/18.
 */

public class ZhiHuTopNewsModel implements Serializable {

    /**
     * image : http://pic4.zhimg.com/58cd9cdfeb831a999fd569471a39cdcb.jpg
     * type : 0
     * id : 9261274
     * ga_prefix : 030411
     * title : 「我就是我，你不舒服是你的事，气死你活该」
     */

    @SerializedName("image")
    private String image;
    @SerializedName("type")
    private int type;
    @SerializedName("id")
    private int id;
    @SerializedName("ga_prefix")
    private String ga_prefix;
    @SerializedName("title")
    private String title;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
