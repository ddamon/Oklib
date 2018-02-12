package com.dunkeng.common.api;


import com.dunkeng.meizi.model.MeiziResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GankApi {
    @GET("福利/{number}/{page}")
    Observable<MeiziResult> getMeizi(@Path("number") int number, @Path("page") int page);
}
