package com.maksimliu.mreader.api;

import com.maksimliu.mreader.bean.ZhiHuDailyDetail;
import com.maksimliu.mreader.bean.ZhiHuDailyNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by MaksimLiu on 2017/3/3.
 */

public interface ZhiHuDailyApi {


    String BASE_API_4_URL = "https://news-at.zhihu.com/api/4/";


    /**
     * 获取最新消息
     *
     * @return
     */
    @GET("news/latest")
    Call<ZhiHuDailyNews> getLatestNews();

    /**
     * 内容获取与离线下载
     * <p>
     * <p>使用最新消息{@link #getLatestNews()}中获得的 id，拼接在 http://news-at.zhihu.com/api/4/news/后</p>
     *
     * @param id 新闻id
     */
    @GET("news/{id}")
    Call<ZhiHuDailyDetail> getNewsDetail(@Path("id") String id);


    /**
     * 查看过往消息
     * <p>
     * <p>如果需要查询 11 月 18 日的消息，before 后的数字应为 20131119,
     * 知乎日报的生日为 2013 年 5 月 19 日，若 before 后数字小于 20130520 ，只会接收到空消息
     * 输入的今日之后的日期仍然获得今日内容，但是格式不同于最新消息的 JSON 格式</p>
     *
     * @param date 时间
     * @return
     */
    @GET("news/before/{date}")
    Call<ZhiHuDailyNews> getOldeNews(@Path("date") String date);


    /**
     * 获取新闻额外信息
     * <p>
     * <p>对应新闻的额外信息，如评论数量，所获的『赞』的数量。</p>
     *
     * @param id 新闻id
     * @return
     */
    @GET("story-extra/#{id}")
    Call<String> getStroyExtra(@Path("id") String id);

    /**
     * 查看新闻对应长评论
     *
     * @param id 新闻id
     * @return
     */
    @GET("story/{id}/long-comments")
    Call<String> getLongComments(@Path("id") String id);

    /**
     * 查看新闻对应短评论
     *
     * @param id 新闻id
     * @return
     */
    @GET("story/{id}/short-comments")
    Call<String> getShortComments(@Path("id") String id);


    /**
     * 查看主题日报列表
     *
     * @return
     */
    @GET("themes")
    Call<String> getThemes();

    /**
     * 查看主题日报内容
     *
     * @param id
     * @return
     */

    @GET("themes/{id}")
    Call<String> getThemeDetail(@Path("id") String id);


    /**
     * 查看新闻的推荐者
     *
     * @param id 新闻id
     * @return
     */
    @GET("story/#{id}/recommenders")
    Call<String> getNewsRecommender(@Path("id") String id);


    /**
     * 获取某个专栏之前的新闻
     *
     * <p>注：新闻id要是属于该专栏，否则，返回结果为空</p>
     *
     * @param section_id 专栏id
     * @param timestamp 时间戳
     * @return
     */
    @GET("section/#{section id}/before/#{timestamp}")
    Call<String> getSectionOldNews(@Path("section id") String section_id, @Path("timestamp") String timestamp);

}
