package com.dunkeng.ganhuo.model;

import com.dunkeng.common.Config;
import com.dunkeng.common.api.GankApi;
import com.dunkeng.ganhuo.contract.GanHuosContract;
import com.oklib.utils.helper.RxUtil;
import com.oklib.utils.network.http.ViseHttp;

import io.reactivex.Observable;


/**
 * Created by Damon.Han on 2017/1/12 0012.
 *
 * @author Damon
 */

public class GanHuosModel implements GanHuosContract.Model {

    @Override
    public Observable<GanHuos> getGanHuosData(String type, int num) {
        Observable<GanHuos> observable = null;
        observable = ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_GANK).create(GankApi.class).getGanHuos(type, num, 1);
        return observable.compose(RxUtil.<GanHuos>rxSchedulerHelper());
    }

}
