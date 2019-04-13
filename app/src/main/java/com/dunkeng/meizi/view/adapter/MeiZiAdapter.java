package com.dunkeng.meizi.view.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dunkeng.R;
import com.dunkeng.meizi.model.Meizi;
import com.oklib.widget.recyclerview.adapter.RecyclerArrayAdapter;
import com.oklib.widget.recyclerview.holder.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Damon.Han on 2019/3/8 0008.
 *
 * @author Damon
 */
public class MeiZiAdapter extends RecyclerArrayAdapter {
    public MeiZiAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MeiZiViewHolder(parent);
    }


    public class MeiZiViewHolder extends BaseViewHolder<Meizi> {

        @BindView(R.id.image)
        ImageView image;

        public MeiZiViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_meizi);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Meizi item) {
            RequestOptions options = new RequestOptions();
            options.dontAnimate();
            Glide.with(getContext()).load(item.getUrl()).apply(options).into(image);
        }
    }
}
