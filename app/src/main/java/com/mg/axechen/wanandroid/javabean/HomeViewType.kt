package com.mg.axechen.wanandroid.javabean

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by AxeChen on 2018/3/26.
 *
 *
 * 首页展示
 */

class HomeViewType(private var itemType: Int, var item: Any?) : MultiItemEntity {

    fun setItemType(itemType: Int) {
        this.itemType = itemType
    }

    override fun getItemType(): Int {
        return itemType
    }

    companion object {

        val VIEW_TYPE_BANNER_LIST = 0
        val VIEW_TYPE_SELECTION = 1
        val VIEW_TYPE_ITEM = 2
        val VIEW_TYPE_BANNER_LOOP = 3
    }
}
