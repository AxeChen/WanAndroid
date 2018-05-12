package com.mg.axechen.wanandroid.block.collect.article

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectFragment
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
            WebViewActivity.lunch(activity, datas[position].link!!, datas[position].title!!)
        }
        listAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.ivLike -> {
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
        listAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
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

    override fun collectInArticleSuccess() {
        super.collectInArticleSuccess()
        Toast.makeText(activity, "收藏成功", Toast.LENGTH_SHORT).show()
    }

    override fun collectInArticleFail() {
        super.collectInArticleFail()
        Toast.makeText(activity, "收藏失败", Toast.LENGTH_SHORT).show()
        addCollectStatus()
    }

    override fun unCollectArticleSuccess() {
        super.unCollectArticleSuccess()
        Toast.makeText(activity, "取消收藏成功", Toast.LENGTH_SHORT).show()
    }

    override fun unCollectArticleFail() {
        super.unCollectArticleFail()
        Toast.makeText(activity, "取消收藏失败", Toast.LENGTH_SHORT).show()
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

}