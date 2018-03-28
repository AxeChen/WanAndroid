package com.mg.axechen.wanandroid.block.main.home

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.mg.axechen.wanandroid.R

/**
 * Created by AxeChen on 2018/3/27.
 */
class CustomLoadMoreView : LoadMoreView {
    override fun getLoadEndViewId(): Int {
        return 0
    }

    constructor() : super()

    override fun getLayoutId(): Int {
        return R.layout.item_loading
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }

}