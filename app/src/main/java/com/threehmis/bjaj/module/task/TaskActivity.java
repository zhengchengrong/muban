package com.threehmis.bjaj.module.task;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
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

public class TaskActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    BaseQuickAdapter mBaseQuickAdapter;
    ArrayList<TaskEntity> list;

    RecyclerView rVDialog01;
    RecyclerView rVDialog02;
    View dialogView;
    BaseQuickAdapter adapterDialog01;
    BaseQuickAdapter adapterDialog02;
    @BindView(R.id.other)
    TextView mOther;
    @BindView(R.id.tv_back)
    TextView mTvBack;


    @Override
    protected int attachLayoutRes() {
        return R.layout.act_tack;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        mTvBack.setVisibility(View.VISIBLE);
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTvTitle.setText("任务下达");
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

        mBaseQuickAdapter = new BaseQuickAdapter<TaskEntity, BaseViewHolder>(R.layout.rv_task_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, TaskEntity taskEntity) {
                baseViewHolder.setText(R.id.tv_task_name, taskEntity.getProjectName());
                baseViewHolder.setText(R.id.tv_task_check, taskEntity.getProjectCheck());
                baseViewHolder.setText(R.id.tv_task_num, taskEntity.getProjectNum());
                baseViewHolder.getView(R.id.tv_task_down).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean wrapInScrollView = true;
                        new MaterialDialog.Builder(TaskActivity.this)
                                .customView(dialogView, wrapInScrollView)
                                .positiveText(R.string.sure)
                                .show();


                    }
                });
            }
        };
        mRvContent.setAdapter(mBaseQuickAdapter);
        //弹出对话框
        dialogView = TaskActivity.this.getLayoutInflater().inflate(R.layout.task_down_dialog, null);
        rVDialog01 = dialogView.findViewById(R.id.rv_task_dialog_one);
        rVDialog02 = dialogView.findViewById(R.id.rv_task_dialog_tow);
        rVDialog01.setLayoutManager(new LinearLayoutManager(this));
        rVDialog02.setLayoutManager(new LinearLayoutManager(this));

        adapterDialog01 = new BaseQuickAdapter<TaskEntity, BaseViewHolder>(R.layout.task_dialog_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, TaskEntity taskEntity) {
                baseViewHolder.setText(R.id.tv_dialog_text, taskEntity.getProjectName());
            }
        };
        rVDialog01.setAdapter(adapterDialog01);
        adapterDialog02 = new BaseQuickAdapter<TaskEntity, BaseViewHolder>(R.layout.task_dialog_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, TaskEntity taskEntity) {
                baseViewHolder.setText(R.id.tv_dialog_text, taskEntity.getProjectName());
            }
        };
        rVDialog02.setAdapter(adapterDialog02);
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
