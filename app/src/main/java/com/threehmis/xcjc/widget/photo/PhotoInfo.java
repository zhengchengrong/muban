package com.threehmis.xcjc.widget.photo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：liji on 2017/11/11 08:08
 * 邮箱：lijiwork@sina.com
 * QQ ：275137657
 */

public class PhotoInfo implements Parcelable {
    
    /**
     * 原始图片路径
     */
    private String originalPath;
    
    /**
     * 压缩的图片路径
     */
    private String compressPath;
    
    /**
     * 图片的网络地址
     */
    private String imgUrl;
    
    public String getImgUrl() {
        return imgUrl == null ? "" : imgUrl;
    }
    
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    
    /**
     * 图片上传到服务器返回的id
     */
    private String uid;
    
    public String getOriginalPath() {
        return originalPath == null ? "" : originalPath;
    }
    
    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }
    
    public String getCompressPath() {
        return compressPath == null ? "" : compressPath;
    }
    
    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }
    
    public String getUid() {
        return uid == null ? "" : uid;
    }
    
    public void setUid(String uid) {
        this.uid = uid;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originalPath);
        dest.writeString(this.compressPath);
        dest.writeString(this.uid);
    }
    
    public PhotoInfo() {
    }
    
    protected PhotoInfo(Parcel in) {
        this.originalPath = in.readString();
        this.compressPath = in.readString();
        this.uid = in.readString();
    }
    
    public static final Creator<PhotoInfo> CREATOR = new Creator<PhotoInfo>() {
        @Override
        public PhotoInfo createFromParcel(Parcel source) {
            return new PhotoInfo(source);
        }
        
        @Override
        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };
}
