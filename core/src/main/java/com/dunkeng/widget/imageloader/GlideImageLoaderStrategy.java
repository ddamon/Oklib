package com.dunkeng.widget.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Damon.Han on 2016/11/24 0024.
 *
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
        Glide.with(context).load(imageLoader.getUrl())
                .placeholder(imageLoader.getPlaceHolder())
                .into(imageLoader.getImgView());
    }

    /**
     * 从缓存中加载
     *
     * @param ctx
     * @param img
     */
    public void loadCache(Context ctx, ImageLoader img) {
        Glide.with(ctx).using(new StreamModelLoader<String>() {
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
        }).load(img.getUrl())
                .placeholder(img.getPlaceHolder())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img.getImgView());
    }
}
