package com.threehmis.xcjc.widget.photo;

import java.util.List;

/**
 * 
 */
public interface OnImageUploadListener {
    
    void onImageUpload(PhotoInfo photoInfo);
    
    void onImageUploadList(List<PhotoInfo> photoInfos);
}
