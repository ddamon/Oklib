package com.oklib.widget.imageloader;

import android.content.Context;

import com.oklib.widget.imageloader.glide.listener.ProgressLoadListener;

import java.io.File;

/**
 * Created by Damon.Han on 2016/11/24 0024.
 *
 * @author Damon
 */

public interface BaseImageLoaderStrategy {
    /**
     * 加载图片
     *
     * @param context
     */
    void loadImage(Context context, ImageLoader imageLoader);

    void loadResource(Context context, int resId, ImageLoader imageLoader);

    void loadAssets(Context context, String assetName, ImageLoader imageLoader);

    void loadFile(Context context, File file, ImageLoader imageLoader);

    void loadImageWithProgress(String url, ImageLoader imageLoader, ProgressLoadListener listener);

    //清除硬盘缓存
    void clearImageDiskCache(final Context context);

    //清除内存缓存
    void clearImageMemoryCache(Context context);
}
