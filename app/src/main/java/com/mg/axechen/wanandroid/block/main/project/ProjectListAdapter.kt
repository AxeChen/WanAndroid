package com.mg.axechen.wanandroid.block.main.project

import com.bilibili.magicasakura.widgets.TintImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.javabean.HomeData

/**
 * Created by AxeChen on 2018/4/2.
 *
 */
class ProjectListAdapter(layoutResId: Int, data: List<HomeData>) : BaseQuickAdapter<HomeData, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: HomeData?) {
        if (helper != null && item != null) {
            // 一般的viewHolder
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
            Glide.with(mContext).load(item.envelopePic).into(helper.getView(R.id.ivImage))
        }
    }

    private fun getThemeColor(): Int {
        // 封装成自定控件
        // 主题框架需要封装
        return WanAndroidApplication.instance!!.getThemeColor(WanAndroidApplication.instance!!.applicationContext, WanAndroidApplication.instance!!.getTheme(WanAndroidApplication.instance!!.applicationContext)!!)
    }

}