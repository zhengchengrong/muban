package com.threehmis.xcjc.module.report;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.threehmis.xcjc.R;
import com.threehmis.xcjc.module.base.BaseActivity;
import com.vondear.rxtools.activity.ActivityScanerCode;
import com.vondear.rxtools.view.RxToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by llz on 2018/1/25.
 */

public class ReportVerificationActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.other)
    TextView mOther;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.delivercompanyname)
    TextView mDelivercompanyname;

    @Override
    protected int attachLayoutRes() {
        return R.layout.act_report_verification;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        mTvTitle.setText("报告验证");
        mTvBack.setVisibility(View.VISIBLE);
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mOther.setVisibility(View.VISIBLE);
        mOther.setText("验证");
        mOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxToast.showToast("验证");
                Intent intent = new Intent(ReportVerificationActivity.this,ActivityScanerCode.class);
                startActivity(intent);
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
