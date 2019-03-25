package com.oklib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.shenganyuan.smac.adapter.CommonSelectDialogAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 单选列表窗口
 */
public class CommonSelectDialog extends BottomDialogBase {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CommonSelectDialogAdapter mAdapterForSingleChoose;
    private List<String> mListData;
    private ArrayList<Integer> mListDataLimitedIndex;
    public static final String Arg_Data_Limited_Index = "Arg_Data_Limited_Index";

    private OnSingleCheckListener onSingleCheckListener;

    public static CommonSelectDialog newInstance(ArrayList<String> data, ArrayList<Integer> limitedDataIndex) {
        CommonSelectDialog frg = new CommonSelectDialog();
        Bundle extras = new Bundle();
        extras.putStringArrayList(Arg_Data, data);
        extras.putIntegerArrayList(Arg_Data_Limited_Index, limitedDataIndex);
        frg.setArguments(extras);
        return frg;
    }

    public static CommonSelectDialog newInstance(ArrayList<String> data) {
        CommonSelectDialog frg = new CommonSelectDialog();
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
        addDialogBody(mRecyclerView);
        setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
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
        mAdapterForSingleChoose = new CommonSelectDialogAdapter(getActivity(), mListData, mListDataLimitedIndex);
        mRecyclerView.setAdapter(mAdapterForSingleChoose);
        mAdapterForSingleChoose.setOnItemClickListener((parent, position) -> {
            dismiss();
            if (onSingleCheckListener != null) {
                onSingleCheckListener.onSingleCheck(position, mListData == null ? null : mListData.get(position));
            }
        });
    }


    public void addOnSingleCheckListener(OnSingleCheckListener listener) {
        onSingleCheckListener = listener;
    }

    public interface OnSingleCheckListener {
        void onSingleCheck(int position, String value);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Dialog mDialog = getDialog();
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes();
        int dialogHeight =  p.height ;
    }

    @Override
    public void onResume() {
        super.onResume();
        int dialogHeight = (int) (getScreenSize(getActivity())[1] * 0.3);
        resetDialogHeight(dialogHeight);
        setPositiveShown(false);
    }
}
