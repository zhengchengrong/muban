package com.threehmis.bjaj.module.mian;

import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


import com.threehmis.bjaj.R;
import com.threehmis.bjaj.module.base.BaseActivity;
import com.threehmis.bjaj.module.mian.fragment.HomeUserFragment;
import com.threehmis.bjaj.module.mian.fragment.HomeWorkFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.contentPanel)
    FrameLayout contentPanel;
    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(android.R.id.tabhost)
    FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;

    private final Class fragmentArray[] = {HomeWorkFragment.class, HomeUserFragment.class};

    private int mImageViewArray[] = {R.drawable.selector_home_1,R.drawable.selector_home_2};
    private int mTitleArray[] = {R.string.home_work,R.string.home_user};
    private String mTextviewArray[] = {"homepage", "message", "report", "guide", "mine"};
    private ImageView msgUnread;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        layoutInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.contentPanel);
        int fragmentCount = fragmentArray.length;
        for (int i = 0; i < fragmentCount; ++i) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            if (i == 0 || i == 1 || i == 3 || i == 4) {
                mTabHost.addTab(tabSpec, fragmentArray[i], null);
                mTabHost.getTabWidget().setDividerDrawable(null);
            }
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.home_tab, null);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        icon.setImageResource(mImageViewArray[index]);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(mTitleArray[index]);
        if (index == 2) {
            title.setTextColor(getResources().getColor(R.color.gary));
        }
        if (index == 1) {
            msgUnread = (ImageView) view.findViewById(R.id.tabUnread);
        }
        return view;
    }


    @Override
    public void showEmpty() {

    }
}
