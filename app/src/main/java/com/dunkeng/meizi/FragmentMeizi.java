package com.dunkeng.meizi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dunkeng.R;
import com.dunkeng.common.Config;
import com.dunkeng.common.OnFragmentOpenDrawerListener;
import com.dunkeng.meizi.contract.MeiziContract;
import com.dunkeng.meizi.model.Meizi;
import com.dunkeng.meizi.model.MeiziModel;
import com.dunkeng.meizi.model.MeiziResult;
import com.dunkeng.meizi.model.PictureBean;
import com.dunkeng.meizi.presenter.MeiziPresenter;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.view.ToastUtils;
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
        //在此处设置图片大小
//        for (int i = 0; i < info.beauties.size(); i++) {
//            int finalI = i;
//            Glide.with(mContext).load(info.beauties.get(i).getUrl()).transition(new DrawableTransitionOptions().crossFade()).into(new SimpleTarget<Drawable>() {
//                @Override
//                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                    float scale = resource.getIntrinsicWidth() / (float) resource.getIntrinsicHeight();
//                    info.beauties.get(finalI).setScale(scale);
////                    coreRecyclerView.getRecyclerView().getAdapter().notifyDataSetChanged();
//                }
//            });
//        }
        coreRecyclerView.getAdapter().addData(info.beauties);
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

        GridLayoutManager manager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
        coreRecyclerView.init(manager,
                new BaseQuickAdapter<Meizi, BaseViewHolder>(R.layout.item_meizi) {
                    @Override
                    protected void convert(BaseViewHolder helper, Meizi item) {
                        RequestOptions options = new RequestOptions();
                        options.dontAnimate();
                        Glide.with(mContext).load(item.getUrl()).apply(options).into((ImageView) helper.getView(R.id.image));
//                helper.setText(R.id.title, item.getDesc());
//                        ImageLoaderUtil.getInstance().loadImage(mContext,
//                                new ImageLoader.Builder()
//                                        .imgView((ImageView) helper.getView(R.id.image))
//                                        .url(item.getUrl()).build());
                    }
                });
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
                PictureBean pictureBean = new PictureBean();
                pictureBean.url = ((Meizi) adapter.getData().get(position)).getUrl();
                pictureBean.desc = ((Meizi) adapter.getData().get(position)).getDesc();
                ActPictureDesc.start(mActivity, view.findViewById(R.id.image), pictureBean);
            }
        });
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(mContext, msg);
        coreRecyclerView.getAdapter().getEmptyView();
    }

}
