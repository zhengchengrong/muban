package com.threehmis.xcjc.module.scenesign;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.threehmis.xcjc.R;
import com.threehmis.xcjc.module.base.BaseActivity;
import com.threehmis.xcjc.module.login.fragment.LoginCompanyFragment;
import com.threehmis.xcjc.module.login.fragment.LoginUserFragment;
import com.threehmis.xcjc.module.scenesign.fragment.SignRecordFragment;
import com.threehmis.xcjc.module.scenesign.fragment.TodaySignFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by llz on 2018/1/25.
 */

public class SceneSignActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.other)
    TextView mOther;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.segmentTabLayout)
    SegmentTabLayout mSegmentTabLayout;
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"当天签到", "签到记录"};
    @Override
    protected int attachLayoutRes() {
        return R.layout.act_scene_sign;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        mTvTitle.setText("现场签到");
        mTvBack.setVisibility(View.VISIBLE);
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TodaySignFragment tsf = new TodaySignFragment();
        SignRecordFragment srf = new SignRecordFragment();
        mFragments.add(tsf);
        mFragments.add(srf);
        mSegmentTabLayout.setTabData(mTitles, this, R.id.fl_content, mFragments);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

}
