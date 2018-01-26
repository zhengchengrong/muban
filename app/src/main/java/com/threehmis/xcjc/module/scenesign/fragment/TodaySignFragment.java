package com.threehmis.xcjc.module.scenesign.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.threehmis.xcjc.R;
import com.threehmis.xcjc.module.base.BaseFragment;
import com.vondear.rxtools.RxTool;
import com.vondear.rxtools.view.RxToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by llz on 2018/1/25.
 */

public class TodaySignFragment extends BaseFragment {
    @BindView(R.id.delivercompanyname)
    TextView mDelivercompanyname;
    @BindView(R.id.tv_flag01)
    TextView mTvFlag01;
    @BindView(R.id.tv_sigin_time1)
    TextView mTvSiginTime1;
    @BindView(R.id.rl_working)
    RelativeLayout mRlWorking;
    @BindView(R.id.tv_flag02)
    TextView mTvFlag02;
    @BindView(R.id.tv_sigin_time02)
    TextView mTvSiginTime02;
    @BindView(R.id.rl_worked)
    RelativeLayout mRlWorked;
    Unbinder unbinder;

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        mRlWorking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxToast.showToast("上班签到");
            }
        });
        mRlWorked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxToast.showToast("下班签退");
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.today_sigin_fragment;
    }


}
