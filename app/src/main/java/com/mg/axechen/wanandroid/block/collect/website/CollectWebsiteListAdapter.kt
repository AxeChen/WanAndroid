package com.mg.axechen.wanandroid.block.collect.website

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.SearchTag

/**
 * Created by AxeChen on 2018/4/28.
 */
class CollectWebsiteListAdapter(layoutResId: Int, data: MutableList<SearchTag>?) : BaseQuickAdapter<SearchTag, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: SearchTag?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tvContent, item.name)
            helper.setText(R.id.tvLink, item.link)
        }
    }
}