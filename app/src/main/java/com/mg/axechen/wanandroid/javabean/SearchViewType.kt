package com.mg.axechen.wanandroid.javabean

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by AxeChen on 2018/4/10.
 */

class SearchViewType(private var itemType: Int, var item: Any?) : MultiItemEntity {

    fun setItemType(itemType: Int) {
        this.itemType = itemType
    }

    override fun getItemType(): Int {
        return itemType
    }

    companion object {
        val VIEW_TYPE_SELECTION = 0
        val VIEW_TYPE_HISTORY = 1
        val VIEW_TYPE_RECOMMEND = 2
        val VIEW_TYPE_RESULT = 3
        val VIEW_TYPE_HISTORY_SELECTION = 4
    }
}
