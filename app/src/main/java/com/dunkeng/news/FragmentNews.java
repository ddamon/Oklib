package com.dunkeng.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.dunkeng.R;
import com.dunkeng.common.Config;
import com.dunkeng.details.ActWebDetail;
import com.dunkeng.details.model.DetailBean;
import com.dunkeng.news.contract.NewsContract;
import com.dunkeng.news.model.News;
import com.dunkeng.news.model.NewsModel;
import com.dunkeng.news.model.NewslistBean;
import com.dunkeng.news.presenter.NewsPresenter;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.view.ToastUtils;
import com.oklib.widget.imageloader.ImageLoader;
import com.oklib.widget.imageloader.ImageLoaderUtil;
import com.oklib.widget.recyclerview.BaseQuickAdapter;
import com.oklib.widget.recyclerview.BaseViewHolder;
import com.oklib.widget.recyclerview.CoreRecyclerView;
import com.oklib.widget.recyclerview.listener.OnItemClickListener;


/**
 * @author Damon
 */
public class FragmentNews extends CoreBaseFragment<NewsPresenter, NewsModel> implements NewsContract.ViewNews {
    CoreRecyclerView coreRecyclerView;
    private int pageNum = 20;

    public static FragmentNews newInstance(int position) {
        FragmentNews fragment = new FragmentNews();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.ArgumentKey.ARG_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 初始化view
     *
     * @return
     */
    @Override
    public View getLayoutView() {
        coreRecyclerView = new CoreRecyclerView(mContext).init(new BaseQuickAdapter<NewslistBean, BaseViewHolder>(R.layout.item_news) {
            @Override
            protected void convert(BaseViewHolder helper, NewslistBean item) {
                helper.setText(R.id.tv_daily_item_title, item.getTitle());
                ImageLoaderUtil.getInstance().loadImage(mContext,
                        new ImageLoader.Builder()
                                .imgView((ImageView) helper.getView(R.id.iv_daily_item_image))
                                .url(item.getPicUrl()).build());
            }
        });
        coreRecyclerView.addOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewslistBean newslistBean = ((NewslistBean) adapter.getData().get(position));
                if (newslistBean == null) {
                    return;
                }
                DetailBean detailBean = new DetailBean();
                detailBean.setCtime(newslistBean.getCtime());
                detailBean.setDescription(newslistBean.getDescription());
                detailBean.setPicUrl(newslistBean.getPicUrl());
                detailBean.setTitle(newslistBean.getTitle());
                detailBean.setUrl(newslistBean.getUrl());
                ActWebDetail.start(mActivity, view.findViewById(R.id.iv_daily_item_image), detailBean);
            }
        });
        //单独使用refresh需要使用带参数的
        coreRecyclerView.openRefresh(new CoreRecyclerView.addDataListener() {
            @Override
            public void addData(int page) {
                initData();
            }
        });
        return coreRecyclerView;
    }

    @Override
    public void initData() {
        int position = getArguments().getInt(Config.ArgumentKey.ARG_POSITION, 0);
        String type = Config.getApiType(position);
        mPresenter.getNewsData(type, pageNum);
    }

    @Override
    public void showContent(News info) {
        coreRecyclerView.getAdapter().addData(info.getNewslist());
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(mContext, msg);
    }
}
