package com.mg.axechen.wanandroid.block.main.project

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.HomeData

/**
 * Created by AxeChen on 2018/4/2.
 *
 */
class ProjectListAdapter(layoutResId: Int, data: List<HomeData>) : BaseQuickAdapter<HomeData, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: HomeData?) {
        // 一般的viewHolder
        helper?.setText(R.id.ttTvName, item?.author)
        helper?.setText(R.id.tvContent, item?.title)
        helper?.setText(R.id.tvTime, item?.niceDate)

        helper?.addOnClickListener(R.id.ivMore)
        helper?.addOnClickListener(R.id.ivLike)
        Glide.with(mContext).load(item?.envelopePic).into(helper?.getView(R.id.ivImage))
    }


}