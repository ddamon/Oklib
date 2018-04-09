package com.dunkeng.news.model;

import com.dunkeng.common.Config;
import com.dunkeng.common.api.TianxingApi;
import com.dunkeng.news.contract.NewsContract;
import com.oklib.utils.helper.RxUtil;
import com.oklib.utils.network.http.ViseHttp;

import io.reactivex.Observable;


/**
 * Created by Damon.Han on 2017/1/12 0012.
 *
 * @author Damon
 */

public class NewsModel implements NewsContract.Model {

    @Override
    public Observable<News> getNewsData(String type, int num) {
        Observable<News> observable = null;
        switch (type) {
            case "guonei":
                observable = ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_TIANXING).create(TianxingApi.class).getGuoneiNews(Config.API_KEY_TIANXING, num);
                break;
            case "world":
                observable = ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_TIANXING).create(TianxingApi.class).getWorldNews(Config.API_KEY_TIANXING, num);
                break;
            case "huabian":
                observable = ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_TIANXING).create(TianxingApi.class).getHuabianNews(Config.API_KEY_TIANXING, num);
                break;
            case "tiyu":
                observable = ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_TIANXING).create(TianxingApi.class).getTiyuNews(Config.API_KEY_TIANXING, num);
                break;
            case "nba":
                observable = ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_TIANXING).create(TianxingApi.class).getNbaNews(Config.API_KEY_TIANXING, num);
                break;
            case "keji":
                observable = ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_TIANXING).create(TianxingApi.class).getKejiNews(Config.API_KEY_TIANXING, num);
                break;
            case "startup":
                observable = ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_TIANXING).create(TianxingApi.class).getStratupNews(Config.API_KEY_TIANXING, num);
                break;
            case "it":
                observable = ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_TIANXING).create(TianxingApi.class).getItNews(Config.API_KEY_TIANXING, num);
                break;
            default:
                break;
        }

        return observable.compose(RxUtil.<News>rxSchedulerHelper());
    }

    @Override
    public String[] getTabs() {
        String[] mTabs = {"国内", "国际", "娱乐", "体育", "NBA", "科技", "创业", "IT资讯"};
        return mTabs;
    }
}
