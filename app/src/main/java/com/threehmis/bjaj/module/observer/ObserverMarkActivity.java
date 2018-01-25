package com.threehmis.bjaj.module.observer;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.threehmis.bjaj.R;
import com.threehmis.bjaj.module.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by llz on 2018/1/24.
 */
// 勘察登记
public class ObserverMarkActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.other)
    TextView mOther;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_observer_name)
    TextView mTvObserverName;
    @BindView(R.id.tv_observer_address)
    TextView mTvObserverAddress;
    @BindView(R.id.jingdu)
    TextView mJingdu;
    @BindView(R.id.weidu)
    TextView mWeidu;
    @BindView(R.id.iv_dingwei_flag)
    ImageView mIvDingweiFlag;
    @BindView(R.id.editTextShow)
    EditText mEditTextShow;

    @Override
    public void showEmpty() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.act_observer_mark;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        mTvTitle.setText("勘察登记");
        mTvBack.setVisibility(View.VISIBLE);
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
