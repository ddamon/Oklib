package com.oklib.widget.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.oklib.widget.imageloader.glide.CircleBorderTransformation;
import com.oklib.widget.imageloader.glide.RoundedCornersTransformation;
import com.oklib.widget.imageloader.glide.listener.ProgressLoadListener;
import com.oklib.widget.imageloader.glide.listener.ProgressModelLoader;
import com.oklib.widget.imageloader.glide.listener.ProgressUIListener;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Damon
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    @Override
    public void loadImage(Context ctx, ImageLoader img) {

        int strategy = img.getWifiStrategy();
        if (strategy == ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI) {
            int netType = NetworkStatusUtil.getNetWorkType(ctx);
            //if wifi ,load pic
            if (netType == NetworkStatusUtil.NETWORKTYPE_WIFI) {
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

    @Override
    public void loadImageWithProgress(String url, final ImageView imageView, final ProgressLoadListener listener) {
        Glide.with(imageView.getContext()).using(new ProgressModelLoader(new ProgressUIListener() {
            @Override
            public void update(final int bytesRead, final int contentLength) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.update(bytesRead, contentLength);
                    }
                });
            }
        })).load(url).skipMemoryCache(true).dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).
                listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        listener.onException();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        listener.onResourceReady();
                        return false;
                    }
                }).into(imageView);
    }


    public void loadNormal(Context context, ImageLoader imageLoader) {
        DrawableRequestBuilder drawableRequestBuilder =
                Glide.with(context).load(imageLoader.getUrl())
                        .crossFade()
                        .thumbnail(imageLoader.getThumbnailSize())
                        .placeholder(imageLoader.getPlaceHolder());
        loadImg(context, imageLoader, drawableRequestBuilder);
    }

    private void loadImg(Context context, ImageLoader imageLoader, DrawableRequestBuilder drawableRequestBuilder) {
        if (imageLoader.isCircle()) {
            //圆形图片
            drawableRequestBuilder.bitmapTransform(
                    new CircleBorderTransformation(context, imageLoader.getBorder())).into(imageLoader.getImgView()
            );
            //FIXME默认是四个方向的圆角
        } else if (imageLoader.getRoundRadius() > 0) {
            drawableRequestBuilder.bitmapTransform(
                    new RoundedCornersTransformation(context, imageLoader.getRoundRadius(), 0, RoundedCornersTransformation.CornerType.ALL)).into(imageLoader.getImgView()
            );
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
        loadImg(context, imageLoader, drawableRequestBuilder);
    }
}
