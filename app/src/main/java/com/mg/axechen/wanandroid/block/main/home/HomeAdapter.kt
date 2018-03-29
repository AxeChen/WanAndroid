package com.mg.axechen.wanandroid.block.main.home

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.holder.Holder
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.BannerBean
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.HomeViewType
import com.mg.axechen.wanandroid.javabean.HomeViewType.VIEW_TYPE_BANNER_LOOP
import com.mg.axechen.wanandroid.javabean.HomeViewType.VIEW_TYPE_ITEM

/**
 * Created by AxeChen on 2018/3/23.
 * 主页Adapter
 */
class HomeAdapter : BaseMultiItemQuickAdapter<HomeViewType, BaseViewHolder> {

    // Banner选择滑动的指示器图标
    var indicator: IntArray = intArrayOf(R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused)

    var listAdapter: BannerListAdapter? = null

    constructor(data: MutableList<HomeViewType>?) : super(data) {
        addItemType(VIEW_TYPE_ITEM, R.layout.item_home)
        addItemType(VIEW_TYPE_BANNER_LOOP, R.layout.item_banner_viewpager)
        addItemType(HomeViewType.VIEW_TYPE_BANNER_LIST, R.layout.item_banner_list)
        addItemType(HomeViewType.VIEW_TYPE_SELECTION, R.layout.item_home_selection)
    }

    override fun convert(helper: BaseViewHolder?, item: HomeViewType?) {
        if (item!!.itemType == VIEW_TYPE_ITEM) {
            // 一般的viewHolder
            var index: Int = helper!!.adapterPosition
            var homeData: HomeData = item.item as HomeData
            helper?.setText(R.id.ttTvName, homeData.author)
            helper?.setText(R.id.tvContent, homeData.title)
            helper?.setText(R.id.tvTime, homeData.niceDate)

            helper?.setText(R.id.tvSuperChapterName, homeData.superChapterName)
            helper?.setText(R.id.tvChildChapterName, homeData.chapterName)
            helper?.addOnClickListener(R.id.ivMore)
            helper?.addOnClickListener(R.id.ivLike)
        } else if (item!!.itemType == VIEW_TYPE_BANNER_LOOP) {
            // 轮滑的view
            val bannerBeans: List<BannerBean> = item.item as List<BannerBean>
            val view: ConvenientBanner<BannerBean> = helper?.getView(R.id.cbLoopView)!!
            // 自定义翻页page
            view.setPages({ BannerLoopItemViewHolder() }, bannerBeans)
                    .setPageIndicator(indicator)
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                    .startTurning(3000)
                    .setManualPageable(true)
        } else if (item.itemType == HomeViewType.VIEW_TYPE_BANNER_LIST) {
            // 展示横向滑动的View
            val bannerBeans: MutableList<BannerBean> = item.item as MutableList<BannerBean>
            listAdapter = BannerListAdapter(R.layout.item_banner_item, bannerBeans)
            val recyclerView: RecyclerView = helper!!.getView(R.id.rvList)
            recyclerView.layoutManager = LinearLayoutManager(helper.itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = listAdapter
        } else if (item.itemType == HomeViewType.VIEW_TYPE_SELECTION) {
            helper!!.setText(R.id.tvSelection, item.item as String)
        }
    }

    class BannerLoopItemViewHolder : Holder<BannerBean> {

        var imageView: ImageView? = null
        var content: TextView? = null

        override fun createView(context: Context?): View {
            var view: View = LayoutInflater.from(context).inflate(R.layout.item_banner_loop_item, null)
            imageView = view.findViewById(R.id.ivImage)
            content = view.findViewById(R.id.tvContent)
            return view
        }

        override fun UpdateUI(context: Context?, position: Int, data: BannerBean) {
            content!!.setText(data.title)
            Glide.with(context).load(data.imagePath).into(imageView)
        }
    }
}