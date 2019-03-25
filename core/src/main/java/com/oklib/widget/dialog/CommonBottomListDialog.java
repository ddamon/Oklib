package com.oklib.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shenganyuan.smac.adapter.CommonListDialogAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 单选列表窗口，无底部按钮
 */
public class CommonBottomListDialog extends BottomDialogBase {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CommonListDialogAdapter mAdapterForSingleChoose;
    private List<String> mListData;
    private ArrayList<Integer> mListDataLimitedIndex;
    private String defaultValue;
    public static final String Arg_Data_Limited_Index = "Arg_Data_Limited_Index";

    private OnSingleCheckListener onSingleCheckListener;

    public static CommonBottomListDialog newInstance(ArrayList<String> data, ArrayList<Integer> limitedDataIndex) {
        CommonBottomListDialog frg = new CommonBottomListDialog();
        Bundle extras = new Bundle();
        extras.putStringArrayList(Arg_Data, data);
        extras.putIntegerArrayList(Arg_Data_Limited_Index, limitedDataIndex);
        frg.setArguments(extras);
        return frg;
    }

    public static CommonBottomListDialog newInstance(ArrayList<String> data) {
        CommonBottomListDialog frg = new CommonBottomListDialog();
        Bundle extras = new Bundle();
        extras.putStringArrayList(Arg_Data, data);
        extras.putIntegerArrayList(Arg_Data_Limited_Index, new ArrayList<>());
        frg.setArguments(extras);
        return frg;
    }

    @Override
    public void init() {
        initArguments();
        initRecyclerView();
        bindRecyclerData();
        hideBottom();
        addDialogBody(mRecyclerView);
    }

    private void initRecyclerView() {
        Context mContext = getActivity();
        mRecyclerView = new RecyclerView(mContext);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewItemDivider(mContext, LinearLayoutManager.VERTICAL)
                .setAppearance(1, "#eeeeee"));
    }

    private void initArguments() {
        Bundle extras = getArguments();
        if (extras != null) {
            mListData = extras.getStringArrayList(Arg_Data);
            mListDataLimitedIndex = extras.getIntegerArrayList(Arg_Data_Limited_Index);
        }
    }

    private void scrollToPosition(int position) {
        mLinearLayoutManager.scrollToPositionWithOffset(position, 0);
        mLinearLayoutManager.setStackFromEnd(true);
    }

    private void bindRecyclerData() {
        mAdapterForSingleChoose = new CommonListDialogAdapter(getActivity(), mListData, mListDataLimitedIndex);
        mRecyclerView.setAdapter(mAdapterForSingleChoose);
        setCheckPosition();
        scrollToPosition(getPositionByValue(defaultValue));
        mAdapterForSingleChoose.setOnItemClickListener((parent, position) -> {
            dismiss();
            if (onSingleCheckListener != null) {
                onSingleCheckListener.onSingleCheck(position, mListData == null ? null : mListData.get(position));
            }
        });
    }

    public int getPositionByValue(String value) {
        if (mListData == null || mListData.size() == 0) {
            return 0;
        }
        int defaultIndex = mListData.indexOf(value);
        if (defaultIndex < 0 || defaultIndex > mListData.size() - 1) {
            return 0;
        }
        return defaultIndex;
    }

    public void setCheckPosition() {
        if (mListData == null || mListData.size() == 0) {
            return;
        }
        int defaultIndex = mListData.indexOf(defaultValue);
        if ( defaultIndex > mListData.size() - 1) {
            return;
        }
        if (mAdapterForSingleChoose != null) {
            mAdapterForSingleChoose.setCheckIndex(defaultIndex);
        }
    }

    public void setDefaultCheckValue(String defaultValue) {
        if (defaultValue == null || defaultValue.length() == 0) {
            return;
        }
        this.defaultValue = defaultValue;
    }

    public void addOnSingleCheckListener(OnSingleCheckListener listener) {
        onSingleCheckListener = listener;
    }

    public interface OnSingleCheckListener {
        void onSingleCheck(int position, String value);
    }

    @Override
    public void onResume() {
        super.onResume();
        resetDialogHeight(-2);
        setPositiveShown(false);
    }
}
