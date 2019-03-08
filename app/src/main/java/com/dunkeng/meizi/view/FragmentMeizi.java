package com.dunkeng.meizi.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dunkeng.R;
import com.dunkeng.common.OnFragmentOpenDrawerListener;
import com.dunkeng.meizi.contract.MeiziContract;
import com.dunkeng.meizi.model.Meizi;
import com.dunkeng.meizi.model.MeiziModel;
import com.dunkeng.meizi.model.MeiziResult;
import com.dunkeng.meizi.model.PictureBean;
import com.dunkeng.meizi.presenter.MeiziPresenter;
import com.dunkeng.meizi.view.adapter.MeiZiAdapter;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.view.ToastUtils;
import com.oklib.widget.recyclerview.CoreRecyclerView;
import com.oklib.widget.recyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;


public class FragmentMeizi extends CoreBaseFragment<MeiziPresenter, MeiziModel> implements MeiziContract.View {
    @BindView(R.id.recycler)
    CoreRecyclerView coreRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    protected OnFragmentOpenDrawerListener mOpenDraweListener;
    private MeiZiAdapter adapter;
    private int page = 1;

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
        mPresenter.getMeizi(1);
    }

    @Override
    public void showContent(MeiziResult info) {
        adapter.addAll(info.beauties);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_meizi;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        toolbar.setTitle("福利");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(v -> {
            if (mOpenDraweListener != null) {
                mOpenDraweListener.onOpenDrawer();
            }
        });

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL);
        coreRecyclerView.setLayoutManager(manager);
        adapter = new MeiZiAdapter(getContext());
        coreRecyclerView.setAdapter(adapter);
        adapter.setMore(new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                mPresenter.getMeizi(page);
            }
        });
        adapter.setNoMore(new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                adapter.pauseMore();
            }

            @Override
            public void onNoMoreClick() {

            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PictureBean pictureBean = new PictureBean();
                pictureBean.url = ((Meizi) adapter.getAllData().get(position)).getUrl();
                pictureBean.desc = ((Meizi) adapter.getAllData().get(position)).getDesc();
                ActPictureDesc.start(mActivity, view.findViewById(R.id.image), pictureBean);
            }
        });
        adapter.setError(new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
        coreRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                page = 1;
                mPresenter.getMeizi(page);
            }
        });

    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(mContext, msg);
    }

}
