package com.oklib.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oklib.R;

public class ConfirmDialog extends SweetDialog implements View.OnClickListener {

    private TextView mTvMessage;
    private Button mBtnCancle, mBtnSubmit;

    private String message;
    private String nevigateText, positiveText;
    private View.OnClickListener onNevigateClickListener, onPositiveClickListener;

    public ConfirmDialog(Context context) {
        super(context);
    }

    @Override
    public int getContentResourceId() {
        return R.layout.core_dialog_confirm;
    }

    @Override
    public void init() {
        mTvMessage = (TextView) v(R.id.confirm_message);
        mBtnCancle = (Button) v(R.id.confirm_cancle);
        mBtnSubmit = (Button) v(R.id.confirm_submit);
    }

    public ConfirmDialog setMessage(String msg) {
        message = msg;
        return this;
    }

    public ConfirmDialog setNevigateButton(String nevigateButton, View.OnClickListener listener) {
        nevigateText = nevigateButton;
        onNevigateClickListener = listener;
        return this;
    }

    public ConfirmDialog setPositiveButton(String positiveButton, View.OnClickListener listener) {
        positiveText = positiveButton;
        onPositiveClickListener = listener;
        return this;
    }

    private void setData() {
        mTvMessage.setText(message);
        if (nevigateText != null) {
            mBtnCancle.setText(nevigateText);
        }
        if (positiveText != null) {
            mBtnSubmit.setText(positiveText);
        }
        if (onNevigateClickListener != null) {
            mBtnCancle.setOnClickListener(onNevigateClickListener);
        } else {
            mBtnCancle.setOnClickListener(this);
        }
        if (onPositiveClickListener != null) {
            mBtnSubmit.setOnClickListener(onPositiveClickListener);
        } else {
            mBtnSubmit.setOnClickListener(this);
        }
    }

    @Override
    public void show() {
        super.show();
        setData();
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
