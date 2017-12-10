package com.oklib.utils.network;

import com.oklib.CoreApp;
import com.oklib.CoreConstants;

import java.io.File;

import okhttp3.Cache;

import static com.oklib.utils.network.NetWorkerConstant.PATH_HTTP_CACHE;

/**
 */
public class HttpCache {

    public static Cache getCache() {
        return new Cache(new File(CoreApp.getAppContext().getCacheDir().getAbsolutePath() + File.separator + PATH_HTTP_CACHE),
                NetWorkerConstant.HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
