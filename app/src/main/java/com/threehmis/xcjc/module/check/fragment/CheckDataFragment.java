package com.threehmis.xcjc.module.check.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threehmis.xcjc.R;
import com.threehmis.xcjc.module.base.BaseFragment;
import com.vondear.rxtools.view.RxToast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by llz on 2018/1/25.
 */

public class CheckDataFragment extends BaseFragment {
    @BindView(R.id.rl_check_man)
    LinearLayout mRlCheckMan;
    String a;
    ArrayList<String> checkMans = new ArrayList<>();
    @BindView(R.id.save)
    TextView mSave;
    @BindView(R.id.saveandadd)
    TextView mSaveandadd;

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        checkMans.clear();
        checkMans.add("合格");
        checkMans.add("不合格");

        for (int i = 0; i < checkMans.size(); i++) {
            a = checkMans.get(i);
            CheckBox newCB = new CheckBox(mActivity);
            newCB.setText(a);
            //给checkbox设置布局属性
            mRlCheckMan.addView(newCB);
        }

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxToast.showToast("保存");
            }
        });
        mSaveandadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxToast.showToast("保存并新增");

            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_check_data;
    }



}
