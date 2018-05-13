package com.mg.axechen.wanandroid.block.collect.article

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectFragment
import com.mg.axechen.wanandroid.block.details.DetailActivity
import com.mg.axechen.wanandroid.block.details.WebViewActivity
import com.mg.axechen.wanandroid.block.main.home.CustomLoadMoreView
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.ProjectListBean
import kotlinx.android.synthetic.main.recyclerview_layout.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/4/19.
 */
class CollectArticleFragment : BaseCollectFragment(), CollectArticleContract.View {

    private var datas = mutableListOf<HomeData>()

    /**
     * 未收藏的集合
     */
    private var unCollectIds = mutableListOf<Int>()

    private var selectId: Int = 0

    private val presenter: CollectArticlePresenter by lazy {
        CollectArticlePresenter(SchedulerProvider.getInstatnce()!!, this, this)
    }

    private val listAdapter: CollectListAdapter by lazy {
        CollectListAdapter(R.layout.item_collect_article, datas, activity)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerLoginStatusReceiver()
        initRecyclerList()
        initRefresh()
        presenter.getCollectArticleList(true)
    }

    override fun getCollectArticleListFail(msg: String, isRefresh: Boolean) {
        listAdapter.loadMoreComplete()
        sRefresh.isRefreshing = false
    }

    override fun loadAllCollectArticle(bean: ProjectListBean, isRefresh: Boolean) {
        if (isRefresh) {
            unCollectIds.clear()
            datas.clear()
        }
        datas.addAll(bean.datas!!)
        sRefresh.isRefreshing = false
        listAdapter.loadMoreComplete()
        listAdapter.setEnableLoadMore(false)
        listAdapter.notifyDataSetChanged()
    }

    override fun getCollectArticleListSuccess(bean: ProjectListBean, isRefresh: Boolean) {
        if (isRefresh) {
            unCollectIds.clear()
            datas.clear()
        }
        sRefresh.isRefreshing = false
        datas.addAll(bean.datas!!)
        listAdapter.loadMoreComplete()
        listAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerList() {
        rvList.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
        }
        listAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            DetailActivity.lunch(activity, datas[position], !unCollectIds.contains(datas[position].originId), datas[position].originId)
        }
        listAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.flLike -> {
                    selectId = datas[position].originId
                    // 判断是要收藏还是取消收藏
                    if (unCollectIds.contains(selectId)) {
                        presenter.collectInArticle(datas[position].originId)
                        removeCollectStatus()
                    } else {
                        presenter.unCollectArticle(datas[position].originId)
                        addCollectStatus()
                    }
                }
            }
        }
        listAdapter.setPreLoadNumber(0)
        listAdapter.setEnableLoadMore(true)
        listAdapter.setLoadMoreView(CustomLoadMoreView())
        listAdapter.setOnLoadMoreListener({
            presenter.getCollectArticleList(false)
        }, rvList)
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

    private fun initRefresh() {
        sRefresh.setOnRefreshListener({
            presenter.getCollectArticleList(true)
        })
    }

    override fun collectInArticleFail() {
        super.collectInArticleFail()
        addCollectStatus()
    }


    override fun unCollectArticleFail() {
        super.unCollectArticleFail()
        removeCollectStatus()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.recyclerview_layout, container, false)
    }

    override fun changeThemeRefresh() {
        super.changeThemeRefresh()
        var color: Int = WanAndroidApplication.instance!!.getThemeColor(activity, WanAndroidApplication.instance!!.getTheme(activity)!!)
        sRefresh.setColorSchemeColors(resources.getColor(color), resources.getColor(color), resources.getColor(color))
    }

    override fun collectStatusChange(id: Int, isCollect: Boolean) {
        super.collectStatusChange(id, isCollect)
        datas
                .filter { it -> (it.originId == id && !isCollect) }
                .forEach { unCollectIds.add(id) }
        listAdapter.setUnCollectIds(unCollectIds)
        listAdapter.notifyDataSetChanged()
    }
}