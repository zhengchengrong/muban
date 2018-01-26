package com.threehmis.xcjc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.threehmis.xcjc.R;
import com.threehmis.xcjc.api.bean.GlobalConstant;
import com.threehmis.xcjc.utils.ImageLoader;
import com.threehmis.xcjc.widget.photo.IMakePic;
import com.threehmis.xcjc.widget.photo.OnImageUploadListener;
import com.threehmis.xcjc.widget.photo.OnItemClickListener;
import com.threehmis.xcjc.widget.photo.PhotoInfo;

import java.util.ArrayList;
import java.util.List;
public class MakePicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    
    private Context mContext = null;
    
    private LayoutInflater mInflater = null;
    
    List<PhotoInfo> mPhotoInfoList = new ArrayList<>();
    
    private IMakePic mMakePic = null;
    
    /**
     * 显示还是编辑
     */
    private int type = GlobalConstant.SHOW;
    
    /**
     * 图片选取的最大数量，当达到最大数量是，不显示增加按钮
     */
    private int maxCount = GlobalConstant.MAX_COUNT;
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public int getMaxCount() {
        return maxCount;
    }
    
    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
    
    private OnItemClickListener mOnItemClickListener;
    
    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    
    public MakePicAdapter(Context context, List<PhotoInfo> photoInfoList, IMakePic makePic) {
        this(context, photoInfoList, makePic, GlobalConstant.SHOW);
    }
    
    public MakePicAdapter(Context context, List<PhotoInfo> photoInfoList, IMakePic makePic, int type) {
        mContext = context;
        mPhotoInfoList = photoInfoList;
        mMakePic = makePic;
        this.type = type;
        mInflater = LayoutInflater.from(mContext);
    }
    
    /**
     * 判断是否需要显示一个增加按钮，当达到最大数量是不显示增加按钮
     * @return
     */
    @Override
    public int getItemCount() {
        if (getType() == GlobalConstant.SHOW) {
            return mPhotoInfoList.size();
        }
        else {
            int num = mPhotoInfoList.size();
            return num == getMaxCount() ? getMaxCount() : ++num;
        }
    }
    
    /**
     * 判断选择哪种布局，一种是显示布局，一种是增加布局（有删除按钮）
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (getType() == GlobalConstant.SHOW) {
            return GlobalConstant.ITEM_CONTENT;
        }
        else {
            if (position == mPhotoInfoList.size()) {
                return GlobalConstant.ITEM_ADD;
            }
            return GlobalConstant.ITEM_CONTENT;
        }
    }
    
    /**
     * 返回列表数据
     *
     * @return
     */
    public List<PhotoInfo> getDataList() {
        return this.mPhotoInfoList;
    }
    
    /**
     * 更新数据集
     * @param dataList
     */
    public void setDataList(ArrayList<PhotoInfo> dataList) {
        mPhotoInfoList = dataList;
        notifyDataSetChanged();
    }
    
    /**
     * 增加一个
     * @param photoInfo
     */
    public void addItem(PhotoInfo photoInfo) {
        if (this.mPhotoInfoList.size() < getMaxCount()) {
            this.mPhotoInfoList.add(photoInfo);
            notifyDataSetChanged();
        }
    }
    
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (getType() == GlobalConstant.SHOW) {
            return new ContentViewHolder(mInflater.inflate(R.layout.item_adapter_photo_list, parent, false));
        }
        else {
            if (viewType == GlobalConstant.ITEM_ADD) {
                return new AddViewHolder(mInflater.inflate(R.layout.item_photo_add, parent, false));
            }
            else {
                return new ContentViewHolder(mInflater.inflate(R.layout.item_adapter_photo_list, parent, false));
            }
        }
    }
    
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        
        //显示图集
        if (getType() == GlobalConstant.SHOW) {
            
            //显示图片数据
            if (holder instanceof ContentViewHolder) {
                String path = "";
                PhotoInfo photoInfo = getDataList().get(position);
                if (photoInfo != null) {
                    
                    //予加载压缩地址
                    path = photoInfo.getCompressPath();
                    
                    //原始地址
                    if (TextUtils.isEmpty(path)) {
                        path = photoInfo.getOriginalPath();
                    }
                    
                    //网络地址
                    if (TextUtils.isEmpty(path)) {
                        path = photoInfo.getImgUrl();
                    }
                    
                }
                
                //加载图片
                ImageLoader.load(mContext,
                        path,
                        ((ContentViewHolder) holder).mIvPhoto,
                        R.drawable.add_road_book_take_img_photo);
                
                //删除按钮隐藏
                ((ContentViewHolder) holder).mIvDelete.setVisibility(View.GONE);
                
                //查看预览图
                ((ContentViewHolder) holder).mIvPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                        if (null != mOnItemClickListener) {
                            mOnItemClickListener.onItemClick(v, position);
                        }
                    }
                    
                });
            }
        }
        else {
            //显示图片数据
            if (holder instanceof ContentViewHolder) {
                String path = "";
                PhotoInfo photoInfo = getDataList().get(position);
                if (photoInfo != null) {
                    
                    //予加载压缩地址
                    path = photoInfo.getCompressPath();
                    
                    //原始地址
                    if (TextUtils.isEmpty(path)) {
                        path = photoInfo.getOriginalPath();
                    }
                    
                    //网络地址
                    if (TextUtils.isEmpty(path)) {
                        path = photoInfo.getImgUrl();
                    }
                    
                }
                
                //加载图片
                ImageLoader.load(mContext,
                        path,
                        ((ContentViewHolder) holder).mIvPhoto,
                        R.drawable.add_road_book_take_img_photo);
                
                //图片右上角显示删除按钮
                ((ContentViewHolder) holder).mIvDelete.setVisibility(View.VISIBLE);
                final String finalPath = path;
                
                //删除按钮操作
                ((ContentViewHolder) holder).mIvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                        getDataList().remove(holder.getLayoutPosition());
                        notifyDataSetChanged();
                        mMakePic.delPic(finalPath);
                        
                    }
                });
                
                //查看预览图
                //查看预览图
                ((ContentViewHolder) holder).mIvPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (null != mOnItemClickListener) {
                            mOnItemClickListener.onItemClick(v, position);
                        }
                    }

                });
            }
            else if (holder instanceof AddViewHolder) {
                //显示添加按钮
                ((AddViewHolder) holder).mImageAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMakePic.setPicUploadListener(new OnImageUploadListener() {
                            @Override
                            public void onImageUpload(PhotoInfo photoInfo) {
                                //成功回调
                                addItem(photoInfo);
                            }
                            
                            @Override
                            public void onImageUploadList(List<PhotoInfo> photoInfos) {
                                
                                //剩余可传图片数
                                int left = getMaxCount() - getDataList().size();
                                
                                //如果可添加的图片数量大于一次性选择的图片数量
                                if (left >= photoInfos.size()) {
                                    left = photoInfos.size();
                                }
                                
                                //依次添加
                                for (int i = 0; i < left; i++) {
                                    addItem(photoInfos.get(i));
                                }
                            }
                        });
                        mMakePic.takePic(position);
                    }
                    
                });
            }
        }
    }
    
    public static class AddViewHolder extends RecyclerView.ViewHolder {
        
        ImageView mImageAdd;
        
        public AddViewHolder(View footView) {
            super(footView);
            mImageAdd = (ImageView) itemView.findViewById(R.id.imageviewadd);
        }
        
    }
    
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        
        ImageView mIvPhoto;
        
        ImageView mIvDelete;
        
        public ContentViewHolder(View itemView) {
            super(itemView);
            mIvPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            mIvDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }
}
