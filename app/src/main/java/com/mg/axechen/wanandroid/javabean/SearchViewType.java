package com.mg.axechen.wanandroid.javabean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by AxeChen on 2018/4/10.
 */

public class SearchViewType implements MultiItemEntity {

    public static final int VIEW_TYPE_SELECTION = 0;
    public static final int VIEW_TYPE_HISTORY = 1;
    public static final int VIEW_TYPE_RECOMMEND = 2;
    public static final int VIEW_TYPE_RESULT = 3;

    private int itemType;
    private Object item;

    public SearchViewType(int itemType, Object item) {
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
