package com.mg.axechen.wanandroid.block.main.knowledge

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
import com.mg.axechen.wanandroid.javabean.SearchTag
import com.mg.axechen.wanandroid.javabean.TreeBean

/**
 * Created by AxeChen on 2018/4/1.
 * 知识体系的adapter
 */
class KnowledgeTreeListAdapter : BaseQuickAdapter<TreeBean, BaseViewHolder> {

    var layoutInflater: LayoutInflater? = null

    var openTags = mutableListOf<Int>()

    var knowledgeItemClick: KnowledgeItemListener? = null

    constructor(layoutResId: Int, data: List<TreeBean>?, context: Context) : super(layoutResId, data) {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun convert(helper: BaseViewHolder?, item: TreeBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tvName, item.name)
            var kind: String = item.children?.size.toString()
            kind += "分类"
            helper.setText(R.id.tvKinds, kind)

            var flowLayout: FlowLayout = helper.getView(R.id.flowLayout)
            var tags: MutableList<TreeBean> = item.children as MutableList<TreeBean>

            var views = mutableListOf<View>()
            for (tag in tags) {

                val textView = layoutInflater!!.inflate(R.layout.item_flowlayout, null) as TextView
                textView.text = tag.name
                textView.id = tag.id
                val margin = ViewGroup.MarginLayoutParams(ActionMenuView.LayoutParams.WRAP_CONTENT, ActionMenuView.LayoutParams.WRAP_CONTENT)
                margin.rightMargin = 10
                margin.topMargin = 10
                margin.leftMargin = 10
                margin.bottomMargin = 10
                textView.layoutParams = margin
                views.add(textView)

                textView.setOnClickListener({
                    knowledgeItemClick?.knowledgeItemClick(tag, tags.indexOf(tag), helper.adapterPosition)
                })
            }
            flowLayout.addItems(views)
            var kinds: TextView = helper.getView(R.id.tvKinds)

            if (openTags.contains(item.id)) {
                flowLayout.visibility = View.VISIBLE
            } else {
                flowLayout.visibility = View.GONE
            }

            kinds.setOnClickListener({
                if (flowLayout.visibility == View.VISIBLE) {
                    flowLayout.visibility = View.GONE
                    openTags.remove(item.id)
                } else {
                    flowLayout.visibility = View.VISIBLE
                    openTags.add(item.id)
                }
            })

        }
    }


    interface KnowledgeItemListener {
        fun knowledgeItemClick(bean: TreeBean, index: Int, position: Int)
    }
}