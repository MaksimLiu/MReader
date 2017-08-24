package com.maksimliu.mreader.api;

import com.maksimliu.mreader.bean.BaseGankBean;
import com.maksimliu.mreader.bean.GankCategoryBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by MaksimLiu on 2017/3/9.
 * <h3>干货集中营 API </h3>
 */

public interface GankApi {


    String BASE_API_URL = "http://gank.io/api/";

    String ANDROID_CATEGORY_TYPE ="Android";

    String IOS_CATEGORY_TYPE = "iOS";

    String EXTRAS_CATEGORY_TYPE = "拓展资源";

    String FRONT_END_CATEGORY_TYPE = "前端";

    String OTHERS_CATEGORY_TYPE = "瞎推荐";

    String FULI_CATEGORY_TYPE = "福利";


    /**
     * 获取某个类别下的干货
     *
     * @param category 类别  福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param page     第几页
     * @return JSON
     */

    @GET("data/{category}/20/{page}")
    Observable<BaseGankBean<GankCategoryBean>> getCategoryGank(@Path("category") String category, @Path("page") int page);

}
