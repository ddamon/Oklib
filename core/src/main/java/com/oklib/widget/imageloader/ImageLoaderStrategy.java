package com.oklib.widget.imageloader;

import android.content.Context;

/**
 * Created by Damon.Han on 2016/11/24 0024.
 *
 * @author Damon
 */

public interface ImageLoaderStrategy {
    /**
     * 加载图片
     *
     * @param context
     */
    public void loadImage(Context context, ImageLoader img);
}
