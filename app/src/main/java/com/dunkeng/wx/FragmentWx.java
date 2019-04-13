package com.dunkeng.wx;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dunkeng.R;
import com.dunkeng.common.Config;
import com.dunkeng.common.OnFragmentOpenDrawerListener;
import com.dunkeng.details.ActWebDetail;
import com.dunkeng.details.model.DetailBean;
import com.dunkeng.wx.contract.WxContract;
import com.dunkeng.wx.model.Wx;
import com.dunkeng.wx.model.WxBean;
import com.dunkeng.wx.model.WxModel;
import com.dunkeng.wx.presenter.WxPresenter;
import com.dunkeng.wx.view.WxAdapter;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.view.ToastUtils;
import com.oklib.widget.recyclerview.CoreRecyclerView;
import com.oklib.widget.recyclerview.adapter.RecyclerArrayAdapter;
import com.oklib.widget.recyclerview.inter.OnItemClickListener;

import butterknife.BindView;

/**
 * Created by Damon on 2016/12/7.
 * 知乎日报
 */

public class FragmentWx extends CoreBaseFragment<WxPresenter, WxModel> implements WxContract.WxView {
    @BindView(R.id.recycler)
    CoreRecyclerView coreRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    protected OnFragmentOpenDrawerListener mOpenDraweListener;
    private int pageNum = 30;
    private WxAdapter adapter;

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
    public void showContent(Wx info) {
        adapter.addAll(info.getNewslist());


    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_wx;
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

        adapter = new WxAdapter(mContext);
        coreRecyclerView.setAdapter(adapter);
        coreRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                initData();
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                WxBean newslistBean = ((WxBean) adapter.getAllData().get(position));
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
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(mContext, msg);
    }
}
