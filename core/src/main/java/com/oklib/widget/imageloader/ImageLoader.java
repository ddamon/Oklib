package com.oklib.widget.imageloader;

import android.widget.ImageView;

import com.oklib.R;


/**
 * 图片加载类
 *
 * @author Damon
 */

public class ImageLoader {
    private int type;  // (Big,Medium,small)
    private String url; //url to parse
    private int placeHolder; //placeholder when fail to load pics
    private ImageView imgView; //ImageView instantce
    private int wifiStrategy;//load strategy ,wheather under wifi
    private boolean isCircle;
    private int border;//
    private int roundRadius;//
    private float thumbnailSize;//
    private int fallback;
    private ImageLoader(Builder builder) {
        this.type = builder.type;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.imgView = builder.imgView;
        this.wifiStrategy = builder.wifiStrategy;
        this.isCircle = builder.isCircle;
        this.border = builder.border;
        this.roundRadius = builder.roundRadius;
        this.thumbnailSize = builder.thumbnailSize;
        this.fallback = builder.fallback;
    }

    public float getThumbnailSize() {
        return thumbnailSize;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public int getWifiStrategy() {
        return wifiStrategy;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public int getRoundRadius() {
        return roundRadius;
    }

    public int getBorder() {
        return border;
    }
    public int getFallback() {
        return fallback;
    }

    public static class Builder {
        private int type;
        private String url;
        private int placeHolder;
        private ImageView imgView;
        private int wifiStrategy;
        private boolean isCircle;
        private int border;//
        private int roundRadius;//
        private float thumbnailSize;//
        private int fallback;//

        public Builder() {
            this.type = ImageLoaderUtil.PIC_SMALL;
            this.url = "";
            this.placeHolder = R.drawable.lib_img_default;
            this.imgView = null;
            this.wifiStrategy = ImageLoaderUtil.LOAD_STRATEGY_NORMAL;
            this.isCircle = false;
            this.border = 0;
            this.roundRadius = 0;
            this.thumbnailSize = 1;
            this.fallback = 0;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder imgView(ImageView imgView) {
            this.imgView = imgView;
            return this;
        }

        public Builder strategy(int strategy) {
            this.wifiStrategy = strategy;
            return this;
        }

        public Builder circle() {
            this.isCircle = true;
            return this;
        }

        public Builder border(int border) {
            this.border = border;
            return this;
        }

        public Builder roundRadius(int roundRadius) {
            this.roundRadius = roundRadius;
            return this;
        }

        public Builder thumb(float thumnailSize) {
            this.thumbnailSize = thumnailSize;
            return this;
        }
        public Builder fallback(int fallback) {
            this.fallback = fallback;
            return this;
        }
        public ImageLoader build() {
            return new ImageLoader(this);
        }
    }


}
