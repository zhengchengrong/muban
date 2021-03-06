package com.threehmis.xcjc.module.observer;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.threehmis.xcjc.R;
import com.threehmis.xcjc.module.base.BaseActivity;
import com.threehmis.xcjc.utils.DeviceUtils;
import com.vondear.rxtools.RxLogTool;
import com.vondear.rxtools.view.RxToast;

import java.io.File;

import butterknife.BindView;

/**
 * Created by llz on 2018/1/24.
 */
// 勘察登记
public class ObserverMarkActivity2 extends BaseActivity {


    private static final int CAMERA_REQUEST_CODE = 0;
    private static final int REQUESTCODE = 10;
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
    @BindView(R.id.editTextShow)
    EditText mEditTextShow;
    @BindView(R.id.rv_addphoto)
    ImageView mRvAddphoto;
    @BindView(R.id.cb_check_end)
    CheckBox mCbCheckEnd;

    File file;
    StringBuffer sb = new StringBuffer();
    @BindView(R.id.bmapView)
    TextureMapView mBmapView;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.tv_cancle)
    TextView mTvCancle;
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
        return R.layout.act_observer_mark;
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
        mRvAddphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxToast.showToast("拍照");
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (DeviceUtils.existSDCard()) {
                    file = new File(Environment.getExternalStorageDirectory(), "/DCIM/"
                            + "/xcjc/" + System.currentTimeMillis() + ".jpg");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
                        Uri uriForFile = FileProvider.getUriForFile(ObserverMarkActivity2.this, "com.threehmis.xcjc.fileprovider", file);
                        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                        intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intentFromCapture.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    } else {
                        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    }
                }
                startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
            }
        });



        mBaiduMap = mBmapView.getMap();

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
    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mBmapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 這裡不斷監聽，獲取當前的位置
            RxLogTool.d(locData);
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
    protected void updateViews(boolean isRefresh) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            Log.e("TAG", "---------" + FileProvider.getUriForFile(this, "com.threehmis.xcjc.fileprovider", file));
            mRvAddphoto.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            //在手机相册中显示刚拍摄的图片
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(file);
            mediaScanIntent.setData(contentUri);
    }
}
