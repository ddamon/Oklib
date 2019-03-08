package com.dunkeng.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.dunkeng.R;
import com.dunkeng.common.Config;
import com.dunkeng.details.ActWebDetail;
import com.dunkeng.details.model.DetailBean;
import com.dunkeng.news.contract.NewsContract;
import com.dunkeng.news.model.News;
import com.dunkeng.news.model.NewsModel;
import com.dunkeng.news.model.NewslistBean;
import com.dunkeng.news.presenter.NewsPresenter;
import com.dunkeng.news.view.NewsAdapter;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.view.ToastUtils;
import com.oklib.widget.recyclerview.CoreRecyclerView;
import com.oklib.widget.recyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;


/**
 * @author Damon
 */
public class FragmentNews extends CoreBaseFragment<NewsPresenter, NewsModel> implements NewsContract.ViewNews {
    @BindView(R.id.recycler)
    CoreRecyclerView coreRecyclerView;
    private int pageNum = 20;
    private NewsAdapter adapter;

    public static FragmentNews newInstance(int position) {
        FragmentNews fragment = new FragmentNews();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.ArgumentKey.ARG_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        int position = getArguments().getInt(Config.ArgumentKey.ARG_POSITION, 0);
        String type = Config.getApiType(position);
        mPresenter.getNewsData(type, pageNum);
    }

    @Override
    public void showContent(News info) {
        adapter.addAll(info.getNewslist());
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_recyclerview;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        adapter = new NewsAdapter(mContext);
        coreRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NewslistBean newslistBean = ((NewslistBean) adapter.getItem(position));
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
        coreRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                initData();
            }
        });
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(mContext, msg);
    }
}
