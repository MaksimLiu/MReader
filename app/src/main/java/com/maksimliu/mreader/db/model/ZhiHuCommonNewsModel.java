package com.maksimliu.mreader.db.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by MaksimLiu on 2017/3/18.
 */

public class ZhiHuCommonNewsModel extends RealmObject implements Serializable {

    /**
     * images : ["http://pic1.zhimg.com/9e2e2f963d8a959be5e3c06e2e9b8188.jpg"]
     * type : 0
     * id : 9263837
     * ga_prefix : 030415
     * title : 除了冬天的铁栏杆，不能舔的还有这盘世上最苦游戏卡带
     * multipic : true
     */

    @SerializedName("type")
    private int type;
    @SerializedName("id")
    private int id;
    @SerializedName("ga_prefix")
    private String ga_prefix;
    @SerializedName("title")
    private String title;
    @SerializedName("multipic")
    private boolean multipic;
    @Ignore
    @SerializedName("images")
    private List<String> images;

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

    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
