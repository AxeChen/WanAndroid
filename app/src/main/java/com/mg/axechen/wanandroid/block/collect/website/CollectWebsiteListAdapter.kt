package com.mg.axechen.wanandroid.block.collect.website

import android.content.Context
import com.bilibili.magicasakura.widgets.TintImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.javabean.SearchTag

/**
 * Created by AxeChen on 2018/4/28.
 */
class CollectWebsiteListAdapter(layoutResId: Int, data: MutableList<SearchTag>?,context: Context) : BaseQuickAdapter<SearchTag, BaseViewHolder>(layoutResId, data) {

    private var ids = mutableListOf<Int>()

    private var context: Context? = null

    init {
        this.context = context
    }

    private fun getThemeColor(): Int {
        // 封装成自定控件
        // 主题框架需要封装
        return WanAndroidApplication.instance!!.getThemeColor(context!!, WanAndroidApplication.instance!!.getTheme(context!!)!!)

    }

    fun setUnCollectIds(ids: MutableList<Int>) {
        this.ids.clear()
        this.ids.addAll(ids)
    }

    override fun convert(helper: BaseViewHolder?, item: SearchTag?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tvContent, item.name)
            helper.setText(R.id.tvLink, item.link)
            var like: TintImageView = helper.getView(R.id.ivLike)
            if (ids.contains(item.id)) {
                like.setBackgroundTintList(R.color.tab_icon_no_select)
            } else {
                like.setBackgroundTintList(getThemeColor())
            }
            helper.addOnClickListener(R.id.ivLike)
        }
    }
}