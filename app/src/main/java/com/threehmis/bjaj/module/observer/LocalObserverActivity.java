package com.threehmis.bjaj.module.observer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.threehmis.bjaj.R;
import com.threehmis.bjaj.api.bean.TaskEntity;
import com.threehmis.bjaj.module.base.BaseActivity;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by llz on 2018/1/24.
 */

public class LocalObserverActivity extends BaseActivity {


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

    @Override
    public void showEmpty() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.act_local_observer;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        mTvTitle.setText("现场勘察");
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
        taskEntity.setProjectName("工程名称:  "+"欧风新天地家园");
        taskEntity.setProjectCheck("检测项目:  "+"静载" );
        taskEntity.setProjectNum("20根");
        list.add(taskEntity);
        TaskEntity taskEntity2 = new TaskEntity();
        taskEntity2.setProjectName("工程名称:  "+"欧风新天地家园");
        taskEntity2.setProjectCheck("检测项目:  "+"超声波无损检测" );
        taskEntity2.setProjectNum("3300n");
        list.add(taskEntity2);
        TaskEntity taskEntity3 = new TaskEntity();
        taskEntity3.setProjectName("工程名称:  "+"欧风新天地家园");
        taskEntity3.setProjectCheck("检测项目:  "+"静载" );
        taskEntity3.setProjectNum("45组");
        list.add(taskEntity3);

        mBaseQuickAdapter = new BaseQuickAdapter<TaskEntity, BaseViewHolder>(R.layout.rv_observer_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, TaskEntity taskEntity) {
                baseViewHolder.setText(R.id.tv_observer_name,taskEntity.getProjectNum());
                baseViewHolder.getView(R.id.tv_observer_dengji).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LocalObserverActivity.this,ObserverMarkActivity.class);
                        startActivity(intent);

                    }
                });
                baseViewHolder.getView(R.id.tv_observer_chaxun).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LocalObserverActivity.this,MarkQueryActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };
        mRvContent.setAdapter(mBaseQuickAdapter);
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
