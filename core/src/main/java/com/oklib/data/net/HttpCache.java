package com.oklib.data.net;

import com.oklib.CoreApp;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by hpw on 16/11/2.
 */
public class HttpCache {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    public static Cache getCache() {
        File file = new File(CoreApp.getAppContext().getCacheDir().getAbsolutePath() + File.separator + "data/NetCache");
        return new Cache(file, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
