package com.oklib.widget.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.oklib.widget.imageloader.glide.listener.ProgressLoadListener;

/**
 * Created by Damon.Han on 2016/11/24 0024.
 *
 * @author Damon
 */

public class ImageLoaderUtil {

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

    public void loadImage(Context context, ImageLoader imageLoader) {
        mStrategy.loadImage(context, imageLoader);
    }

    public void loadImageWithProgress(String url, ImageView imageView, ProgressLoadListener listener) {
        mStrategy.loadImageWithProgress(url, imageView, listener);
    }


    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        mStrategy = strategy;
    }


}