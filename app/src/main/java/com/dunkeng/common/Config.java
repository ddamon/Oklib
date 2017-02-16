package com.dunkeng.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by Damon on 2016/12/7.
 */

public class Config {
    public static final String SD_CARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;// SD卡路径
    public static final String APP_CARD_PATH = SD_CARD_PATH + "dunkeng";// SD卡路径
    public static final String APP_CARD_PATH_PICTURE = APP_CARD_PATH + File.separator + "picture";// SD卡路径
    /*
    * tianxing
    * */
    public static final String BASE_URL_TIANXING = "http://api.tianapi.com/";
    public static final String API_KEY_TIANXING = "7216c2025585eddc71b02cafcf856062";
    /**
     * gank
     */
    public static final String BASE_URL_GANK = "http://gank.io/api/data/";
    /**
     * zhihu
     */
    public static final String BASE_URL_ZHIHU = "http://news-at.zhihu.com/api/4/";

    public static class Data {
        public static final int pageSize = 20;
    }

    /**
     * para key
     */
    public static class ArgumentKey {
        public static final String ARG_POSITION = "arg_position";
        public static final String ARG_ZHIHU_ID = "arg_zhihu_id";
        public static final String ARG_NEWS_BEAN = "arg_news_bean";
        public static final String ARG_PICTURE_BEAN = "arg_picture_bean";
    }

    public static String getApiType(int position) {
        String type = "guonei";
        switch (position) {
            case 0:
                type = "guonei";
                break;
            case 1:
                type = "world";
                break;
            case 2:
                type = "huabian";
                break;
            case 3:
                type = "tiyu";
                break;
            case 4:
                type = "nba";
                break;
            case 5:
                type = "keji";
                break;
            case 6:
                type = "startup";
                break;
            case 7:
                type = "it";
                break;
            case 8:
                type = "wxnew";
                break;
            default:
                type = "guonei";
                break;
        }
        return type;
    }
}
