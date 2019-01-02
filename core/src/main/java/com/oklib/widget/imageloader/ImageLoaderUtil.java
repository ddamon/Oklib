package com.oklib.widget.imageloader;

import android.content.Context;

import com.bumptech.glide.request.RequestListener;

import java.io.File;

/**
 * Created by Damon.Han on 2016/11/24 0024.
 *
 * @author Damon
 */

public class ImageLoaderUtil implements BaseImageLoaderStrategy {

    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;
    /**
     * 普通方式加载
     */
    public static final int LOAD_STRATEGY_NORMAL = 0;
    /**
     * 仅在wifi下加载
     */
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;


    private BaseImageLoaderStrategy mStrategy;

    private static ImageLoaderUtil mInstance;

    private ImageLoaderUtil() {
        mStrategy = new GlideImageLoaderStrategy();
    }

    public static ImageLoaderUtil getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoaderUtil.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderUtil();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    @Override
    public void loadImage(Context context, ImageLoader imageLoader) {
        mStrategy.loadImage(context, imageLoader);
    }

    @Override
    public void loadResource(Context context, int resId, ImageLoader imageLoader) {
        mStrategy.loadResource(context, resId, imageLoader);
    }

    @Override
    public void loadAssets(Context context, String assetName, ImageLoader imageLoader) {
        mStrategy.loadAssets(context, assetName, imageLoader);
    }

    @Override
    public void loadFile(Context context, File file, ImageLoader imageLoader) {
        mStrategy.loadFile(context, file, imageLoader);
    }

    @Override
    public void loadImageWithProgress(Context context,ImageLoader imageLoader, RequestListener listener) {
        mStrategy.loadImageWithProgress(context, imageLoader, listener);
    }

    @Override
    public void clearImageDiskCache(Context context) {
        mStrategy.clearImageDiskCache(context);
    }

    @Override
    public void clearImageMemoryCache(Context context) {
        mStrategy.clearImageMemoryCache(context);
    }


    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        mStrategy = strategy;
    }


}