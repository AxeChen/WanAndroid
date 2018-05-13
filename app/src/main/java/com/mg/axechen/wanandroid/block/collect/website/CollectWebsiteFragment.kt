package com.mg.axechen.wanandroid.block.collect.website

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectFragment
import com.mg.axechen.wanandroid.block.details.WebViewActivity
import com.mg.axechen.wanandroid.javabean.SearchTag
import kotlinx.android.synthetic.main.fragment_home.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/4/19.
 * 收藏列表界面
 */
class CollectWebsiteFragment : BaseCollectFragment(), CollectWebsiteContract.View {

    /**
     * 未收藏的集合
     */
    private var unCollectIds = mutableListOf<Int>()

    private var selectId: Int = 0

    private var datas = mutableListOf<SearchTag>()

    override fun getCollectWebSiteFail(msg: String) {
    }

    private val presenter: CollectWebsitePresenter by lazy {
        CollectWebsitePresenter(this, this, SchedulerProvider.getInstatnce()!!)
    }

    private val listAdapter: CollectWebsiteListAdapter by lazy {
        CollectWebsiteListAdapter(R.layout.item_collect_website, datas,activity)
    }

    override fun getCollectWebSiteSuccess(list: MutableList<SearchTag>) {
        datas.clear()
        datas.addAll(list)
        sRefresh.isRefreshing = false
        listAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.recyclerview_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRefresh()
        initListAdapter()
        presenter.getCollectWebsite()
    }

    private fun initRefresh() {
        sRefresh.setOnRefreshListener({
            presenter.getCollectWebsite()
        })
    }

    private fun initListAdapter() {
        rvList.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
        }
        listAdapter.setOnItemClickListener { adapter, view, position ->
            WebViewActivity.lunch(activity, datas[position].link!!, datas[position].name!!)
        }

        listAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.ivLike -> {
                    selectId = datas[position].id
                    // 判断是要收藏还是取消收藏
                    if (unCollectIds.contains(selectId)) {
                        presenter.collectWebsite(datas[position].name!!, datas[position].link!!)
                        removeCollectStatus()
                    } else {
                        addCollectStatus()
                        presenter.unCollectWebsite(datas[position].id)
                    }
                }

            }
        }
    }

    override fun collectWebsiteFail() {
        super.collectWebsiteFail()
        Toast.makeText(activity, "收藏失败", Toast.LENGTH_SHORT).show()
        removeCollectStatus()
    }

    override fun unCollectWebsiteFail() {
        super.unCollectWebsiteFail()
        Toast.makeText(activity, "取消收藏失败", Toast.LENGTH_SHORT).show()
        addCollectStatus()
    }

    private fun addCollectStatus() {
        unCollectIds.add(selectId)
        listAdapter.setUnCollectIds(unCollectIds)
        listAdapter.notifyDataSetChanged()
    }

    private fun removeCollectStatus() {
        unCollectIds.remove(selectId)
        listAdapter.setUnCollectIds(unCollectIds)
        listAdapter.notifyDataSetChanged()
    }

    override fun changeThemeRefresh() {
        super.changeThemeRefresh()
        var color: Int = WanAndroidApplication.instance!!.getThemeColor(activity, WanAndroidApplication.instance!!.getTheme(activity)!!)
        sRefresh.setColorSchemeColors(resources.getColor(color), resources.getColor(color), resources.getColor(color))
    }


}