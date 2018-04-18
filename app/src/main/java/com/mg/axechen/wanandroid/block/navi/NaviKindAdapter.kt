package com.mg.axechen.wanandroid.block.navi

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.NaviBean

/**
 * Created by AxeChen on 2018/4/17.
 */
class NaviKindAdapter(layoutResId: Int, data: MutableList<NaviBean>?) : BaseQuickAdapter<NaviBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: NaviBean?) {
        helper!!.setText(R.id.tvText, item!!.name)
    }


}