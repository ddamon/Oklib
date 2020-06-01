package com.oklib.widget.recyclerviewpager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oklib.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@TargetApi(12)
public abstract class FragmentStatePagerAdapter extends RecyclerView.Adapter<FragmentStatePagerAdapter.FragmentViewHolder> {
    private static final String TAG = "FragmentStatePagerAdapter";

    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;
    private SparseArray<Fragment.SavedState> mStates = new SparseArray<>();
    private Set<Integer> mIds = new HashSet<>();
    private IContainerIdGenerator mContainerIdGenerator = new IContainerIdGenerator() {
        private Random mRandom = new Random();

        @Override
        public int genId(Set<Integer> idContainer) {
            return Math.abs(mRandom.nextInt());
        }
    };

    public FragmentStatePagerAdapter(FragmentManager fm) {
        mFragmentManager = fm;
    }

    /**
     * set custom idGenerator
     */
    public void setContainerIdGenerator(@NonNull IContainerIdGenerator idGenerator) {
        mContainerIdGenerator = idGenerator;
    }

    @Override
    public void onViewRecycled(FragmentViewHolder holder) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        int tagId = genTagId(holder.getAdapterPosition());
        Fragment f = mFragmentManager.findFragmentByTag(tagId + "");
        if (f != null) {
            mStates.put(tagId, mFragmentManager.saveFragmentInstanceState(f));
            mCurTransaction.remove(f);
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
        if (holder.itemView instanceof ViewGroup) {
            ((ViewGroup) holder.itemView).removeAllViews();
        }
        super.onViewRecycled(holder);
    }

    @Override
    public final FragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvp_fragment_container, parent, false);
        int id = mContainerIdGenerator.genId(mIds);
        if (parent.getContext() instanceof Activity) {
            while (((Activity) parent.getContext()).getWindow().getDecorView().findViewById(id) != null) {
                id = mContainerIdGenerator.genId(mIds);
            }
        }
        view.findViewById(R.id.rvp_fragment_container).setId(id);
        mIds.add(id);
        return new FragmentViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(final FragmentViewHolder holder, int position) {
        // do nothing
    }

    protected int genTagId(int position) {
        // itemId must not be zero
        long itemId = getItemId(position);
        if (itemId == RecyclerView.NO_ID) {
            return position + 1;
        } else {
            return (int) itemId;
        }
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    public abstract Fragment getItem(int position, Fragment.SavedState savedState);

    public abstract void onDestroyItem(int position, Fragment fragment);

    public class FragmentViewHolder extends RecyclerView.ViewHolder implements View.OnAttachStateChangeListener {

        public FragmentViewHolder(View itemView) {
            super(itemView);
            itemView.addOnAttachStateChangeListener(this);
        }

        @Override
        public void onViewAttachedToWindow(View v) {
            if (mCurTransaction == null) {
                mCurTransaction = mFragmentManager.beginTransaction();
            }
            final int tagId = genTagId(getLayoutPosition());
            final Fragment fragmentInAdapter = getItem(getLayoutPosition(), mStates.get(tagId));
            if (fragmentInAdapter != null) {
                mCurTransaction.replace(itemView.getId(), fragmentInAdapter, tagId + "");
                mCurTransaction.commitAllowingStateLoss();
                mCurTransaction = null;
                mFragmentManager.executePendingTransactions();
            }
        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            final int tagId = genTagId(getLayoutPosition());
            Fragment frag = mFragmentManager.findFragmentByTag(tagId + "");
            if (frag == null) {
                return;
            }
            if (mCurTransaction == null) {
                mCurTransaction = mFragmentManager.beginTransaction();
            }
            mStates.put(tagId, mFragmentManager.saveFragmentInstanceState(frag));
            mCurTransaction.remove(frag);
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
            mFragmentManager.executePendingTransactions();
            onDestroyItem(getLayoutPosition(), frag);
        }
    }

    public interface IContainerIdGenerator {
        int genId(Set<Integer> idContainer);
    }
}
