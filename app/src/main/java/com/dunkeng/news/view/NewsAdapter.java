package com.dunkeng.news.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dunkeng.R;
import com.dunkeng.news.model.NewslistBean;
import com.oklib.widget.imageloader.ImageLoader;
import com.oklib.widget.imageloader.ImageLoaderUtil;
import com.oklib.widget.recyclerview.adapter.RecyclerArrayAdapter;
import com.oklib.widget.recyclerview.viewHolder.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Damon.Han on 2019/3/8 0008.
 *
 * @author Damon
 */
public class NewsAdapter extends RecyclerArrayAdapter {
    public NewsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(parent);
    }


    public class NewsViewHolder extends BaseViewHolder<NewslistBean> {
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
        public void setData(NewslistBean item) {
            tvDailyItemTitle.setText(item.getTitle());
            if (item.getCtime() != null && item.getCtime().contains(" ")) {
                tvDailyItemDate.setText(item.getCtime().split(" ")[0]);
            }
            tvDailyItemDescription.setText(item.getDescription());
            ImageLoaderUtil.getInstance().loadImage(getContext(),
                    new ImageLoader.Builder()
                            .imgView(ivDailyItemImage)
                            .url(item.getPicUrl()).build());
        }
    }
}