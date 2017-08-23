package com.maksimliu.mreader.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MaksimLiu on 2017/3/18.
 */

public class ZhiHuStories {

    /**
     * images : ["http://pic1.zhimg.com/9e2e2f963d8a959be5e3c06e2e9b8188.jpg"]
     * type : 0
     * id : 9263837
     * ga_prefix : 030415
     * title : 除了冬天的铁栏杆，不能舔的还有这盘世上最苦游戏卡带
     * multipic : true
     */

    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private boolean multipic;

    private List<String> images;

    private String image;

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
