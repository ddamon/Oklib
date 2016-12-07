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
}
