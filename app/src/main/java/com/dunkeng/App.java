package com.dunkeng;

import android.util.Log;

import com.oklib.CoreApp;
import com.oklib.utils.Logger.Logger;
import com.oklib.utils.Logger.inner.LogcatTree;
import com.oklib.utils.network.http.ViseHttp;
import com.oklib.utils.network.http.interceptor.HttpLogInterceptor;
import com.tencent.smtt.sdk.QbSdk;


/**
 */

public class App extends CoreApp {


    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
        initNet();

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
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
