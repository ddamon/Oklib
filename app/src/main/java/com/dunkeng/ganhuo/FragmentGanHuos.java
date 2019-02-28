package com.dunkeng.ganhuo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.dunkeng.R;
import com.dunkeng.common.Config;
import com.dunkeng.details.ActWebDetail;
import com.dunkeng.details.model.DetailBean;
import com.dunkeng.ganhuo.contract.GanHuosContract;
import com.dunkeng.ganhuo.model.GanHuoBean;
import com.dunkeng.ganhuo.model.GanHuos;
import com.dunkeng.ganhuo.model.GanHuosModel;
import com.dunkeng.ganhuo.presenter.GanHuosPresenter;
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
public class FragmentGanHuos extends CoreBaseFragment<GanHuosPresenter, GanHuosModel> implements GanHuosContract.ViewGanHuos {
    CoreRecyclerView coreRecyclerView;
    private int pageNum = 20;

    public static FragmentGanHuos newInstance(int position) {
        FragmentGanHuos fragment = new FragmentGanHuos();
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
        coreRecyclerView = new CoreRecyclerView(mContext).init(new BaseQuickAdapter<GanHuoBean, BaseViewHolder>(R.layout.item_news) {
            @Override
            protected void convert(BaseViewHolder helper, GanHuoBean item) {
                helper.setText(R.id.tv_daily_item_title, item.getDesc());
                if (item.getCreatedAt() != null) {
                    helper.setText(R.id.tv_daily_item_date, item.getCreatedAt());
                }
                helper.setText(R.id.tv_daily_item_description, item.getSource());
                String url = "";
                if (item.getImages() != null && item.getImages().size() > 0) {
                    url = item.getImages().get(0);
                }
                ImageLoaderUtil.getInstance().loadImage(mContext,
                        new ImageLoader.Builder().placeHolder(R.mipmap.shit_blue).fallback(R.mipmap.shit_blue)
                                .imgView((ImageView) helper.getView(R.id.iv_daily_item_image))
                                .url(url).build());
            }
        });
        coreRecyclerView.addOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                GanHuoBean newslistBean = ((GanHuoBean) adapter.getData().get(position));
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
        String[] types = getContext().getResources().getStringArray(R.array.ganhuo_tab);
        String type = types[position];
        mPresenter.getGanHuosData(type, pageNum);
    }

    @Override
    public void showContent(GanHuos info) {
        coreRecyclerView.getAdapter().addData(info.getResults());
    }

    @Override
    public void showTabList(String[] mTabs) {

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
