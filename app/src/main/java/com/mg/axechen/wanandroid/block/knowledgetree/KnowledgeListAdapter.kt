package com.mg.axechen.wanandroid.block.knowledgetree

import com.bilibili.magicasakura.widgets.TintImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.javabean.HomeData

/**
 * Created by AxeChen on 2018/4/8.
 *
 */
class KnowledgeListAdapter(layoutResId: Int, data: MutableList<HomeData>?) : BaseQuickAdapter<HomeData, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: HomeData?) {
        if (helper != null && item != null) {
            helper.setText(R.id.ttTvName, item.author)
            helper.setText(R.id.tvContent, item.title)
            helper.setText(R.id.tvTime, item.niceDate)
            var like: TintImageView = helper.getView(R.id.ivLike)
            if (item.collect) {
                like.setBackgroundTintList(getThemeColor())
            } else {
                like.setBackgroundTintList(R.color.tab_icon_no_select)
            }
            helper.addOnClickListener(R.id.flLike)
        }

    }

    private fun getThemeColor(): Int {
        // 封装成自定控件
        // 主题框架需要封装
        return WanAndroidApplication.instance!!.getThemeColor(WanAndroidApplication.instance!!.applicationContext, WanAndroidApplication.instance!!.getTheme(WanAndroidApplication.instance!!.applicationContext)!!)

    }

}