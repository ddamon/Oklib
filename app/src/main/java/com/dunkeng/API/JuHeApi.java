package com.dunkeng.api;


import com.dunkeng.news.model.NewsGson;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Damon.Han on 2016/11/4 0004.
 */

public interface JuHeApi {

    @GET("/toutiao/index")
    Observable<NewsGson> getHomeNews(@Query("key") String key, @Query("type") String type);
}
