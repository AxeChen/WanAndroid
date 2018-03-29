package com.mg.axechen.wanandroid.javabean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by AxeChen on 2018/3/26.
 * <p>
 * 首页展示
 */

public class HomeViewType implements MultiItemEntity {

    public static final int VIEW_TYPE_BANNER_LIST = 0;
    public static final int VIEW_TYPE_SELECTION = 1;
    public static final int VIEW_TYPE_ITEM = 2;
    public static final int VIEW_TYPE_BANNER_LOOP = 3;

    private int itemType;
    private Object item;

    public HomeViewType(int itemType, Object item) {
        this.itemType = itemType;
        this.item = item;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
