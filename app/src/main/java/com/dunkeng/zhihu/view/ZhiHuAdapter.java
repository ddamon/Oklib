package com.dunkeng.zhihu.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dunkeng.R;
import com.dunkeng.zhihu.model.StoryItemBean;
import com.oklib.widget.imageloader.ImageLoader;
import com.oklib.widget.imageloader.ImageLoaderUtil;
import com.oklib.widget.recyclerview.adapter.RecyclerArrayAdapter;
import com.oklib.widget.recyclerview.holder.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Damon.Han on 2019/3/8 0008.
 *
 * @author Damon
 */
public class ZhiHuAdapter extends RecyclerArrayAdapter {
    public ZhiHuAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(parent);
    }


    public class NewsViewHolder extends BaseViewHolder<StoryItemBean> {
        @BindView(R.id.iv_daily_item_image)
        ImageView ivDailyItemImage;
        @BindView(R.id.tv_daily_item_title)
        TextView tvDailyItemTitle;
        @BindView(R.id.tv_daily_item_date)
        TextView tvDailyItemDate;
        @BindView(R.id.tv_daily_item_description)
        TextView tvDailyItemDescription;

        public NewsViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_news);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(StoryItemBean item) {

            tvDailyItemTitle.setText(item.getTitle());
            String url = "";
            url = item.getImage();
            ImageLoaderUtil.getInstance().loadImage(getContext(),
                    new ImageLoader.Builder().placeHolder(R.mipmap.shit_blue).fallback(R.mipmap.shit_blue).roundRadius(10)
                            .imgView(ivDailyItemImage)
                            .url(url).build());
        }
    }
}