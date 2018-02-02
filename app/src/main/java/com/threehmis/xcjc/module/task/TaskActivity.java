package com.threehmis.xcjc.module.task;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.ybq.android.spinkit.SpinKitView;
import com.threehmis.xcjc.R;
import com.threehmis.xcjc.api.BaseObserverNoMvp;
import com.threehmis.xcjc.api.Const;
import com.threehmis.xcjc.api.OrginzatioinBean;
import com.threehmis.xcjc.api.OrginzatioinUserBean;
import com.threehmis.xcjc.api.RetrofitFactory;
import com.threehmis.xcjc.api.RxSchedulers;
import com.threehmis.xcjc.api.TaskDownResBean;
import com.threehmis.xcjc.api.TaskSureDownBean;
import com.threehmis.xcjc.api.UserBean;
import com.threehmis.xcjc.api.bean.XCJCResponseBean;
import com.threehmis.xcjc.module.base.BaseActivity;
import com.threehmis.xcjc.utils.ACache;
import com.vondear.rxtools.RxLogTool;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.RxToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by llz on 2018/1/24.
 */

public class TaskActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    BaseQuickAdapter mBaseQuickAdapter;

    RecyclerView rVDialog01;
    RecyclerView rVDialog02;
    View dialogView;
    BaseQuickAdapter adapterDialog01;
    BaseQuickAdapter adapterDialog02;
    @BindView(R.id.other)
    TextView mOther;
    @BindView(R.id.tv_back)
    TextView mTvBack;

    ACache mACache;

    List<TaskDownResBean.RowsBean> list = new ArrayList<TaskDownResBean.RowsBean>();
    List<OrginzatioinBean.RowsBean> depdeLists = new ArrayList<OrginzatioinBean.RowsBean>();
    List<OrginzatioinUserBean.RowsBean> userLists = new ArrayList<OrginzatioinUserBean.RowsBean>();
    //展示的用户
    List<OrginzatioinUserBean.RowsBean> userListsdepater = new ArrayList<OrginzatioinUserBean.RowsBean>();

    String departmentId, xmfzrId, id;

    int pagenum = 0; //默认加载第1页

    int total = 0;

    @Override
    protected int attachLayoutRes() {
        return R.layout.act_tack;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

        showLoading();
        mACache = ACache.get(this);

        mTvBack.setVisibility(View.VISIBLE);
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTvTitle.setText("任务下达");
        mRvContent.setLayoutManager(new LinearLayoutManager(this));

        mBaseQuickAdapter = new BaseQuickAdapter<TaskDownResBean.RowsBean, BaseViewHolder>(R.layout.rv_task_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final TaskDownResBean.RowsBean taskEntity) {
                baseViewHolder.setText(R.id.tv_task_name, "工程名称:" + taskEntity.getGcName());
                baseViewHolder.setText(R.id.tv_task_check, "检测项目:" + taskEntity.getJcParam());
                baseViewHolder.setText(R.id.tv_task_num, taskEntity.getJcNum());
                baseViewHolder.getView(R.id.tv_task_down).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        id = taskEntity.getId(); // 任务检测id
                        boolean wrapInScrollView = true;
                        new MaterialDialog.Builder(TaskActivity.this)
                                .customView(dialogView, wrapInScrollView).negativeText(R.string.negative)
                                .positiveText(R.string.sure).onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                showLoading();
                                sureDown();
                            }
                        })
                                .show();
                    }
                });
            }
        };
        mRvContent.setAdapter(mBaseQuickAdapter);

        mBaseQuickAdapter.setEnableLoadMore(true);

        mBaseQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override public void onLoadMoreRequested() {
                if(pagenum<total/10) {
                    getData(++pagenum);
                }else{
                    mBaseQuickAdapter.loadMoreEnd();
                }
            }
        }, mRvContent);

        //弹出对话框
        dialogView = TaskActivity.this.getLayoutInflater().inflate(R.layout.task_down_dialog, null);
        rVDialog01 = dialogView.findViewById(R.id.rv_task_dialog_one);
        rVDialog02 = dialogView.findViewById(R.id.rv_task_dialog_tow);
        rVDialog01.setLayoutManager(new LinearLayoutManager(this));
        rVDialog02.setLayoutManager(new LinearLayoutManager(this));

        adapterDialog01 = new BaseQuickAdapter<OrginzatioinBean.RowsBean, BaseViewHolder>(R.layout.task_dialog_item, depdeLists) {
            @Override
            protected void convert(final BaseViewHolder baseViewHolder, final OrginzatioinBean.RowsBean rowsBean) {
                baseViewHolder.setText(R.id.cb_check, rowsBean.getLabel());
                final CheckBox checkBox = baseViewHolder.getView(R.id.cb_check);
                if (rowsBean.isSelect() == true) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < depdeLists.size(); i++) {
                            depdeLists.get(i).setSelect(false);
                        }
                        int index = baseViewHolder.getAdapterPosition();
                        depdeLists.get(index).setSelect(true);
                        departmentId = depdeLists.get(index).getId();//部门id
                        adapterDialog01.notifyDataSetChanged();
                        userListsdepater.clear();
                        for (int j = 0; j < userLists.size(); j++) {
                            OrginzatioinUserBean.RowsBean rowsBean = userLists.get(j);
                            if (rowsBean.getOrgnizationId().equals(depdeLists.get(index).getId())) {
                                rowsBean.setSelect(false);
                                userListsdepater.add(rowsBean);
                            }
                        }
                        adapterDialog02.notifyDataSetChanged();
                    }
                });
            }
        };
        rVDialog01.setAdapter(adapterDialog01);
        adapterDialog02 = new BaseQuickAdapter<OrginzatioinUserBean.RowsBean, BaseViewHolder>(R.layout.task_dialog_item, userListsdepater) {
            @Override
            protected void convert(final BaseViewHolder baseViewHolder, OrginzatioinUserBean.RowsBean rowsBean) {
                baseViewHolder.setText(R.id.cb_check, rowsBean.getUserName());
                final CheckBox checkBox = baseViewHolder.getView(R.id.cb_check);
                if (rowsBean.isSelect() == true) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < userListsdepater.size(); i++) {
                            userListsdepater.get(i).setSelect(false);
                        }
                        int index = baseViewHolder.getAdapterPosition();
                        xmfzrId = userListsdepater.get(index).getUserName();
                        userListsdepater.get(index).setSelect(true);
                        adapterDialog02.notifyDataSetChanged();
                    }
                });
            }
        };
        rVDialog02.setAdapter(adapterDialog02);
        getData(pagenum);
        //获取检测部门数据
        getDataDepde();
        //获取所有用户的数据
        getDataMan("");
    }

    private void sureDown() {
        String customerId = RxSPTool.getString(this, Const.CUSTOMERID);
        TaskSureDownBean taskSureDownBean = new TaskSureDownBean();
        taskSureDownBean.setId(id);
        taskSureDownBean.setCustomerId(customerId);
        taskSureDownBean.setXmfzrId(xmfzrId);
        taskSureDownBean.setDepartmentId(departmentId);
        Observable<XCJCResponseBean<UserBean>> observable = RetrofitFactory.getInstance().sureDown(taskSureDownBean);
        observable.compose(RxSchedulers.<XCJCResponseBean<UserBean>>compose()).subscribe(new BaseObserverNoMvp<UserBean>(this) {
            @Override
            protected void onHandleSuccess(UserBean userBean) {
                hideLoading();
                // 下达成功
                RxToast.showToast("下达成功");
            }
        });
    }

    private void getData(int pagenum) {
        String customerId = RxSPTool.getString(this, Const.CUSTOMERID);
        String userAccount = RxSPTool.getString(this, Const.USERACCOUNT);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("customerId", customerId);
        map.put("jcTaskXdManId", userAccount);
        map.put(Const.PAGENUM, pagenum+"");
        map.put(Const.PAGESIZE, "10");
        Observable<XCJCResponseBean<TaskDownResBean>> observable = RetrofitFactory.getInstance().getWorkList(map);
        ;
        observable.compose(RxSchedulers.<XCJCResponseBean<TaskDownResBean>>compose()).subscribe(new BaseObserverNoMvp<TaskDownResBean>(this) {
            @Override
            protected void onHandleSuccess(TaskDownResBean taskDownResBean) {
                total = taskDownResBean.getTotal();
                list.addAll(taskDownResBean.getRows());
                mBaseQuickAdapter.notifyDataSetChanged();
                mBaseQuickAdapter.loadMoreComplete();
            }
        });
    }

    private void getDataDepde() {
        String customerId = RxSPTool.getString(this, Const.CUSTOMERID);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("customerId", customerId);
        Observable<XCJCResponseBean<OrginzatioinBean>> observable = RetrofitFactory.getInstance().getOrginzationList(map);
        ;
        observable.compose(RxSchedulers.<XCJCResponseBean<OrginzatioinBean>>compose()).subscribe(new BaseObserverNoMvp<OrginzatioinBean>(this) {
            @Override
            protected void onHandleSuccess(OrginzatioinBean orginzatioinBean) {
       /*         String json = new Gson().toJson(orginzatioinBean);
                RxSPTool.putJSONCache(TaskActivity.this,Const.DEPDELIST,json);
                OrginzatioinBean orginzatioinBean1 =  new Gson().fromJson(json,OrginzatioinBean.class);*/
                depdeLists.addAll(orginzatioinBean.getRows());
                depdeLists.get(0).setSelect(true); // 默认选中第一个。
                adapterDialog01.notifyDataSetChanged();

            }
        });
    }

    private void getDataMan(String id) {
        String customerId = RxSPTool.getString(this, Const.CUSTOMERID);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("customerId", customerId);
        //   map.put("orgnizationId",id);
        Observable<XCJCResponseBean<OrginzatioinUserBean>> observable = RetrofitFactory.getInstance().getOrginzationUserList(map);
        ;
        observable.compose(RxSchedulers.<XCJCResponseBean<OrginzatioinUserBean>>compose()).subscribe(new BaseObserverNoMvp<OrginzatioinUserBean>(this) {
            @Override
            protected void onHandleSuccess(OrginzatioinUserBean orginzatioinBean) {
              //  mSpinKit.setVisibility(View.GONE);
     /*           String json = new Gson().toJson(orginzatioinBean);
                RxSPTool.putJSONCache(TaskActivity.this,Const.USERLIST,json);*/
                hideLoading();
                userLists.addAll(orginzatioinBean.getRows());
                for (int i = 0; i < orginzatioinBean.getRows().size(); i++) {
                    OrginzatioinUserBean.RowsBean rowsBean = orginzatioinBean.getRows().get(i);
                    if(depdeLists.size()>0) {
                        if (rowsBean.getOrgnizationId().equals(depdeLists.get(0).getId())) {
                            userListsdepater.add(rowsBean);
                        }
                    }
                }
                if (userListsdepater.size() == 0) {
                    // 没有数据的时候默认显示该布局
                    adapterDialog02.setEmptyView(R.layout.no_user_layout, rVDialog02);
                }
                adapterDialog02.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    public void showEmpty() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
