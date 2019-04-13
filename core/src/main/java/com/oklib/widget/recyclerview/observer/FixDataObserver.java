package com.oklib.widget.recyclerview.observer;

import android.support.v7.widget.RecyclerView;

import com.oklib.widget.recyclerview.adapter.RecyclerArrayAdapter;


public class FixDataObserver extends RecyclerView.AdapterDataObserver {

    private RecyclerView recyclerView;
    public FixDataObserver(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        if (recyclerView.getAdapter() instanceof RecyclerArrayAdapter) {
            RecyclerArrayAdapter adapter = (RecyclerArrayAdapter) recyclerView.getAdapter();
            if (adapter.getFooterCount() > 0 && adapter.getCount() == itemCount) {
                recyclerView.scrollToPosition(0);
            }
        }
    }
}
