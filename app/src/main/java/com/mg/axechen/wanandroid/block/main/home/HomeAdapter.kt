package com.mg.axechen.wanandroid.block.main.home

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.HomeViewType
import com.mg.axechen.wanandroid.javabean.HomeViewType.VIEW_TYPE_ITEM

/**
 * Created by AxeChen on 2018/3/23.
 * 主页Adapter
 */
class HomeAdapter : BaseMultiItemQuickAdapter<HomeViewType, BaseViewHolder> {


    constructor(data: MutableList<HomeViewType>?) : super(data) {
        addItemType(VIEW_TYPE_ITEM, R.layout.item_home)
    }

    override fun convert(helper: BaseViewHolder?, item: HomeViewType?) {
        if (item!!.itemType == VIEW_TYPE_ITEM) {
            var homeData: HomeData = item.item as HomeData
            helper?.setText(R.id.ttTvName, homeData.author)
            helper?.setText(R.id.tvContent, homeData.title)
            helper?.setText(R.id.tvTime, homeData.niceDate)
            var tags: String = ""
            for (it in homeData.tags) {
                tags += it.name
            }
            helper?.setText(R.id.tvTags, tags)
            helper?.addOnClickListener(R.id.ivMore)
            helper?.addOnClickListener(R.id.ivLike)
        }
    }

}