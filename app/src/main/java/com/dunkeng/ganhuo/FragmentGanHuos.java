package com.dunkeng.ganhuo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.dunkeng.R;
import com.dunkeng.common.Config;
import com.dunkeng.details.ActWebDetail;
import com.dunkeng.details.model.DetailBean;
import com.dunkeng.ganhuo.contract.GanHuosContract;
import com.dunkeng.ganhuo.model.GanHuoBean;
import com.dunkeng.ganhuo.model.GanHuos;
import com.dunkeng.ganhuo.model.GanHuosModel;
import com.dunkeng.ganhuo.presenter.GanHuosPresenter;
import com.dunkeng.ganhuo.view.GanHuosAdapter;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.view.ToastUtils;
import com.oklib.widget.recyclerview.CoreRecyclerView;
import com.oklib.widget.recyclerview.adapter.RecyclerArrayAdapter;
import com.oklib.widget.recyclerview.inter.OnItemClickListener;

import butterknife.BindView;


/**
 * @author Damon
 */
public class FragmentGanHuos extends CoreBaseFragment<GanHuosPresenter, GanHuosModel> implements GanHuosContract.ViewGanHuos {
    @BindView(R.id.recycler)
    CoreRecyclerView coreRecyclerView;
    private int pageNum = 20;
    private GanHuosAdapter adapter;

    public static FragmentGanHuos newInstance(int position) {
        FragmentGanHuos fragment = new FragmentGanHuos();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.ArgumentKey.ARG_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void initData() {
        int position = getArguments().getInt(Config.ArgumentKey.ARG_POSITION, 0);
        String[] types = getContext().getResources().getStringArray(R.array.ganhuo_tab);
        String type = types[position];
        mPresenter.getGanHuosData(type, pageNum);
    }

    @Override
    public void showContent(GanHuos info) {
        adapter.addAll(info.getResults());
    }

    @Override
    public void showTabList(String[] mTabs) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.base_recyclerview;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        adapter = new GanHuosAdapter(mContext);
        coreRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GanHuoBean newslistBean = ((GanHuoBean) adapter.getAllData().get(position));
                if (newslistBean == null) {
                    return;
                }
                DetailBean detailBean = new DetailBean();
                detailBean.setCtime(newslistBean.getCreatedAt());
                detailBean.setDescription(newslistBean.getSource());
                if (newslistBean.getImages() != null && newslistBean.getImages().size() > 0) {
                    detailBean.setPicUrl(newslistBean.getImages().get(0));
                }
                detailBean.setTitle(newslistBean.getDesc());
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
