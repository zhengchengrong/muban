package com.threehmis.bjaj.module.login.fragment;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.threehmis.bjaj.R;
import com.threehmis.bjaj.module.base.BaseFragment;
import com.threehmis.bjaj.module.login.LoginActicity;
import com.threehmis.bjaj.widget.CodeView;
import com.threehmis.bjaj.widget.SuperEditText;
import com.threehmis.bjaj.widget.supertextview.SuperTextView;
import com.vondear.rxtools.view.RxToast;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by llz on 2017/11/9.
 */

public class LoginCompanyFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.ed_account)
    SuperEditText mEdAccount;
    @BindView(R.id.ed_user)
    SuperEditText mEdUser;
    @BindView(R.id.ed_pass)
    SuperEditText mEdPass;
    @BindView(R.id.ed_key)
    SuperEditText mEdKey;
    @BindView(R.id.iv_code)
    ImageView mIvCode;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    private CodeView mCodeView;



    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        mTvLogin.setOnClickListener(this);
        mIvCode.setOnClickListener(this);
        mCodeView = CodeView.getInstance();
        Bitmap bitmap = mCodeView.createBitmap();
        mIvCode.setImageBitmap(bitmap);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_logincompany;
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login:
                if (!mEdKey.getText().toString().equals(mCodeView.getCode())) {
                    RxToast.showToast("验证码有误");
                }
                break;
            case R.id.iv_code:
                mIvCode.setImageBitmap(mCodeView.createBitmap());
                break;
        }
    }
}
