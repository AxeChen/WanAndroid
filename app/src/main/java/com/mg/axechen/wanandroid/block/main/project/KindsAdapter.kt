package com.mg.axechen.wanandroid.block.main.project

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.TreeBean

/**
 * Created by AxeChen on 2018/4/3.
 *
 * 项目界面侧滑菜单
 */
class KindsAdapter(layoutResId: Int, data: List<TreeBean>) : BaseQuickAdapter<TreeBean, BaseViewHolder>(layoutResId, data) {

    private var selectProject: TreeBean? = null

    override fun convert(helper: BaseViewHolder?, item: TreeBean?) {
        helper!!.setText(R.id.tvText, item!!.name)
        if (selectProject?.id == item.id) {
            helper.setChecked(R.id.rbCheck, true)
        } else {
            helper.setChecked(R.id.rbCheck, false)
        }
    }

    fun setSelect(selectBean: TreeBean) {
        this.selectProject = selectBean
    }


}