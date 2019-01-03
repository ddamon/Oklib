package com.dunkeng.details;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dunkeng.App;
import com.dunkeng.R;
import com.dunkeng.common.Config;
import com.dunkeng.details.contract.DetailContract;
import com.dunkeng.details.model.DetailBean;
import com.dunkeng.details.model.DetailModel;
import com.dunkeng.details.presenter.DetailPresenter;
import com.oklib.base.CoreBaseActivity;
import com.oklib.utils.NetUtils;
import com.oklib.utils.view.SnackbarUtil;
import com.oklib.widget.imageloader.ImageLoader;
import com.oklib.widget.imageloader.ImageLoaderUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * Created by Damon.Han on 2017/2/9 0009.
 *
 * @author Damon
 */

public class ActWebDetail extends CoreBaseActivity<DetailPresenter, DetailModel> implements DetailContract.DetailView {

    @BindView(R.id.detail_bar_image)
    ImageView detailBarImage;
    @BindView(R.id.detail_bar_copyright)
    TextView detailBarCopyright;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.wv_detail_content)
    WebView wvDetailContent;

    @Override
    public int getLayoutId() {
        return R.layout.zhihu_details;
    }

    @Override
    public void initUI(Bundle savedInstanceState) {
        WebSettings settings = wvDetailContent.getSettings();
        if (App.getNoImageState()) {
            settings.setBlockNetworkImage(true);
        }
        if (App.getAutoCacheState()) {
            settings.setAppCacheEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);
            if (NetUtils.isConnected(mContext)) {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            }
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        wvDetailContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mPresenter.getDetail();
    }

    public static void start(Context context, View view, DetailBean detailBean) {
        Intent intent = new Intent(context, ActWebDetail.class);
        intent.putExtra(Config.ArgumentKey.ARG_DETAIL_BEAN, detailBean);
        ActivityCompat.startActivity(context, intent, ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, view, "translate_view").toBundle());
    }

    @Override
    public void showContent() {
        DetailBean info = (DetailBean) getIntent().getSerializableExtra(Config.ArgumentKey.ARG_DETAIL_BEAN);
        if (info == null) {
            return;
        }
        ImageLoaderUtil.getInstance().loadImageWithProgress(this, new ImageLoader.Builder().imgView(detailBarImage).url(info.getPicUrl()).build(), new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        });
        collapsingToolbar.setTitle(info.getTitle());
        detailBarCopyright.setText(info.getDescription());
        wvDetailContent.loadUrl(info.getUrl());
        setToolBar(toolbar,info.getTitle());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        SnackbarUtil.showShort(getWindow().getDecorView(), msg);
    }

    @Override
    public void showLog(String string) {

    }
}
