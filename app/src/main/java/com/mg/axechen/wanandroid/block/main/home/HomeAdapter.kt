package com.mg.axechen.wanandroid.block.main.home

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bilibili.magicasakura.widgets.TintImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.javabean.BannerBean
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.HomeViewType
import com.mg.axechen.wanandroid.views.viewpager.AutoScrollViewPager
import com.mg.axechen.wanandroid.views.viewpager.CirclePageIndicator
import com.mg.axechen.wanandroid.views.viewpager.PhotoPagerAdapter

/**
 * Created by AxeChen on 2018/3/23.
 * 主页Adapter
 */
class HomeAdapter : BaseMultiItemQuickAdapter<HomeViewType, BaseViewHolder> {

    private var context: Context? = null

    init {
        this.context = context
    }

    private var position: Int = 0

    private var listAdapter: BannerListAdapter? = null

    var bannerImageClicListener: OnBannerImageClickListener? = null

    constructor(data: MutableList<HomeViewType>?, context: Context?) : super(data) {
        this.context = context
        addItemType(HomeViewType.VIEW_TYPE_ITEM, R.layout.item_home)
        addItemType(HomeViewType.VIEW_TYPE_BANNER_LOOP, R.layout.item_banner_viewpager)
        addItemType(HomeViewType.VIEW_TYPE_BANNER_LIST, R.layout.item_banner_list)
        addItemType(HomeViewType.VIEW_TYPE_SELECTION, R.layout.item_home_selection)
    }

    override fun convert(helper: BaseViewHolder?, item: HomeViewType?) {
        when {
            item!!.itemType == HomeViewType.VIEW_TYPE_ITEM -> {
                // 一般的viewHolder
                var index: Int = helper!!.adapterPosition
                var homeData: HomeData = item.item as HomeData
                helper.setText(R.id.ttTvName, homeData.author)
                helper.setText(R.id.tvContent, homeData.title)
                helper.setText(R.id.tvTime, homeData.niceDate)

                helper.setText(R.id.tvSuperChapterName, homeData.superChapterName)
                helper.setText(R.id.tvChildChapterName, homeData.chapterName)
                var like: TintImageView = helper.getView(R.id.ivLike)
                if (homeData.collect) {
                    like.setBackgroundTintList(getThemeColor())
                } else {
                    like.setBackgroundTintList(R.color.tab_icon_no_select)
                }
                helper.addOnClickListener(R.id.flLike)
            }
            item.itemType == HomeViewType.VIEW_TYPE_BANNER_LOOP -> {
                // 轮滑的view
                val bannerBeans = item.item as MutableList<BannerBean>
                var viewPager: AutoScrollViewPager = helper!!.getView(R.id.autoViewPager)
                var photoAdapter = PhotoPagerAdapter(context!!, bannerBeans, true)
                viewPager.adapter = photoAdapter
                var indicator: CirclePageIndicator = helper!!.getView(R.id.autoIndicator)
                indicator.setViewPager(viewPager)
                viewPager.offscreenPageLimit = bannerBeans.size
                viewPager.currentItem = position
                helper.setText(R.id.tvTitle, bannerBeans[position].title)
                viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {
                    }

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    }

                    override fun onPageSelected(index: Int) {
                        helper.setText(R.id.tvTitle, bannerBeans[index].title)
                        position = index
                    }

                })

                photoAdapter.setImageClickListener(object : PhotoPagerAdapter.ImageClickListener {
                    override fun onImageClickListener(bean: BannerBean) {
                        bannerImageClicListener?.onImageClickListener(bean)
                    }
                })

            }
            item.itemType == HomeViewType.VIEW_TYPE_BANNER_LIST -> {
                // 展示横向滑动的View
                val bannerBeans: MutableList<BannerBean> = item.item as MutableList<BannerBean>
                listAdapter = BannerListAdapter(R.layout.item_banner_item, bannerBeans)
                val recyclerView: RecyclerView = helper!!.getView(R.id.rvList)
                recyclerView.layoutManager = LinearLayoutManager(helper.itemView.context, LinearLayoutManager.HORIZONTAL, false)
                recyclerView.adapter = listAdapter
            }
            item.itemType == HomeViewType.VIEW_TYPE_SELECTION ->
                helper!!.setText(R.id.tvSelection, item.item as String)
        }
    }

    private fun getThemeColor(): Int {
        // 封装成自定控件
        // 主题框架需要封装
        return WanAndroidApplication.instance!!.getThemeColor(context!!, WanAndroidApplication.instance!!.getTheme(context!!)!!)

    }

    interface OnBannerImageClickListener {
        fun onImageClickListener(bean: BannerBean)
    }
}