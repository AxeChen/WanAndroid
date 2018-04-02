package com.mg.axechen.wanandroid.block.main.knowledge

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.KnowledgeTreeBean

/**
 * Created by AxeChen on 2018/4/1.
 * 知识体系的adapter
 */
class KnowledgeTreeListAdapter : BaseQuickAdapter<KnowledgeTreeBean, BaseViewHolder> {

    constructor(layoutResId: Int, data: List<KnowledgeTreeBean>?) : super(layoutResId, data)

    override fun convert(helper: BaseViewHolder?, item: KnowledgeTreeBean?) {
        helper!!.setText(R.id.tvName, item!!.name)
        var kind: String = item.children.size.toString()
        kind += "分类"
        helper!!.setText(R.id.tvKinds, kind)
    }
}