package com.threehmis.bjaj.module.mian.fragment;

import android.content.Intent;
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
import com.threehmis.bjaj.module.observer.LocalObserverActivity;
import com.threehmis.bjaj.module.task.TaskActivity;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by llz on 2017/11/9.
 */

public class HomeWorkFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    Unbinder unbinder;
    private int[] icons = new int[]{R.drawable.icon_main_1, R.drawable.icon_main_2, R.drawable.icon_main_3, R.drawable.icon_main_4,
            R.drawable.icon_main_5, R.drawable.icon_main_6, R.drawable.icon_main_7};
    private String[] titles = new String[]{"任务下达", "现场勘察", "现场检测", "进度查询"
            , "报告验证", "现场签到", "通讯录"};

    BaseQuickAdapter mBaseQuickAdapter;


    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.home_work);
        rvContent.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        rvContent.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvContent.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        final ArrayList<ItemBean> list = new ArrayList<>();
        for (int i = 0; i < icons.length; i++) {
            ItemBean bean = new ItemBean();
            bean.setIconId(icons[i]);
            bean.setTitle(titles[i]);
            list.add(bean);
        }
        mBaseQuickAdapter = new BaseQuickAdapter<ItemBean, BaseViewHolder>(R.layout.lv_home_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final ItemBean changeAddressResponBean) {
                baseViewHolder.setText(R.id.tv_title, changeAddressResponBean.title);
                baseViewHolder.setImageResource(R.id.iv_icon,changeAddressResponBean.getIconId());
                baseViewHolder.getView(R.id.iv_icon).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (changeAddressResponBean.getTitle()) {
                            case "任务下达":
                                Intent intent = new Intent(mActivity, TaskActivity.class);
                                startActivity(intent);
                                break;
                            case "现场勘察":
                                Intent intent2 = new Intent(mActivity, LocalObserverActivity.class);
                                startActivity(intent2);
                                break;
                            case "现场检测":
                               // ((MainActivity) getActivity()).jumpToActivity(FieldTestActivity.class);
                                break;
                            case "进度查询":
                                break;
                            case "报告验证":
                                break;
                            case "现场签到":
                                break;
                            case "通讯录":
                                break;
                        }
                    }
                });
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
