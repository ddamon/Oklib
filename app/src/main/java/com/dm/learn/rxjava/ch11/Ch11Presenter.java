package com.dm.learn.rxjava.ch11;

import android.view.View;

import com.jakewharton.rxbinding3.view.RxView;
import com.oklib.base.CoreBasePresenter;
import com.oklib.utils.helper.RxUtil;

import io.reactivex.functions.Consumer;
import kotlin.Unit;


/**
 * Created by Damon.Han on 2017/3/29 0029.
 *
 * @author Damon
 */

public class Ch11Presenter extends CoreBasePresenter<Ch11Contract.Model, Ch11Contract.View> {

    private String testResult;

    @Override
    public void onStart() {

    }

    public void getData() {
        mView.showMsg("tool");
    }

    /**
     * 测试点击事件
     */
    public void testClick(View view) {
        RxView.clicks(view).subscribe(new Consumer<Unit>() {
            @Override
            public void accept(Unit unit) throws Exception {
                mView.showToast("演示点击事件");
            }
        });

    }

    /**
     * 测试点击事件
     */
    public void testLongClick(View view) {
        RxView.longClicks(view).subscribe(new Consumer<Unit>() {
            @Override
            public void accept(Unit unit) throws Exception {
                mView.showToast("演示长点击事件");
            }
        });

    }

    /**
     * 测试重复点击
     */
    public void testRepeatClick(View view) {
        RxView.clicks(view).compose(RxUtil.preventRepeatClicksTransformer()).subscribe(new Consumer<Unit>() {
            @Override
            public void accept(Unit unit) throws Exception {
                mView.showToast("演示重复点击事件");
            }
        });

    }


}
