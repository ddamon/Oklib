package com.dunkeng.news;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.dunkeng.R;
import com.dunkeng.common.Config;
import com.dunkeng.common.OnFragmentOpenDrawerListener;
import com.dunkeng.news.contract.NewsContract;
import com.dunkeng.news.model.News;
import com.dunkeng.news.model.NewsModel;
import com.dunkeng.news.model.NewslistBean;
import com.dunkeng.news.presenter.NewsPresenter;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.ToastUtils;
import com.oklib.widget.imageloader.ImageLoader;
import com.oklib.widget.imageloader.ImageLoaderUtil;
import com.oklib.widget.recyclerview.BaseQuickAdapter;
import com.oklib.widget.recyclerview.BaseViewHolder;
import com.oklib.widget.recyclerview.CoreRecyclerView;
import com.oklib.widget.recyclerview.listener.OnItemClickListener;

import butterknife.BindView;

/**
 * Created by Damon on 2016/12/7.
 * 知乎日报
 */

public class FragmentWx extends CoreBaseFragment<NewsPresenter, NewsModel> implements NewsContract.ViewNews {
    @BindView(R.id.recycler)
    CoreRecyclerView coreRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    protected OnFragmentOpenDrawerListener mOpenDraweListener;
    private int pageNum = 30;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentOpenDrawerListener) {
            mOpenDraweListener = (OnFragmentOpenDrawerListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOpenDraweListener = null;
    }

    @Override
    public void initData() {
        String type = Config.getApiType(8);
        mPresenter.getNewsData(type, pageNum);
    }

    @Override
    public void showContent(News info) {
        coreRecyclerView.getAdapter().addData(info.getNewslist());
        coreRecyclerView.addOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ActNewsDetail.start(mActivity, view.findViewById(R.id.iv_daily_item_image), ((NewslistBean) adapter.getData().get(position)));
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.view_recycler_with_bar;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        toolbar.setTitle("微信精选");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(v -> {
            if (mOpenDraweListener != null) {
                mOpenDraweListener.onOpenDrawer();
            }
        });
        coreRecyclerView.init(new BaseQuickAdapter<NewslistBean, BaseViewHolder>(R.layout.item_news) {
            @Override
            protected void convert(BaseViewHolder helper, NewslistBean item) {
                helper.setText(R.id.tv_daily_item_title, item.getTitle());
                ImageLoaderUtil.getInstance().loadImage(mContext,
                        new ImageLoader.Builder()
                                .imgView((ImageView) helper.getView(R.id.iv_daily_item_image))
                                .url(item.getPicUrl()).build());
            }
        });
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(mContext, msg);
    }
}
