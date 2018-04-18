package com.mg.axechen.wanandroid.block.knowledgetree

import android.widget.FrameLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.HomeData

/**
 * Created by AxeChen on 2018/4/8.
 *
 */
class KnowledgeListAdapter(layoutResId: Int, data: MutableList<HomeData>?) : BaseQuickAdapter<HomeData, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: HomeData?) {
        if (helper != null) {
            helper.setText(R.id.ttTvName, item?.author)
            helper.setText(R.id.tvContent, item?.title)
            helper.setText(R.id.tvTime, item?.niceDate)
            helper.setText(R.id.tvSuperChapterName, item?.superChapterName)
            helper.setText(R.id.tvChildChapterName, item?.chapterName)
            helper.addOnClickListener(R.id.ivMore)
            helper.addOnClickListener(R.id.ivLike)
        }

    }

}