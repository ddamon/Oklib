package com.oklib.utils.network.Exception;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.oklib.utils.Logger.Logger;
import com.oklib.utils.network.util.FileUtil;

/**
 * 配置信息加载
 *
 * @author Damon.Han
 */

public class ConfigLoader {

    private static NetResponseConfig config;

    private final static String CONFIG_NAME = "config.json";

    public static boolean checkSucess(Context context, int code) {
        loadConfig(context);
        Logger.v("NetWorker", "web :" + code + ">>>>>>>>>>>>isOk：" + config.getSucessCode().contains(String.valueOf(code)));
        return config.getSucessCode().contains(String.valueOf(code));
    }

    public static NetResponseConfig loadConfig(Context context) {

        if (config != null) {
            return config;
        }
        String jsonStr = FileUtil.loadFromAssets(context, CONFIG_NAME);
        if (jsonStr == null) {
            return null;
        }
        return config = new Gson().fromJson(jsonStr, NetResponseConfig.class);
    }

    public static boolean isFormat(Context context) {
        loadConfig(context);
        return TextUtils.equals(config.getIsFormat(), "ture");
    }

}
