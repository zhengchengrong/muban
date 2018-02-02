package com.threehmis.xcjc.api;

import android.content.Context;
import android.widget.Toast;

import com.threehmis.xcjc.api.bean.BaseEntity;
import com.threehmis.xcjc.api.bean.BaseResult;
import com.threehmis.xcjc.api.bean.XCJCResponseBean;
import com.vondear.rxtools.RxLogTool;
import com.vondear.rxtools.view.RxToast;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by llz on 2018/1/30.
 */

public abstract class BaseObserverNoMvp<T> implements Observer<XCJCResponseBean<T>> {

    private static final String TAG = "BaseObserver";
    private Context mContext;

    protected BaseObserverNoMvp(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }
    @Override
    public void onNext(XCJCResponseBean<T> value) {
        if (value.getVerification() == true) {
            onHandleSuccess(value.getBeans());
        } else {
            onHandleError(value.getMessage());
        }
    }

    @Override
    public void onError(Throwable e) {
        RxLogTool.e(TAG, "error:" + e.toString());
    }

    @Override
    public void onComplete() {
        RxLogTool.d(TAG, "onComplete");
    }


    protected abstract void onHandleSuccess(T t);
    protected void onHandleError(String msg) {
        RxToast.showToast(mContext, msg, Toast.LENGTH_SHORT);
    }
}

