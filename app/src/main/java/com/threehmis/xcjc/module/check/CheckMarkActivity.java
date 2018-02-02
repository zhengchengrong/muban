package com.threehmis.xcjc.module.check;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.threehmis.xcjc.adapter.MakePicAdapter;
import com.threehmis.xcjc.api.Const;
import com.threehmis.xcjc.api.bean.GlobalConstant;
import com.threehmis.xcjc.module.base.BaseActivity;
import com.threehmis.xcjc.module.observer.ObserverMarkActivity;
import com.threehmis.xcjc.utils.CamerUtils;
import com.threehmis.xcjc.utils.DeviceUtils;
import com.threehmis.xcjc.widget.photo.IMakePic;
import com.threehmis.xcjc.widget.photo.OnImageUploadListener;
import com.threehmis.xcjc.widget.photo.PhotoInfo;
import com.vondear.rxtools.RxLogTool;
import com.vondear.rxtools.view.RxToast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by llz on 2018/1/24.
 */
// 勘察登记
public class CheckMarkActivity extends BaseActivity implements IMakePic {


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

    @BindView(R.id.rv_addphoto)
    RecyclerView mRvAddphoto;

    MakePicAdapter mMakePicAdapter;

    ArrayList<PhotoInfo> photoInfos = new ArrayList<>();


    @BindView(R.id.cb_check_end)
    CheckBox mCbCheckEnd;

    File file;
    StringBuffer sb = new StringBuffer();
    @BindView(R.id.bmapView)
    TextureMapView mBmapView;

    BaiduMap mBaiduMap;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    boolean isFirstLoc = true; // 是否首次定位
    // 每个条目对应一个id目录
    String id;

    ArrayList<String> myPics;
    @BindView(R.id.tv_sign)
    TextView mTvSign;

    @Override
    public void showEmpty() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.act_check_mark;
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
        id = getIntent().getStringExtra(Const.PIC_ID);

        if (!TextUtils.isEmpty(id)) {
            if (DeviceUtils.existSDCard()) {
                String dir = Environment.getExternalStorageDirectory() + "/xcjclog/";
                myPics = CamerUtils.GetJpgFileName(dir + id);//遍历获取该条目下所有的现场图片
                for (int i = 0; i < myPics.size(); i++) {
                    PhotoInfo photoInfo = new PhotoInfo();
                    photoInfo.setCompressPath(dir + id + "/" + myPics.get(i));
                    photoInfo.setOriginalPath(dir + id + "/" + myPics.get(i));
                    photoInfos.add(photoInfo);
                }
            }
        }
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
        //图片列表
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 4);
        mRvAddphoto.setLayoutManager(mLayoutManager);


        mMakePicAdapter = new MakePicAdapter(this, photoInfos, this, GlobalConstant.EDIT);
        mRvAddphoto.setAdapter(mMakePicAdapter);

        mTvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxToast.showToast("签到");
            }
        });
    }

    OnImageUploadListener mImageUploadListener;

    @Override
    public void setPicUploadListener(OnImageUploadListener onImageUploadListener) {
        this.mImageUploadListener = onImageUploadListener;
    }

    @Override
    public void takePic(final int position) {

        if (DeviceUtils.existSDCard()) {

            file=new File(Environment.getExternalStorageDirectory(), "/xcjclog/"+id+"/"+System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists())file.getParentFile().mkdirs(); // 一定要加这句，不然在某些机型上生成不了图片
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //7.0及以上
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri uriForFile = FileProvider.getUriForFile(CheckMarkActivity.this, "com.threehmis.xcjc.fileprovider", file);
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intentFromCapture.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
            } else {
                Uri imageUri = Uri.fromFile(file);
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
                startActivityForResult(intent,CAMERA_REQUEST_CODE);
            }
        }else{
            RxToast.showToast(Const.NO_SD);
        }
    }

    /**
     * @param position
     * @param path
     * @param compresspath
     */
    private void showInfo(int position, String path, String compresspath) {

        sb.append("原始的大小：" + new File(path).length() / 1024 + " k");
        sb.append("   ");
        sb.append("原始路径：" + path);

        sb.append("\n");

        sb.append("压缩后大小：" + new File(compresspath).length() / 1024 + " k");
        sb.append("   ");
        sb.append("压缩路径：" + compresspath + "\n");

        List<PhotoInfo> photoInfos = new ArrayList<>();
        PhotoInfo photoInfo = new PhotoInfo();
        photoInfo.setCompressPath(path);
        photoInfo.setOriginalPath(compresspath);
        photoInfos.add(photoInfo);

        if (null != mImageUploadListener) {
            this.mImageUploadListener.onImageUploadList(photoInfos);
        }

        Log.d("takephotolib", "" + sb.toString());

    }

    @Override
    public void delPic(String url) {
        RxLogTool.d(url + "");
        CamerUtils.deleteFile(url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
        if(resultCode == -1) {
            Log.e("TAG", "---------" + FileProvider.getUriForFile(this, "com.threehmis.xcjc.fileprovider", file));
            //mRvAddphoto.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            PhotoInfo photoInfo = new PhotoInfo();
            photoInfo.setCompressPath(file.getAbsolutePath());
            photoInfo.setOriginalPath(file.getAbsolutePath());
            photoInfos.add(photoInfo);
            mMakePicAdapter.notifyDataSetChanged();
            //在手机相册中显示刚拍摄的图片
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(file);
            mediaScanIntent.setData(contentUri);
        }
    }
}
