package com.dunkeng.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.learn.rxjava.LearnActivityRxjavaActivity;
import com.dunkeng.R;
import com.dunkeng.about.ActivityAbout;
import com.dunkeng.common.OnFragmentOpenDrawerListener;
import com.dunkeng.ganhuo.FragmentGanHuosMain;
import com.dunkeng.main.contract.MainContract;
import com.dunkeng.main.model.IpBean;
import com.dunkeng.main.model.IpModel;
import com.dunkeng.main.model.LunarBean;
import com.dunkeng.main.model.LunarModel;
import com.dunkeng.main.model.MainModel;
import com.dunkeng.main.presenter.MainPresenter;
import com.dunkeng.meizi.view.FragmentMeizi;
import com.dunkeng.news.FragmentNewsMain;
import com.dunkeng.tools.FragmentTools;
import com.dunkeng.wx.FragmentWx;
import com.dunkeng.zhihu.ActZhihuDetail;
import com.dunkeng.zhihu.FragmentZhihu;
import com.jakewharton.rxbinding3.view.RxView;
import com.oklib.AppManager;
import com.oklib.base.CoreBaseActivity;
import com.oklib.utils.IntentUtils;
import com.oklib.utils.Logger.Logger;
import com.oklib.utils.assist.ShareUtils;
import com.oklib.utils.helper.RxUtil;
import com.oklib.utils.network.http.mode.CacheResult;
import com.oklib.utils.view.SnackbarUtil;
import com.oklib.widget.dialog.ConfirmDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class MainActivity extends CoreBaseActivity<MainPresenter, MainModel> implements NavigationView.OnNavigationItemSelectedListener, OnFragmentOpenDrawerListener, MainContract.MainView {

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    private RxPermissions permissions;
    private ConfirmDialog confirmDialog;
    private String appName;
    private Map<String, Permission> allNeadPermissions;

    @Override
    protected void onResume() {
        super.onResume();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, (Toolbar) findViewById(R.id.toolbar), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    public void initUI(Bundle savedInstanceState) {
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.main_container, new FragmentZhihu());
        }
        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ((TextView) view.findViewById(R.id.textView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = IntentUtils.getUrlIntent("https://www.baidu.com/s?wd=%E7%97%94%E7%96%AE");
                startActivity(intent);
            }
        });
//        navigationView.setCheckedItem(R.id.nav_zhihu);
        getPermission();
    }

    private void getPermission() {
        appName = getResources().getString(R.string.app_name);
        if (allNeadPermissions == null) {
            allNeadPermissions = new HashMap<>();
        }
        if (permissions == null) {
            permissions = new RxPermissions(MainActivity.this);
        }
        permissions
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    allNeadPermissions.put(permission.name, permission);
                    //全部权限请求完毕
                    if (allNeadPermissions.size() == 2) {
                        Permission externalPermission = allNeadPermissions.get(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if (externalPermission.granted) {
                        } else {
                            allNeadPermissions.clear();
                            showMsg("请授予权限");
                            getPermission();
                        }
                    }
                });
    }

    @Override
    public void initData() {
        super.initData();
//        mPresenter.getLunarData();
//        mPresenter.getIpData();
    }

    @Override
    public void onBackPressedSupport() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressedSupport();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_zhihu) {
            loadRootFragment(R.id.main_container, new FragmentZhihu());
        } else if (id == R.id.nav_news) {
            FragmentNewsMain fragmentNewsMain = FragmentNewsMain.newInstance(0);
            loadRootFragment(R.id.main_container, fragmentNewsMain);
        } else if (id == R.id.tools) {
            loadRootFragment(R.id.main_container, new FragmentTools());

        } else if (id == R.id.learn) {
            startActivity(LearnActivityRxjavaActivity.class);
        } else if (id == R.id.nav_weixin) {
            loadRootFragment(R.id.main_container, new FragmentWx());

        } else if (id == R.id.nav_share) {
            String txt = "蹲坑中...勿扰\n--来自蹲坑APP";
            ShareUtils shareUtils = new ShareUtils(mContext);
            shareUtils.shareText(txt);
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, ActivityAbout.class));
        } else if (id == R.id.menu_name_ganhuo_category) {
            FragmentGanHuosMain fragmentNewsMain = FragmentGanHuosMain.newInstance(0);
            loadRootFragment(R.id.main_container, fragmentNewsMain);
        } else if (id == R.id.menu_name_ganhuo_fuli) {
            loadRootFragment(R.id.main_container, new FragmentMeizi());

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showMessage(String msg) {
        Logger.e(msg);
    }

    @Override
    public void onOpenDrawer() {
        if (!drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.openDrawer(GravityCompat.START);
//            mPresenter.getLunarData();
            mPresenter.getIpData();
        }
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(this, "再按一次结束蹲坑", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    finish();
                    AppManager.getAppManager().AppExit(mContext);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showLunar(LunarModel info) {
        if (info == null) {
            return;
        }
        List<LunarBean> data = info.getNewslist();
        if (data.size() <= 0) {
            return;
        }
        LunarBean lunarBean = data.get(0);
        TextView strDate = navigationView.findViewById(R.id.str_date);
        strDate.setText(lunarBean.getLunardate() + " " + lunarBean.getLubarmonth() + lunarBean.getLunarday());

        RxView.clicks(strDate).compose(RxUtil.preventRepeatClicksTransformer()).subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
//                Intent intent = IntentUtils.getCalendarIntent();
//                startActivity(intent);
            }
        });
    }

    @Override
    public void showIp(CacheResult<IpModel> ipModel) {
        if (ipModel == null) {
            return;
        }
        TextView str = navigationView.findViewById(R.id.str_ip);
        IpBean ipBean = ipModel.getCacheData().getData();
        if (ipModel.isCache()) {
            Logger.e("来自缓存");
        } else {
            Logger.e("来自网络");
        }
        str.setText(ipBean.getIp() + "(" + ipBean.getIsp() + ")\n" + ipBean.getCountry() + ipBean.getRegion() + ipBean.getCity());
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
