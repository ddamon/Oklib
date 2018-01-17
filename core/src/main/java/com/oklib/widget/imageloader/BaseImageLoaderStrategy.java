package com.oklib.widget.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.oklib.widget.imageloader.glide.listener.ProgressLoadListener;

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
    void loadImage(Context context, ImageLoader img);

    void loadImageWithProgress(String url, ImageView imageView, ProgressLoadListener listener);

}
