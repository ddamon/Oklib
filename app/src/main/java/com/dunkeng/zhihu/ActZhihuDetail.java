package com.dunkeng.zhihu;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dunkeng.App;
import com.dunkeng.R;
import com.dunkeng.common.Config;
import com.dunkeng.zhihu.contract.ZhihuContract;
import com.dunkeng.zhihu.model.ZhihuDetailBean;
import com.dunkeng.zhihu.model.ZhihuModel;
import com.dunkeng.zhihu.presenter.ZhihuDetailsPresenter;
import com.oklib.base.CoreBaseActivity;
import com.oklib.utils.NetUtils;
import com.oklib.utils.file.HtmlUtil;
import com.oklib.utils.view.SnackbarUtil;
import com.oklib.utils.view.StatusBarUtil;
import com.oklib.widget.imageloader.ImageLoader;
import com.oklib.widget.imageloader.ImageLoaderUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Damon.Han on 2017/2/9 0009.
 *
 * @author Damon
 */

public class ActZhihuDetail extends CoreBaseActivity<ZhihuDetailsPresenter, ZhihuModel> implements ZhihuContract.ZhihuDetailsView {

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
    @BindView(R.id.nsv_scroller)
    NestedScrollView nsvScroller;

    private RxPermissions rxPermissions;

    @Override
    public int getLayoutId() {
        return R.layout.zhihu_details;
    }

    @Override
    public void initUI(Bundle savedInstanceState) {
        ButterKnife.bind(this);
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
        rxPermissions = new RxPermissions(this);
        requestPermission();
    }

    private void requestPermission() {
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(permission -> {
                    if (permission) {
                        mPresenter.getZhihuDetails(getIntent().getIntExtra(Config.ArgumentKey.ARG_ZHIHU_ID, -1));
                    } else {
                        showMsg("请授予权限");
                        requestPermission();
                    }
                });
    }

    public static void start(Context context, View view, int id) {
        Intent intent = new Intent(context, ActZhihuDetail.class);
        intent.putExtra(Config.ArgumentKey.ARG_ZHIHU_ID, id);
        ActivityCompat.startActivity(context, intent, ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, view, "translate_view").toBundle());
    }

    @Override
    public void showContent(ZhihuDetailBean info) {
        ImageLoaderUtil.getInstance().loadImage(mContext, new ImageLoader.Builder().imgView(detailBarImage).url(info.getImage()).build());
        collapsingToolbar.setTitle(info.getTitle());
        detailBarCopyright.setText(info.getImage_source());
        String htmlData = HtmlUtil.createHtmlData(info.getBody(), info.getCss(), info.getJs());
        wvDetailContent.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
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

}
