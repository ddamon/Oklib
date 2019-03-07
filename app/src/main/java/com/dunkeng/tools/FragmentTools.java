package com.dunkeng.tools;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dunkeng.R;
import com.dunkeng.common.OnFragmentOpenDrawerListener;
import com.dunkeng.tools.contract.ToolsContract;
import com.dunkeng.tools.model.ToolObj;
import com.dunkeng.tools.model.ToolsModel;
import com.dunkeng.tools.presenter.ToolsPresenter;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.view.ToastUtils;
import com.oklib.widget.recyclerview.CoreRecyclerView;
import com.oklib.widget.recyclerview.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * 工具类
 */
public class FragmentTools extends CoreBaseFragment<ToolsPresenter, ToolsModel> implements ToolsContract.View {
    @BindView(R.id.recycler)
    CoreRecyclerView coreRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    protected OnFragmentOpenDrawerListener mOpenDraweListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentOpenDrawerListener) {
            mOpenDraweListener = (OnFragmentOpenDrawerListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOpenDraweListener = null;
    }

    @Override
    public void initData() {
        mPresenter.getData();
    }

    @Override
    public void showContent(List<ToolObj> info) {
        coreRecyclerView.getAdapter().addData(info);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_meizi;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        toolbar.setTitle("福利");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(v -> {
            if (mOpenDraweListener != null) {
                mOpenDraweListener.onOpenDrawer();
            }
        });

        GridLayoutManager manager = new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
        coreRecyclerView.init(manager,
                new BaseQuickAdapter<ToolObj, BaseViewHolder>(R.layout.item_tools) {
                    @Override
                    protected void convert(BaseViewHolder helper, ToolObj item) {
                        RequestOptions options = new RequestOptions();
                        options.dontAnimate();
                        Glide.with(mContext).load(item.getDrawable()).apply(options).into((ImageView) helper.getView(R.id.image));
                        helper.setText(R.id.text, item.getTitle());
                    }
                });
        coreRecyclerView.addOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(mContext, msg);
        coreRecyclerView.showLoadMoreFailedView();
    }
}
