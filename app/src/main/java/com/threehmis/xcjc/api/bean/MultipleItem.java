package com.threehmis.xcjc.api.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by llz on 2018/1/26.
 */

public class MultipleItem implements MultiItemEntity {
    public static final int TEXT = 0;
    public static final int IMG = 1;
    private int itemType;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;


    public MultipleItem(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
