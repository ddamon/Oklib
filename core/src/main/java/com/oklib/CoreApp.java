package com.oklib;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.oklib.utils.pref.AnyPref;

import static com.oklib.CoreConstants.DEFAULT_AUTO_SAVE;
import static com.oklib.CoreConstants.DEFAULT_NO_IMAGE;
import static com.oklib.CoreConstants.SP_THEME_INDEX;

/**
 */

public abstract class CoreApp extends Application {
    private static CoreApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        AnyPref.init(this);
    }

    public static synchronized CoreApp getInstance() {
        return mApp;
    }

    public static Context getAppContext() {
        return mApp.getApplicationContext();
    }

    public static Resources getAppResources() {
        return mApp.getResources();
    }

    /**
     * 主题色
     * @param context
     * @return
     */
    public static int getThemeIndex(Context context) {
        return AnyPref.getDefault().getInt(SP_THEME_INDEX, 5);
    }

    public static void setThemeIndex(Context context, int index) {
        AnyPref.getDefault().putInt(SP_THEME_INDEX, index).apply();
    }

    /**
     * 夜间模式
     * @param context
     * @return
     */
    public static boolean getNightModel(Context context) {
        return AnyPref.getDefault().getBoolean(CoreConstants.SP_NIGHT_MODE, false);
    }

    public static void setNightModel(Context context, boolean nightModel) {
        AnyPref.getDefault().putBoolean(CoreConstants.SP_NIGHT_MODE, nightModel).apply();
    }

    public static boolean getNoImageState() {
        return AnyPref.getDefault().getBoolean(CoreConstants.SP_NO_IMAGE, DEFAULT_NO_IMAGE);
    }

    /**
     * 无图片模式
     * @param state
     */
    public static void setNoImageState(boolean state) {
        AnyPref.getDefault().putBoolean(CoreConstants.SP_NO_IMAGE, state).apply();
    }

    public static boolean getAutoCacheState() {
        return AnyPref.getDefault().getBoolean(CoreConstants.SP_AUTO_CACHE, DEFAULT_AUTO_SAVE);
    }

    /**
     * 缓存状态设置
     * @param state
     */
    public static void setAutoCacheState(boolean state) {
        AnyPref.getDefault().putBoolean(CoreConstants.SP_AUTO_CACHE, state).apply();
    }
}
