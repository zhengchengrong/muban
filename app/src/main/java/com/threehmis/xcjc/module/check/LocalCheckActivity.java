package com.threehmis.xcjc.module.check;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.threehmis.xcjc.R;
import com.threehmis.xcjc.api.Const;
import com.threehmis.xcjc.api.bean.TaskEntity;
import com.threehmis.xcjc.module.base.BaseActivity;
import com.vondear.rxtools.view.RxToast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by llz on 2018/1/24.
 */

public class LocalCheckActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.other)
    TextView mOther;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    BaseQuickAdapter mBaseQuickAdapter;
    ArrayList<TaskEntity> list;
    @BindView(R.id.iv_delete)
    ImageView mIvDelete;

    @Override
    public void showEmpty() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.act_local_check;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        mTvTitle.setText("现场检测");
        mTvBack.setVisibility(View.VISIBLE);
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setProjectName("工程名称:  " + "欧风新天地家园");
        taskEntity.setProjectCheck("检测项目:  " + "静载");
        taskEntity.setProjectNum("20根");
        list.add(taskEntity);
        TaskEntity taskEntity2 = new TaskEntity();
        taskEntity2.setProjectName("工程名称:  " + "欧风新天地家园");
        taskEntity2.setProjectCheck("检测项目:  " + "超声波无损检测");
        taskEntity2.setProjectNum("3300n");
        list.add(taskEntity2);
        TaskEntity taskEntity3 = new TaskEntity();
        taskEntity3.setProjectName("工程名称:  " + "欧风新天地家园");
        taskEntity3.setProjectCheck("检测项目:  " + "静载");
        taskEntity3.setProjectNum("45组");
        list.add(taskEntity3);

        mBaseQuickAdapter = new BaseQuickAdapter<TaskEntity, BaseViewHolder>(R.layout.rv_check_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final TaskEntity taskEntity) {
                baseViewHolder.setText(R.id.tv_observer_name, taskEntity.getProjectNum());
                baseViewHolder.getView(R.id.tv_observer_dengji).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LocalCheckActivity.this, CheckMarkActivity.class);
                        intent.putExtra(Const.PIC_ID,taskEntity.getProjectNum());
                        startActivity(intent);

                    }
                });
                baseViewHolder.getView(R.id.tv_observer_chaxun).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LocalCheckActivity.this, CheckRecodeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };
        mRvContent.setAdapter(mBaseQuickAdapter);
        mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEtSearch.setText("");
            }
        });
        mEtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                Log.e("输入过程中执行该方法", "文字变化");
                if (s.length() > 0) {
                    mIvDelete.setVisibility(View.VISIBLE);
                }else{
                    mIvDelete.setVisibility(View.GONE);

                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听
                Log.e("输入前确认执行该方法", "开始输入");

            }
            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                Log.e("输入结束执行该方法", "输入结束");
            }
        });
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(mEtSearch.getText())){
                    RxToast.showToast("请输入内容");
                }else{
                    RxToast.showToast(mEtSearch.getText().toString());
                }
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
