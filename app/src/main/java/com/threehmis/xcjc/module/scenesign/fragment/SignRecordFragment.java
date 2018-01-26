package com.threehmis.xcjc.module.scenesign.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.threehmis.xcjc.R;
import com.threehmis.xcjc.api.bean.TaskEntity;
import com.threehmis.xcjc.module.base.BaseFragment;
import com.vondear.rxtools.view.RxToast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by llz on 2018/1/25.
 */

public class SignRecordFragment extends BaseFragment {
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    BaseQuickAdapter mBaseQuickAdapter;
    ArrayList<TaskEntity> list;
    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
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
        mRvContent.setLayoutManager(new LinearLayoutManager(mActivity));
        mBaseQuickAdapter = new BaseQuickAdapter<TaskEntity, BaseViewHolder>(R.layout.sign_record_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, TaskEntity taskEntity) {
                baseViewHolder.setText(R.id.tv_name, taskEntity.getProjectNum());
                baseViewHolder.getView(R.id.tv_statu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RxToast.showToast("状态");

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
    protected int attachLayoutRes() {
        return R.layout.sign_record_fragment;
    }


}