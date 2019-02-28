package com.oklib.widget.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * @author Damon
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    @Override
    public void loadImage(Context ctx, ImageLoader img) {
        int strategy = img.getWifiStrategy();
        if (strategy == ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI) {
            int netType = NetworkStatusUtil.getNetWorkType(ctx);
            if (netType == NetworkStatusUtil.NETWORKTYPE_WIFI) {
                loadNet(ctx, img);
            } else {
                loadCache(ctx, img);
            }
        } else {
            loadNet(ctx, img);
        }
    }

    @Override
    public void loadResource(Context context, int resId, ImageLoader imageLoader) {
        loadImg(context, imageLoader, getRequestManager(context).load(resId), false);
    }

    @Override
    public void loadAssets(Context context, String assetName, ImageLoader imageLoader) {
        loadImg(context, imageLoader, getRequestManager(context).load("file:///android_asset/" + assetName), false);
    }

    @Override
    public void loadFile(Context context, File file, ImageLoader imageLoader) {
        loadImg(context, imageLoader, getRequestManager(context).load(file), false);
    }

    @Override
    public void loadImageWithProgress(Context context, ImageLoader imageLoader, final RequestListener listener) {
        Glide.with(imageLoader.getImgView().getContext()).load(imageLoader.getUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageLoader.getImgView());
    }


    @Override
    public void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context.getApplicationContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context.getApplicationContext()).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Glide.get(context.getApplicationContext()).clearMemory();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context.getApplicationContext()).clearMemory();
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RequestManager getRequestManager(Context context) {
        return Glide.with(context);
    }

    private void loadNet(Context context, ImageLoader imageLoader) {
        loadImg(context, imageLoader, getRequestManager(context).load(imageLoader.getUrl()), false);
    }

    private RequestBuilder loadImg(Context context, ImageLoader imageLoader, RequestBuilder drawableRequestBuilder, boolean isCache) {
        RequestOptions options = new RequestOptions();
        options.fallback(imageLoader.getFallback());
        if (imageLoader.getPlaceHolder() != 0) {
            options.placeholder(imageLoader.getPlaceHolder());
        }
        if (imageLoader.isCircle()) {
            options.centerCrop();
            //圆形图片
            drawableRequestBuilder.apply(options).into(imageLoader.getImgView());
        }
        if (imageLoader.getRoundRadius() > 0) {
            options.circleCrop();
            drawableRequestBuilder.apply(options).into(imageLoader.getImgView());
        }
        if (isCache) {
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
        }
        drawableRequestBuilder.transition(DrawableTransitionOptions.withCrossFade());
        drawableRequestBuilder.apply(options).into(imageLoader.getImgView());
        return drawableRequestBuilder;
    }

    /**
     * 从缓存中加载
     *
     * @param context
     * @param imageLoader
     */
    private void loadCache(Context context, ImageLoader imageLoader) {
        loadImg(context, imageLoader, getRequestManager(context).load(imageLoader.getUrl()), true);
    }
}
