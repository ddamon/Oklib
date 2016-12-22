package com.oklib.widget.imageloader;

import android.content.Context;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.oklib.widget.imageloader.glide.CircleBorderTransformation;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Damon
 */

public class GlideImageLoaderStrategy implements ImageLoaderStrategy {

    @Override
    public void loadImage(Context ctx, ImageLoader img) {
        //if currently not under wifi
        if (!ImageLoaderUtil.wifiFlag) {
            loadNormal(ctx, img);
            return;
        }

        int strategy = img.getWifiStrategy();
        if (strategy == ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI) {
            int netType = ImageLoaderUtil.getNetWorkType(ctx);
            //if wifi ,load pic
            if (netType == ImageLoaderUtil.NETWORKTYPE_WIFI) {
                loadNormal(ctx, img);
            } else {
                //if not wifi ,load cache
                loadCache(ctx, img);
            }
        } else {
            //如果不是在wifi下才加载图片
            loadNormal(ctx, img);
        }
    }

    public void loadNormal(Context context, ImageLoader imageLoader) {
        DrawableRequestBuilder drawableRequestBuilder =
                Glide.with(context).load(imageLoader.getUrl())
                .crossFade()
                .placeholder(imageLoader.getPlaceHolder());
        if (imageLoader.isCircle()) {
            //圆形图片
            drawableRequestBuilder.bitmapTransform(new CircleBorderTransformation(context, imageLoader.getBorder())).into(imageLoader.getImgView());
        } else {
            drawableRequestBuilder.into(imageLoader.getImgView());
        }
    }

    /**
     * 从缓存中加载
     *
     * @param context
     * @param imageLoader
     */
    public void loadCache(Context context, ImageLoader imageLoader) {
        DrawableRequestBuilder drawableRequestBuilder = Glide.with(context).using(new StreamModelLoader<String>() {
            @Override
            public DataFetcher<InputStream> getResourceFetcher(final String model, int i, int i1) {
                return new DataFetcher<InputStream>() {
                    @Override
                    public InputStream loadData(Priority priority) throws Exception {
                        throw new IOException();
                    }

                    @Override
                    public void cleanup() {

                    }

                    @Override
                    public String getId() {
                        return model;
                    }

                    @Override
                    public void cancel() {

                    }
                };
            }
        }).load(imageLoader.getUrl())
                .crossFade()
                .placeholder(imageLoader.getPlaceHolder())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (imageLoader.isCircle()) {
            //圆形图片
            drawableRequestBuilder.bitmapTransform(new CircleBorderTransformation(context, imageLoader.getBorder())).into(imageLoader.getImgView());
        } else {
            drawableRequestBuilder.into(imageLoader.getImgView());
        }
    }
}
