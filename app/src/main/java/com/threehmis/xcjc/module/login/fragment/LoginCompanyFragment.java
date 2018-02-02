package com.threehmis.xcjc.module.login.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.threehmis.xcjc.R;
import com.threehmis.xcjc.api.BaseObserverNoMvp;
import com.threehmis.xcjc.api.Const;
import com.threehmis.xcjc.api.RetrofitFactory;
import com.threehmis.xcjc.api.RxSchedulers;
import com.threehmis.xcjc.api.UserBean;
import com.threehmis.xcjc.api.bean.BaseResult;
import com.threehmis.xcjc.api.bean.PictureResult;
import com.threehmis.xcjc.api.bean.XCJCResponseBean;
import com.threehmis.xcjc.module.base.BaseFragment;
import com.threehmis.xcjc.module.login.LoginActicity;
import com.threehmis.xcjc.module.mian.MainActivity;
import com.threehmis.xcjc.widget.CodeView;
import com.threehmis.xcjc.widget.SuperEditText;
import com.threehmis.xcjc.widget.supertextview.SuperTextView;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.module.wechat.pay.MD5;
import com.vondear.rxtools.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;
import io.reactivex.Observable;

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
        hideLoading();
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
                final String customerId = mEdAccount.getText().toString();
                if(TextUtils.isEmpty(customerId)){
                    RxToast.showToast("企业账号不能为空");
                    return;
                }
                final  String userAccount = mEdUser.getText().toString();
                if(TextUtils.isEmpty(userAccount)){
                    RxToast.showToast("用户账号不能为空");
                    return;
                }
                String userPass = mEdUser.getText().toString();
                if(TextUtils.isEmpty(userPass)){
                    RxToast.showToast("用户密码不能为空");
                    return;
                }
             /*   if (!mEdKey.getText().toString().equals(mCodeView.getCode())) {
                    RxToast.showToast("验证码有误");
                    return;
                }*/
                showLoading();
                UserBean userBean = new UserBean();
                userBean.setCustomerId(customerId);
                userBean.setUserAccount(userAccount);
                userBean.setUserPass(MD5.getMessageDigest(userPass.getBytes()));
                Observable<XCJCResponseBean<UserBean>> observable =   RetrofitFactory.getInstance().toLogin(userBean);;
                observable.compose(RxSchedulers.<XCJCResponseBean<UserBean>>compose()).subscribe(new BaseObserverNoMvp<UserBean>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(UserBean pictureResult) {
                      hideLoading();
                        //登陆成功，保存用户信息
                        RxSPTool.putString(getActivity(), Const.CUSTOMERID,customerId);
                        RxSPTool.putString(getActivity(), Const.USERACCOUNT,userAccount);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });

                break;
            case R.id.iv_code:
                mIvCode.setImageBitmap(mCodeView.createBitmap());
                break;
        }
    }
}
