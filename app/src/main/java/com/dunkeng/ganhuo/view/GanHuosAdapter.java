package com.dunkeng.ganhuo.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dunkeng.R;
import com.dunkeng.ganhuo.model.GanHuoBean;
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
public class GanHuosAdapter extends RecyclerArrayAdapter {
    public GanHuosAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(parent);
    }


    public class NewsViewHolder extends BaseViewHolder<GanHuoBean> {
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
        public void setData(GanHuoBean item) {

            tvDailyItemTitle.setText(item.getDesc());
            if (item.getCreatedAt() != null) {
                tvDailyItemDate.setText(item.getCreatedAt());
            }
            tvDailyItemDescription.setText(item.getSource());
            String url = "";
            if (item.getImages() != null && item.getImages().size() > 0) {
                url = item.getImages().get(0);
            }
            ImageLoaderUtil.getInstance().loadImage(getContext(),
                    new ImageLoader.Builder().placeHolder(R.mipmap.shit_blue).fallback(R.mipmap.shit_blue)
                            .imgView(ivDailyItemImage)
                            .url(url).build());
        }
    }
}