package com.dunkeng;

import com.oklib.CoreApp;
import com.oklib.utils.Logger.Logger;
import com.oklib.utils.Logger.inner.LogcatTree;
import com.oklib.utils.network.http.ViseHttp;
import com.oklib.utils.network.http.interceptor.HttpLogInterceptor;
import com.oklib.utils.network.loader.LoaderManager;


/**
 */

public class App extends CoreApp {


    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
        initNet();
        LoaderManager.getLoader().init(this);
    }

    private void initLog() {
        Logger.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(false);//是否排版显示
        Logger.plant(new LogcatTree());//添加打印日志信息到Logcat的树
    }

    private void initNet() {
        ViseHttp.init(this);
        ViseHttp.CONFIG()
                .setCookie(true)
                //配置日志拦截器
                .interceptor(new HttpLogInterceptor()
                        .setLevel(HttpLogInterceptor.Level.BODY));

    }

}
