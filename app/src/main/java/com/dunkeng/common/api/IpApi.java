package com.dunkeng.common.api;


import com.dunkeng.main.model.IpModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IpApi {
    @GET("getIpInfo2.php")
    Observable<IpModel> getIpData(@Query("ip") String ip);
}
