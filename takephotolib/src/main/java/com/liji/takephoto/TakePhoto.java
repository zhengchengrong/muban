package com.liji.takephoto;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 作者：liji on 2016/7/11 15:59
 * 邮箱：lijiwork@sina.com
 */
public class TakePhoto implements ICanShow {
    
    private Context context;
    
    private PopupWindow popwindow;
    
    private View popview;
    
    private final int REQUEST_CODE_CAMERA = 1000;
    
    private final int REQUEST_CODE_GALLERY = 1001;
    
    private RelativeLayout btnBendi;
    
    private RelativeLayout btnCapture;
    
    private RelativeLayout btnCancel;
    
    FunctionConfig functionConfig;
    
    private onPictureSelected mPictureSelected = null;
    
    /**
     * 图片选择结果回调
     */
    public interface onPictureSelected {
        void select(String path, String compresspaht);
    }
    
    public void setOnPictureSelected(onPictureSelected selected) {
        this.mPictureSelected = selected;
    }
    
    public TakePhoto(final Context context) {
        
        this.context = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        popview = layoutInflater.inflate(R.layout.pop_picture_select, null);
        btnBendi = (RelativeLayout) popview.findViewById(R.id.btn_bendi);
        btnCancel = (RelativeLayout) popview.findViewById(R.id.btn_cancel);
        btnCapture = (RelativeLayout) popview.findViewById(R.id.btn_paizhao);
        popwindow = new PopupWindow(popview, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popwindow.setBackgroundDrawable(new ColorDrawable());
        popwindow.setAnimationStyle(R.style.AnimBottom);
        popwindow.setTouchable(true);
        popwindow.setOutsideTouchable(true);
        popwindow.setFocusable(true);
        
        popwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(context, 1.0f);
            }
        });
        
        ThemeConfig theme = ThemeConfig.DEFAULT;
        
        //配置功能
        functionConfig = new FunctionConfig.Builder().setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();
        
        //配置imageloader
        cn.finalteam.galleryfinal.ImageLoader imageloader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme).setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
        
        //取消
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        
        //本地相册
        btnBendi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
                hide();
            }
        });
        
        //拍照
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);
                hide();
            }
        });
        
    }
    
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
            
            if (!TextUtils.isEmpty(resultList.get(0).getPhotoPath())) {
                Luban.with(context)
                        .load(new File(resultList.get(0).getPhotoPath()))
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                
                            }
                            
                            @Override
                            public void onSuccess(File file) {
                                Log.i("path", file.getAbsolutePath());
                                
                                if (resultList != null) {
                                    try {
                                        mPictureSelected.select(resultList.get(0).getPhotoPath(),
                                                file.getAbsolutePath());
                                    }
                                    catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    finally {
                                        hide();
                                    }
                                }
                            }
                            
                            @Override
                            public void onError(Throwable e) {
                                
                            }
                        })
                        .launch();
            }
            
        }
        
        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            hide();
        }
    };
    
    @Override
    public void show() {
        if (!isShow()) {
            setBackgroundAlpha(context, 0.5f);
            popwindow.showAtLocation(popview, Gravity.BOTTOM, 0, 0);
        }
    }
    
    @Override
    public void hide() {
        if (isShow()) {
            
            popwindow.dismiss();
        }
    }
    
    public void setBackgroundAlpha(Context context, float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }
    
    @Override
    public boolean isShow() {
        return popwindow.isShowing();
    }
}