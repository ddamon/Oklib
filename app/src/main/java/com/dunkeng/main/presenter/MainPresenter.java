package com.dunkeng.main.presenter;

import com.dunkeng.main.contract.MainContract;
import com.dunkeng.main.model.IpModel;
import com.dunkeng.main.model.LunarModel;
import com.dunkeng.main.model.MainModel;
import com.oklib.base.CoreBasePresenter;
import com.oklib.utils.network.http.ViseHttp;
import com.oklib.utils.network.http.mode.CacheMode;
import com.oklib.utils.network.http.mode.CacheResult;

import io.reactivex.functions.Consumer;

/**
 * Created by Damon.Han on 2019/2/20 0020.
 *
 * @author Damon
 */
public class MainPresenter extends CoreBasePresenter<MainModel, MainContract.MainView> {
    @Override
    public void onStart() {

    }

    public void getLunarData() {
        mModel.getLunarData()
                .subscribe(
                        new Consumer<LunarModel>() {
                            @Override
                            public void accept(LunarModel lunar) throws Exception {
                                mView.showLunar(lunar);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mView.showMsg("数据加载失败");
                            }
                        });
    }

    public void getIpData() {
        mModel.getIpData()
                .compose(ViseHttp.getApiCache().<IpModel>transformer(CacheMode.FIRST_REMOTE, IpModel.class))
                .subscribe(
                        new Consumer<CacheResult<IpModel>>() {
                            @Override
                            public void accept(CacheResult<IpModel> lunar) throws Exception {
                                mView.showIp(lunar);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mView.showMsg("ip获取失败");
                            }
                        });
    }

}
