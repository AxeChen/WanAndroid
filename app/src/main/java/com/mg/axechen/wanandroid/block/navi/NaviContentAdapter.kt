package com.mg.axechen.wanandroid.block.navi

import android.content.Context
import android.support.v7.widget.ActionMenuView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.FlowLayout
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.NaviBean

/**
 * Created by AxeChen on 2018/4/17.
 */
class NaviContentAdapter : BaseQuickAdapter<NaviBean, BaseViewHolder> {
    var layoutInflater: LayoutInflater? = null
    var itemClickLister: ItemClickListener? = null

    constructor(layoutResId: Int, data: MutableList<NaviBean>?, context: Context) : super(layoutResId, data) {
        layoutInflater = LayoutInflater.from(context)
    }


    override fun convert(helper: BaseViewHolder?, item: NaviBean?) {
        helper?.setText(R.id.tvTitle, item?.name)
        var articles: MutableList<HomeData> = item?.articles!!
        var flowLayout: FlowLayout = helper!!.getView(R.id.flowLayout)
        var views = mutableListOf<View>()
        for (tag in articles) {

            var textView = layoutInflater!!.inflate(R.layout.item_flowlayout, null) as TextView
            textView.text = tag.title
            textView.id = tag.id
            val margin = ViewGroup.MarginLayoutParams(ActionMenuView.LayoutParams.WRAP_CONTENT, ActionMenuView.LayoutParams.WRAP_CONTENT)
            margin.rightMargin = 10
            margin.topMargin = 10
            margin.leftMargin = 10
            margin.bottomMargin = 10
            textView.layoutParams = margin
            textView.setTextColor(helper.itemView.context.resources.getColor(R.color.text_primary_color))
            views.add(textView)

            textView.setOnClickListener({
                itemClickLister?.itemClick(tag)
            })
        }
        flowLayout.addItems(views)
    }

    interface ItemClickListener {
        fun itemClick(data: HomeData)
    }
}