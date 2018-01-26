package com.threehmis.xcjc.module.mian;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.threehmis.xcjc.R;
import com.threehmis.xcjc.adapter.HomeFragmentPagerAdapter;
import com.threehmis.xcjc.api.bean.TabEntity;
import com.threehmis.xcjc.module.base.BaseActivity;
import com.threehmis.xcjc.module.login.LoginActicity;
import com.threehmis.xcjc.module.mian.fragment.HomeUserFragment;
import com.threehmis.xcjc.module.mian.fragment.HomeWorkFragment;
import com.threehmis.xcjc.utils.CamerUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionGen;

public class MainActivity extends BaseActivity {


    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tl_home)
    CommonTabLayout mTlHome;
    private String[] mTitleArray = {"工作台", "个人中心"};
    private int mIconSelectIds[] = {R.drawable.icon_home_1_unselecd, R.drawable.icon_home_2_unselecd};
    private int mIconUnselectIds[] = {R.drawable.icon_home_1_selecd, R.drawable.icon_home_2_selecd};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

        for (int i = 0; i < mTitleArray.length; i++) {
            mTabEntities.add(new TabEntity(mTitleArray[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        initTab();

        // 缺少权限时, 进入权限配置页面
        //存储授权 Manifest.permission.RECORD_AUDIO
        PermissionGen.with(this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.CALL_PHONE
                        )
                .request();
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }
    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                     int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        // 如果授权成功，先创建图片存放目录
        //1.创建拍照图片存放目录
        CamerUtils.mkdirPic();
    }
    private void initTab() {
        mTlHome.setTabData(mTabEntities);
        mTlHome.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mFragments.add(new HomeWorkFragment());
        mFragments.add(new HomeUserFragment());
        mViewPager.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager(),mFragments));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTlHome.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }

}
