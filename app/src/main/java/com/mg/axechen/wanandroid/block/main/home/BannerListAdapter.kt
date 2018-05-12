package com.mg.axechen.wanandroid.block.main.home

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.BannerBean


/**
 * Created by AxeChen on 2018/3/29.
 * 横向滑动列表的adapter
 */
class BannerListAdapter : BaseQuickAdapter<BannerBean, BaseViewHolder> {

    constructor(layoutResId: Int, data: MutableList<BannerBean>) : super(layoutResId, data)

    override fun convert(helper: BaseViewHolder?, item: BannerBean?) {
        val text = helper!!.setText(R.id.tvContent, item!!.title)
        val view: ImageView = helper?.getView(R.id.ivImage)!!
        Glide.with(mContext).load(item.imagePath).into(view)
    }

}