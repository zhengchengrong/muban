package com.threehmis.bjaj.module.mian.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.threehmis.bjaj.R;
import com.threehmis.bjaj.module.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by llz on 2017/11/9.
 */

public class HomeUserFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    Unbinder unbinder;
    private int[] icons = new int[]{R.drawable.icon_home_2_1, R.drawable.icon_home_2_2, R.drawable.icon_home_2_3, R.drawable.icon_home_2_4};
    private String[] titles = new String[]{"手机认证", "关于我们", "常见问题", "操作指南"};


    @Override
    protected void initInjector() {

    }
    BaseQuickAdapter mBaseQuickAdapter;
    ArrayList<ItemBean> list;
    @Override
    protected void initViews() {
        tvTitle.setText(R.string.home_user);
        rvContent.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        rvContent.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvContent.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        list = new ArrayList<>();
        for (int i = 0; i < icons.length; i++) {
            ItemBean bean = new ItemBean();
            bean.setIconId(icons[i]);
            bean.setTitle(titles[i]);
            list.add(bean);
        }


         mBaseQuickAdapter = new BaseQuickAdapter<ItemBean, BaseViewHolder>(R.layout.lv_home_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, ItemBean changeAddressResponBean) {
                baseViewHolder.setText(R.id.tv_title, changeAddressResponBean.title);
                baseViewHolder.setImageResource(R.id.iv_icon,changeAddressResponBean.getIconId());
            }

        };
        rvContent.setAdapter(mBaseQuickAdapter);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_home_user;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    class ItemBean {
        public String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIconId() {
            return iconId;
        }

        public void setIconId(int iconId) {
            this.iconId = iconId;
        }

        public int iconId;
    }

}

