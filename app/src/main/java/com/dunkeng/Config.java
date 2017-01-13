package com.dunkeng;

import android.os.Environment;

import java.io.File;

/**
 * Created by Damon on 2016/12/7.
 */

public class Config {
    public static final String SD_CARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;// SD卡路径

    public static final String BASE_URL_TIANXING = "http://api.tianapi.com/";
    public static final String API_KEY_TIANXING = "7216c2025585eddc71b02cafcf856062";

    public static final String BASE_URL_JUHE = "http://v.juhe.cn/";
    public static final String API_KEY_JUHE = "ca58bb771e86993898c6914dda3fb67b";
    /**
     * zhihu
     */
    public static final String BASE_URL_ZHIHU = "http://news-at.zhihu.com/api/4/";


    public static final String ARG_POSITION = "arg_position";

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
            case 6:
                type = "startup";
                break;
            case 7:
                type = "it";
                break;
            default:
                type = "guonei";
                break;
        }
        return type;
    }
}
