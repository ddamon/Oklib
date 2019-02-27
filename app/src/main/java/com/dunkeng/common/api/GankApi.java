package com.dunkeng.common.api;


import com.dunkeng.ganhuo.model.GanHuos;
import com.dunkeng.meizi.model.MeiziResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GankApi {
    @GET("data/福利/{number}/{page}")
    Observable<MeiziResult> getMeizi(@Path("number") int number, @Path("page") int page);

    @GET("data/{category}/{number}/{page}")
    Observable<GanHuos> getGanHuos(@Path("category") String category, @Path("number") int number, @Path("page") int page);
}
