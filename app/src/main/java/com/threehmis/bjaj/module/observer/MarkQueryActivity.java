package com.threehmis.bjaj.module.observer;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.threehmis.bjaj.R;
import com.threehmis.bjaj.api.bean.TaskEntity;
import com.threehmis.bjaj.module.base.BaseActivity;
import com.threehmis.bjaj.utils.DensityUtils;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.view.RxToast;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by llz on 2018/1/24.
 */
// 勘察登记
public class MarkQueryActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.other)
    TextView mOther;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_observer_name)
    TextView mTvObserverName;
    @BindView(R.id.tv_observer_address)
    TextView mTvObserverAddress;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;

    BaseQuickAdapter mBaseQuickAdapter;
    ArrayList<TaskEntity> list;
    @BindView(R.id.bmapView)
    TextureMapView mMapView;
    BaiduMap mBaiduMap;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    boolean isFirstLoc = true; // 是否首次定位

    @Override
    public void showEmpty() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.act_mark_query;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        mTvTitle.setText("勘察登记");
        mTvBack.setVisibility(View.VISIBLE);
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mBaseQuickAdapter = new BaseQuickAdapter<TaskEntity, BaseViewHolder>(R.layout.mark_query_item, list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, TaskEntity taskEntity) {
                baseViewHolder.setText(R.id.tv_mark_name, taskEntity.getProjectNum());
                baseViewHolder.getView(R.id.tv_mark_detail).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RxToast.showToast("详情");

                    }
                });
            }
        };
        mRvContent.setAdapter(mBaseQuickAdapter);


        mBaiduMap = mMapView.getMap();

        //设置地图缩放级别
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类
        option.setScanSpan(5000); //15秒
        option.setPriority(LocationClientOption.GpsFirst); // 设置GPS优先
        option.disableCache(false);// 禁止启用缓存定位
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }
    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 這裡不斷監聽，獲取當前的位置
            RxLogUtils.d(locData);
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

            mBaiduMap
                    .setMyLocationConfigeration(new MyLocationConfiguration(
                            MyLocationConfiguration.LocationMode.NORMAL, true, null));
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
