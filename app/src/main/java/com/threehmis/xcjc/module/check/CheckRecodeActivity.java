package com.threehmis.xcjc.module.check;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.threehmis.xcjc.R;
import com.threehmis.xcjc.api.bean.TabEntity;
import com.threehmis.xcjc.module.base.BaseActivity;
import com.threehmis.xcjc.module.check.fragment.BeginRecodeFragment;
import com.threehmis.xcjc.module.check.fragment.CheckDataFragment;
import com.threehmis.xcjc.module.check.fragment.WorkNumFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by llz on 2018/1/24.
 */
// 检测记录
public class CheckRecodeActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.other)
    TextView mOther;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.stl_check_recode)
    SegmentTabLayout mStlCheckRecode;
    @BindView(R.id.vp_check_recode)
    ViewPager mVpCheckRecode;
    private String[] mTitles = {"数据检测", "原始记录","工作量"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int attachLayoutRes() {
        return R.layout.act_check_recode;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        mTvTitle.setText("检测记录");
        mTvBack.setVisibility(View.VISIBLE);
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mFragments.add(new CheckDataFragment());
        mFragments.add(new BeginRecodeFragment());
        mFragments.add(new WorkNumFragment());
        initTab();
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private void initTab() {


        mVpCheckRecode.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mStlCheckRecode.setTabData(mTitles);
        mStlCheckRecode.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mVpCheckRecode.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mVpCheckRecode.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mStlCheckRecode.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mVpCheckRecode.setCurrentItem(0);
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
