package com.mg.axechen.wanandroid.block.collect.article

import android.content.Context
import com.bilibili.magicasakura.widgets.TintImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.javabean.HomeData

/**
 * Created by AxeChen on 2018/4/27.
 *
 */
class CollectListAdapter(layoutResId: Int, data: MutableList<HomeData>?, context: Context) : BaseQuickAdapter<HomeData, BaseViewHolder>(layoutResId, data) {

    private var ids = mutableListOf<Int>()

    private var context: Context? = null

    init {
        this.context = context
    }

    fun setUnCollectIds(ids: MutableList<Int>) {
        this.ids.clear()
        this.ids.addAll(ids)
    }

    override fun convert(helper: BaseViewHolder?, item: HomeData?) {
        if (helper != null && item != null) {
            helper.setText(R.id.ttTvName, item.author)
            helper.setText(R.id.tvContent, item.title)
            helper.setText(R.id.tvTime, item.niceDate)
            var like: TintImageView = helper.getView(R.id.ivLike)
            if (ids.contains(item.originId)) {
                like.setBackgroundTintList(R.color.tab_icon_no_select)
            } else {
                like.setBackgroundTintList(getThemeColor())
            }
            helper.addOnClickListener(R.id.ivMore)
            helper.addOnClickListener(R.id.flLike)
        }
    }

    private fun getThemeColor(): Int {
        // 封装成自定控件
        // 主题框架需要封装
        return WanAndroidApplication.instance!!.getThemeColor(context!!, WanAndroidApplication.instance!!.getTheme(context!!)!!)

    }
}