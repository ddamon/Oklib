package com.dm.learn.rxjava.ch11;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.dunkeng.R;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.oklib.base.CoreBaseActivity;
import com.oklib.utils.helper.RxUtil;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * Created by Damon.Han on 2019/2/19 0019.
 *
 * @author Damon
 */
public class ActivityValidateText extends CoreBaseActivity {
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.button)
    Button button;
    ValidateResult r = new ValidateResult();


    @Override
    public int getLayoutId() {
        return R.layout.learn_rxjava_activity_validate_txt;
    }

    @Override
    public void initUI(Bundle savedInstanceState) {

        valiate();
        RxView.clicks(button).compose(RxUtil.preventRepeatClicksTransformer()).subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                if (r == null) {
                    return;
                }
                if (r.flag) {
                    showToast("登陆成功");
                } else {
                    showToast(r.message);
                }
            }
        });
    }

    private void valiate() {
        Observable<CharSequence> observablePhone;
        Observable<CharSequence> observablePassword;
        observablePhone = RxTextView.textChanges(editText);
        observablePassword = RxTextView.textChanges(editText2);
        Observable.combineLatest(observablePhone, observablePassword, new BiFunction<CharSequence, CharSequence, ValidateResult>() {
            @Override
            public ValidateResult apply(CharSequence charSequence, CharSequence charSequence2) throws Exception {
                if (charSequence.length() == 0 || charSequence2.length() == 0) {
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                }
                ValidateResult result = new ValidateResult();
                if (charSequence.length() != 11) {
                    result.flag = false;
                    result.message = "请输入合适的手机号码";
                } else if (charSequence2.length() != 6) {
                    result.flag = false;
                    result.message = "密码必须为6位";
                } else {
                    button.setEnabled(true);
                    result.flag = true;
                    result.message = "登录成功";
                }

                return result;
            }
        }).subscribe(new Consumer<ValidateResult>() {
            @Override
            public void accept(ValidateResult validateResult) throws Exception {
                r = validateResult;
            }
        });

    }
}
