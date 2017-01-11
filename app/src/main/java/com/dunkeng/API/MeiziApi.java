package com.dunkeng.api;


import com.dunkeng.meizi.model.MeiZiGson;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Damon.Han on 2016/11/4 0004.
 */

public interface MeiziApi {

    @GET("meinv/")
    Observable<MeiZiGson> getPictureData(@Query("key") String key, @Query("num") String num, @Query("page") int page);
}
