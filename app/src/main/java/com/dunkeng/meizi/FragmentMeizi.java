package com.dunkeng.meizi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.dunkeng.R;
import com.dunkeng.common.Config;
import com.dunkeng.common.OnFragmentOpenDrawerListener;
import com.dunkeng.meizi.contract.MeiziContract;
import com.dunkeng.meizi.model.Meizi;
import com.dunkeng.meizi.model.MeiziModel;
import com.dunkeng.meizi.model.MeiziResult;
import com.dunkeng.meizi.presenter.MeiziPresenter;
import com.dunkeng.picture.ActPictureDesc;
import com.dunkeng.picture.model.PictureBean;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.ToastUtils;
import com.oklib.utils.logger.Logger;
import com.oklib.widget.imageloader.ImageLoader;
import com.oklib.widget.imageloader.ImageLoaderUtil;
import com.oklib.widget.recyclerview.BaseQuickAdapter;
import com.oklib.widget.recyclerview.BaseViewHolder;
import com.oklib.widget.recyclerview.CoreRecyclerView;
import com.oklib.widget.recyclerview.listener.OnItemClickListener;

import butterknife.BindView;


public class FragmentMeizi extends CoreBaseFragment<MeiziPresenter, MeiziModel> implements MeiziContract.View {
    @BindView(R.id.recycler)
    CoreRecyclerView coreRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    protected OnFragmentOpenDrawerListener mOpenDraweListener;

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
        coreRecyclerView.getAdapter().addData(info.beauties);
        coreRecyclerView.openLoadMore(Config.Data.pageSize, new CoreRecyclerView.addDataListener() {
            @Override
            public void addData(int page) {
                page++;
                mPresenter.getMeizi(page);
            }
        }).openRefresh();
        coreRecyclerView.addOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.e("点击图片");
                PictureBean pictureBean = new PictureBean();
                pictureBean.url = ((Meizi) adapter.getData().get(position)).getUrl();
                pictureBean.desc = ((Meizi) adapter.getData().get(position)).getDesc();
                ActPictureDesc.start(mActivity, view.findViewById(R.id.image), pictureBean);
            }
        });
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
        coreRecyclerView.init(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL), new BaseQuickAdapter<Meizi, BaseViewHolder>(R.layout.item_meizi) {
            @Override
            protected void convert(BaseViewHolder helper, Meizi item) {
//                helper.setText(R.id.title, item.getDesc());
                ImageLoaderUtil.getInstance().loadImage(mContext,
                        new ImageLoader.Builder()
                                .imgView((ImageView) helper.getView(R.id.image))
                                .url(item.getUrl()).build());
            }
        });
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(mContext, msg);
        coreRecyclerView.showLoadMoreFailedView();
    }

}
