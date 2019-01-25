package com.dunkeng;

import android.util.Log;

import com.oklib.CoreApp;
import com.oklib.utils.Logger.Logger;
import com.oklib.utils.Logger.inner.LogcatTree;
import com.oklib.utils.network.http.ViseHttp;
import com.oklib.utils.network.http.interceptor.HttpLogInterceptor;


/**
 */

public class App extends CoreApp {


    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
        initNet();
    }

    private void initLog() {
        Logger.getLogConfig()
                //是否输出日志
                .configAllowLog(true)
                .configLevel(Log.VERBOSE)
                //是否排版显示
                .configShowBorders(false);
        //添加打印日志信息到Logcat的树
        Logger.plant(new LogcatTree());
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
