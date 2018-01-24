package com.oklib.widget.dialog;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.oklib.R;

public abstract class SweetDialog extends Dialog {

    private Context mContext;
    private View mDialogView;

    public SweetDialog(Context context) {
        super(context, R.style.sweet_dialog);
        this.mContext = context;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    private AnimatorSet getFadeInAnimator() {
        if (mDialogView != null) {
            AnimatorSet inSet = new AnimatorSet();
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mDialogView, "alpha", 0.2f, 1.0f);
            alpha.setDuration(45);

            ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(mDialogView, "scaleX", 0.7f, 1.05f);
            ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(mDialogView, "scaleY", 0.7f, 1.05f);
            scaleX1.setDuration(45);
            scaleY1.setDuration(45);

            ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(mDialogView, "scaleX", 1.05f, 0.95f);
            ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(mDialogView, "scaleY", 1.05f, 0.95f);
            scaleX2.setDuration(35);
            scaleY2.setDuration(35);

            ObjectAnimator scaleX3 = ObjectAnimator.ofFloat(mDialogView, "scaleX", 0.95f, 1.0f);
            ObjectAnimator scaleY3 = ObjectAnimator.ofFloat(mDialogView, "scaleY", 0.95f, 1.0f);
            scaleX3.setDuration(10);
            scaleY3.setDuration(10);

            inSet.play(alpha).with(scaleX1);
            inSet.play(scaleX1).with(scaleY1);
            inSet.play(scaleX2).after(scaleY1);
            inSet.play(scaleX2).with(scaleY2);
            inSet.play(scaleX3).after(scaleY2);
            inSet.play(scaleX3).with(scaleY3);
            inSet.setInterpolator(new LinearInterpolator());
            return inSet;
        }
        return null;
    }

    private AnimatorSet getFadeOutAnimator() {
        if (mDialogView != null) {
            AnimatorSet outSet = new AnimatorSet();
            ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(mDialogView, "scaleX", 1.0f, 0.0f);
            ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(mDialogView, "scaleY", 1.0f, 0.0f);
            scaleX1.setDuration(155);
            scaleY1.setDuration(155);

            ObjectAnimator alpha = ObjectAnimator.ofFloat(mDialogView, "alpha", 1.0f, 0.0f);
            alpha.setDuration(135);

            outSet.play(scaleX1).with(scaleY1);
            outSet.play(scaleY1).with(alpha);

            outSet.setInterpolator(new LinearInterpolator());
            return outSet;
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentResourceId());
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        init();
    }

    /* contentView */
    public abstract int getContentResourceId();

    /* initiallize */
    public abstract void init();

    protected View v(int id) {
        return findViewById(id);
    }

    @Override
    public void show() {
        super.show();
        if (Build.VERSION.SDK_INT > 21) {
            AnimatorSet inSet = getFadeInAnimator();
            if (inSet != null) {
                inSet.setTarget(mDialogView);
                inSet.start();
            }
        }
    }
}
