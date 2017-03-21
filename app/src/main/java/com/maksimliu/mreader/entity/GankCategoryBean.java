package com.maksimliu.mreader.entity;

import java.util.List;

/**
 * Created by MaksimLiu on 2017/3/15.
 */

public class GankCategoryBean {


    /**
     * error : false
     * results : [{"_id":"58c259ce421aa90f03345158","createdAt":"2017-03-10T15:46:22.219Z","desc":"编码中陌生单词不认识？是时候打造一款AndroidStudio单词备忘插件了","images":["http://img.gank.io/b24f270b-ea5c-4e6b-87bb-628c329c01cf"],"publishedAt":"2017-03-15T11:47:17.825Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/760c98f682ea","used":true,"who":"bolex"},{"_id":"58c65797421aa90f1317865b","createdAt":"2017-03-13T16:25:59.413Z","desc":"以Android项目为例了解Travis CI的使用","publishedAt":"2017-03-15T11:47:17.825Z","source":"web","type":"Android","url":"https://mp.weixin.qq.com/s?__biz=MzI3ODI3NTU3Mg==&mid=2247483753&idx=1&sn=00a7bced404e340bf4a64a0756afd517&chksm=eb5835f2dc2fbce46d5ac68823bab3e1e8b9ae62339febd48bc673a4d355900d7a64bb9907b0&mpshare=1&scene=1&srcid=03130uTYA7UqtjW8cJCGtogS&key=d17593927da82b11b08880e61e8349e4a307c7f1dfeec7c933ed81cc10531d108373970a84fb88e290d90ae002506ff8fdccaad139406eaeb109f92b76ed0c8bd2ee19ca14de6918f3e3f46323395b24&ascene=0&uin=MTA4NDYwMDI4Mg%3D%3D&devicetype=iMac+MacBookPro11%2C4+OSX+OSX+10.12.2+build(16C67)&version=12010310&nettype=WIFI&fontScale=100&pass_ticket=NUgGEHBSuBid4sBnBK45euKREnaJHtZLK%2F2wj8bpb8lKQ2o8B4s3T9Qy0SWZeIGT","used":true,"who":"Eric"},{"_id":"58c69733421aa90f03345171","createdAt":"2017-03-13T20:57:23.723Z","desc":"简单轻量的Android Router 框架","images":["http://img.gank.io/79d2ca8a-2af8-48fd-baa7-1c5e8c8aca10"],"publishedAt":"2017-03-15T11:47:17.825Z","source":"web","type":"Android","url":"https://github.com/themores/AntCaves","used":true,"who":null},{"_id":"58c88e08421aa90f1317866a","createdAt":"2017-03-15T08:42:48.806Z","desc":"安卓开发测试模板","publishedAt":"2017-03-15T11:47:17.825Z","source":"chrome","type":"Android","url":"https://github.com/jaredsburrows/android-gradle-java-app-template","used":true,"who":"蒋朋"},{"_id":"58c8ab42421aa95810795c6b","createdAt":"2017-03-15T10:47:30.697Z","desc":"Android 流式标签布局","images":["http://img.gank.io/57f31ba9-d015-45db-a202-29ce51c3f363"],"publishedAt":"2017-03-15T11:47:17.825Z","source":"chrome","type":"Android","url":"https://github.com/nex3z/FlowLayout","used":true,"who":"带马甲"},{"_id":"58c8ac54421aa95810795c6c","createdAt":"2017-03-15T10:52:04.465Z","desc":"Android  Holding Button 效果","images":["http://img.gank.io/2f0871b0-056b-4ad9-973f-18cdd8c78d55"],"publishedAt":"2017-03-15T11:47:17.825Z","source":"chrome","type":"Android","url":"https://github.com/dewarder/HoldingButton","used":true,"who":"马夹袋"},{"_id":"58c26dbd421aa90f13178643","createdAt":"2017-03-10T17:11:25.556Z","desc":"2017年伊始，你需要尝试的25个Android第三方库","publishedAt":"2017-03-14T13:17:14.21Z","source":"api","type":"Android","url":"http://blog.csdn.net/crazy1235/article/details/55805071","used":true,"who":"hq"},{"_id":"58c639af421aa95810795c54","createdAt":"2017-03-13T14:18:23.516Z","desc":"NDK Mapping工具","publishedAt":"2017-03-14T13:17:14.21Z","source":"web","type":"Android","url":"https://mp.weixin.qq.com/s?__biz=MzAxNzMxNzk5OQ==&mid=2649485311&idx=1&sn=c8d2fea39f1e85f1eb0d3b8129e1e467&chksm=83f826ffb48fafe93918816e32c820cb404a67f65dfdcb2212dca21f44e3c76d375380979b5d#rd","used":true,"who":"xuyisheng"},{"_id":"58c65e62421aa90efc4fb6be","createdAt":"2017-03-13T16:54:58.706Z","desc":"震惊！如此多的Android开发技巧！！！","publishedAt":"2017-03-14T13:17:14.21Z","source":"web","type":"Android","url":"https://github.com/cctanfujun/android-tips-tricks-cn","used":true,"who":"谈釜君"},{"_id":"58c675cd421aa95810795c59","createdAt":"2017-03-13T18:34:53.286Z","desc":"安卓炫酷日历组件，tile 风格相当漂亮。","images":["http://img.gank.io/a31b6cc4-e03c-4e62-bde0-410ded8052f9","http://img.gank.io/c6e8207c-9808-476c-8ad8-d902be5d9061"],"publishedAt":"2017-03-14T13:17:14.21Z","source":"chrome","type":"Android","url":"https://github.com/MorochoRochaDarwin/OneCalendarView","used":true,"who":"蒋朋"}]
     */

    private boolean error;
    private List<GankCategoryModel> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GankCategoryModel> getResults() {
        return results;
    }

    public void setResults(List<GankCategoryModel> results) {
        this.results = results;
    }



}
