package com.dunkeng.api;


import com.dunkeng.news.model.News;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Damon.Han on 2016/11/4 0004.
 */

public interface TianxingApi {

    @GET("/social")
    Observable<News> getSocialNews(@Query("key") String key, @Query("num") int num);
}
