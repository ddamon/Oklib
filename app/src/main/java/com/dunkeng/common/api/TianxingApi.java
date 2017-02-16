package com.dunkeng.common.api;


import com.dunkeng.news.model.News;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Damon.Han on 2016/11/4 0004.
 */

public interface TianxingApi {
    //news
    @GET("/guonei")
    Observable<News> getGuoneiNews(@Query("key") String key, @Query("num") int num);
    @GET("/world")
    Observable<News> getWorldNews(@Query("key") String key, @Query("num") int num);
    @GET("/huabian")
    Observable<News> getHuabianNews(@Query("key") String key, @Query("num") int num);
    @GET("/tiyu")
    Observable<News> getTiyuNews(@Query("key") String key, @Query("num") int num);
    @GET("/nba")
    Observable<News> getNbaNews(@Query("key") String key, @Query("num") int num);
    @GET("/keji")
    Observable<News> getKejiNews(@Query("key") String key, @Query("num") int num);
    @GET("/startup")
    Observable<News> getStratupNews(@Query("key") String key, @Query("num") int num);
    @GET("/it")
    Observable<News> getItNews(@Query("key") String key, @Query("num") int num);
    //微信精选
    @GET("/wxnew")
    Observable<News> getWeixinNews(@Query("key") String key, @Query("num") int num);


}
