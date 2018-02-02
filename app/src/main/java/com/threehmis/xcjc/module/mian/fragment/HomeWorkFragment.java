package com.threehmis.xcjc.module.mian.fragment;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.threehmis.xcjc.R;
import com.threehmis.xcjc.api.BaseObserverNoMvp;
import com.threehmis.xcjc.api.Const;
import com.threehmis.xcjc.api.RetrofitFactory;
import com.threehmis.xcjc.api.RxSchedulers;
import com.threehmis.xcjc.api.TodoGroupBean;
import com.threehmis.xcjc.api.WorkNumBean;
import com.threehmis.xcjc.api.bean.XCJCResponseBean;
import com.threehmis.xcjc.module.base.BaseFragment;
import com.threehmis.xcjc.module.check.LocalCheckActivity;
import com.threehmis.xcjc.module.contact.ContactsActivity;
import com.threehmis.xcjc.module.scenesign.SceneSignActivity;
import com.threehmis.xcjc.module.observer.LocalObserverActivity;
import com.threehmis.xcjc.module.progressquery.ProgessQueryActivity;
import com.threehmis.xcjc.module.report.ReportVerificationActivity;
import com.threehmis.xcjc.module.task.TaskActivity;
import com.vondear.rxtools.RxSPTool;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by llz on 2017/11/9.
 */

public class HomeWorkFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    private int[] icons = new int[]{R.drawable.icon_main_1, R.drawable.icon_main_2, R.drawable.icon_main_3, R.drawable.icon_main_4,
            R.drawable.icon_main_5, R.drawable.icon_main_6, R.drawable.icon_main_7};
    private String[] titles = new String[]{"任务下达", "现场勘察", "现场检测", "进度查询"
            , "报告验证", "现场签到", "通讯录"};

    BaseQuickAdapter mBaseQuickAdapter;
    TodoGroupBean.BeanBean bean;

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.home_work);
        rvContent.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        rvContent.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvContent.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        final ArrayList<ItemBean> list = new ArrayList<>();
        for (int i = 0; i < icons.length; i++) {
            ItemBean bean = new ItemBean();
            bean.setIconId(icons[i]);
            bean.setTitle(titles[i]);
            list.add(bean);
        }
        mBaseQuickAdapter = new BaseQuickAdapter<ItemBean, BaseViewHolder>(R.layout.lv_home_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final ItemBean changeAddressResponBean) {
                if(baseViewHolder.getAdapterPosition() == 0){
                    if(bean!=null)
                    baseViewHolder.setText(R.id.tv_num, "("+bean.getTaskAssignCount()+")");
                }
                if(baseViewHolder.getAdapterPosition() == 1){
                    if(bean!=null)
                    baseViewHolder.setText(R.id.tv_num, "("+bean.getInvestigationCount()+")");
                }
                if(baseViewHolder.getAdapterPosition() == 2){
                    if(bean!=null)
                    baseViewHolder.setText(R.id.tv_num, "("+bean.getDetectionCount()+")");
                }
                baseViewHolder.setText(R.id.tv_title, changeAddressResponBean.title);
                baseViewHolder.setImageResource(R.id.iv_icon,changeAddressResponBean.getIconId());
                baseViewHolder.getView(R.id.iv_icon).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (changeAddressResponBean.getTitle()) {
                            case "任务下达":
                                Intent intent = new Intent(mActivity, TaskActivity.class);
                                startActivity(intent);
                                break;
                            case "现场勘察":
                                Intent intent2 = new Intent(mActivity, LocalObserverActivity.class);
                                startActivity(intent2);
                                break;
                            case "现场检测":
                                Intent intent3 = new Intent(mActivity, LocalCheckActivity.class);
                                startActivity(intent3);
                                break;
                            case "进度查询":
                                Intent intent4 = new Intent(mActivity, ProgessQueryActivity.class);
                                startActivity(intent4);
                                break;
                            case "报告验证":
                                Intent intent5 = new Intent(mActivity, ReportVerificationActivity.class);
                                startActivity(intent5);
                                break;
                            case "现场签到":
                                Intent intent6 = new Intent(mActivity, SceneSignActivity.class);
                                startActivity(intent6);
                                break;
                            case "通讯录":
                                Intent intent7 = new Intent(mActivity, ContactsActivity.class);
                                startActivity(intent7);
                                break;
                        }
                    }
                });
            }

        };
        rvContent.setAdapter(mBaseQuickAdapter);
        getDatas();
    }
    private void getDatas() {
        WorkNumBean workNumBean = new WorkNumBean();
        String customerId = RxSPTool.getString(mActivity, Const.CUSTOMERID);
        String userAccount = RxSPTool.getString(mActivity, Const.USERACCOUNT);
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("clManId",customerId);
        map.put("customerId",userAccount);
        Observable<XCJCResponseBean<TodoGroupBean>> observable =   RetrofitFactory.getInstance().getWorkNum(map);;
        observable.compose(RxSchedulers.<XCJCResponseBean<TodoGroupBean>>compose()).subscribe(new BaseObserverNoMvp<TodoGroupBean>(getActivity()) {
            @Override
            protected void onHandleSuccess(TodoGroupBean pictureResult) {
                 bean = pictureResult.getBean();
                 mBaseQuickAdapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_home_user;
    }



    class ItemBean {
        public String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIconId() {
            return iconId;
        }

        public void setIconId(int iconId) {
            this.iconId = iconId;
        }

        public int iconId;
    }

}
