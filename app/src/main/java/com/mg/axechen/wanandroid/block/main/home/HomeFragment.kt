package com.mg.axechen.wanandroid.block.main.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectFragment
import com.mg.axechen.wanandroid.block.details.DetailActivity
import com.mg.axechen.wanandroid.block.details.WebViewActivity
import com.mg.axechen.wanandroid.javabean.BannerBean
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.HomeListBean
import com.mg.axechen.wanandroid.javabean.HomeViewType
import com.mg.axechen.wanandroid.utils.SharePreferencesContants
import com.mg.axechen.wanandroid.utils.SharedPreferencesUtils
import kotlinx.android.synthetic.main.fragment_home.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/3/23.
 * 主页文章列表
 */
class HomeFragment : BaseCollectFragment(), HomeContract.View {


    private var selectId: Int = 0

    /**
     * HomeItemList
     */
    private val datas = mutableListOf<HomeViewType>()

    /**
     * 循环轮询的数据
     */
    private val bannerLoopDatas = mutableListOf<BannerBean>()

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(datas, activity)
    }

    private val presenter: HomePresenter by lazy {
        HomePresenter(SchedulerProvider.getInstatnce()!!, this, this)
    }

    override fun getHomeListFail(msg: String, isRefresh: Boolean) {
        homeAdapter.loadMoreComplete()
    }

    override fun getHomeListSuccess(homeListBean: HomeListBean, isRefresh: Boolean) {
        if (homeListBean.datas != null) {

            if (isRefresh) {
                datas
                        .filter { it -> it.itemType == HomeViewType.VIEW_TYPE_ITEM }
                        .forEach { it -> datas.remove(it) }
            }

            var homedatas: List<HomeData> = homeListBean.datas!!

            sRefresh.isRefreshing = false
            homedatas.mapTo(datas) { it -> HomeViewType(HomeViewType.VIEW_TYPE_ITEM, it) }
            homeAdapter.loadMoreComplete()
            homeAdapter.notifyDataSetChanged()
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    private fun initRefresh() {
        // 封装成自定控件
        // 主题框架需要封装
        var color: Int = WanAndroidApplication.instance!!.getThemeColor(activity, WanAndroidApplication.instance!!.getTheme(activity)!!)
        sRefresh.setColorSchemeColors(resources.getColor(color), resources.getColor(color), resources.getColor(color))
        sRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            presenter.getHomeList(true)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerLoginStatusReceiver()
        initRefresh()
        presenter.getBannerData(true)
        rvList.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = homeAdapter
        }
        homeAdapter.setOnItemClickListener { adapter, view, position ->
            var homeData: HomeData = homeAdapter.data.get(position).item as HomeData
            DetailActivity.lunch(activity, homeData, homeData.collect, homeData.id)
        }

        homeAdapter.setPreLoadNumber(0)
        homeAdapter.setEnableLoadMore(true)
        homeAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.flLike) {
                if (SharedPreferencesUtils.getInt(SharePreferencesContants.USER_ID) == 0) {
                    Toast.makeText(activity,getString(R.string.collect_fail_pls_login),Toast.LENGTH_SHORT).show()
                } else {
                    var homdata: HomeData = datas[position].item as HomeData
                    selectId = homdata.id
                    if (homdata.collect) {
                        presenter.unCollectArticle(selectId)
                        addCollectStatus(homdata)
                    } else {
                        removeCollectStatus(homdata)
                        presenter.collectInArticle(selectId)
                    }
                }

            }
        }

        homeAdapter.setLoadMoreView(CustomLoadMoreView())
        homeAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            presenter.getHomeList(false)
        }, rvList)
        homeAdapter.bannerImageClicListener = object : HomeAdapter.OnBannerImageClickListener {
            override fun onImageClickListener(bean: BannerBean) {
                WebViewActivity.lunch(activity, bean.url!!, bean.title!!)
            }
        }
    }

    private fun addCollectStatus(homeData: HomeData) {
        homeData.collect = false
        homeAdapter.notifyDataSetChanged()
    }

    private fun removeCollectStatus(homeData: HomeData) {
        homeData.collect = true
        homeAdapter.notifyDataSetChanged()
    }

    override fun showBanner(banners: List<BannerBean>, isRefresh: Boolean) {

        if (isRefresh) {
            datas.clear()
        }

        bannerLoopDatas += banners
        // 先添加图片轮询的数据
        datas.add(HomeViewType(HomeViewType.VIEW_TYPE_BANNER_LOOP, bannerLoopDatas))
        // 然后添加推荐的文章
        datas.add(HomeViewType(HomeViewType.VIEW_TYPE_SELECTION, "最新博文"))
        homeAdapter.notifyDataSetChanged()
        // 请求最新博文
        presenter.getHomeList(isRefresh)
    }

    override fun getBannerFail(errorMsg: String, isRefresh: Boolean) {
        homeAdapter.loadMoreComplete()
    }

    override fun onResume() {
        super.onResume()
        initRefresh()
    }

    override fun changeThemeRefresh() {
        super.changeThemeRefresh()
        homeAdapter.notifyDataSetChanged()
    }

    override fun loginSuccess() {
        presenter.getHomeList(true)
    }

    override fun collectStatusChange(id: Int, isCollect: Boolean) {
        super.collectStatusChange(id, isCollect)
        datas
                .filter { it -> it.itemType == HomeViewType.VIEW_TYPE_ITEM }
                .map { it -> it.item as HomeData }
                .filter { it.id == id }
                .forEach { it.collect = isCollect }
        homeAdapter.notifyDataSetChanged()
    }
}