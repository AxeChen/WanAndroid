package com.mg.axechen.wanandroid.block.knowledgetree

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
import com.mg.axechen.wanandroid.block.details.DetailActivity
import com.mg.axechen.wanandroid.block.details.WebViewActivity
import com.mg.axechen.wanandroid.block.main.home.CustomLoadMoreView
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.ProjectListBean
import com.mg.axechen.wanandroid.javabean.TreeBean
import com.mg.axechen.wanandroid.utils.SharePreferencesContants
import com.mg.axechen.wanandroid.utils.SharedPreferencesUtils
import kotlinx.android.synthetic.main.fragment_home.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/4/8.
 *
 */
class KnowledgeListFragment : BaseCollectFragment(), KnowledgeListContract.View {

    companion object {
        var INTENT_TAG_JAVA_BEAN_TREE_BEAN = "treeBean"
    }

    private var datas = mutableListOf<HomeData>()

    var treeBean: TreeBean? = null

    var init: Boolean = false

    private var selectId: Int = 0

    private val presenter: KnowledgeListPresenter by lazy {
        KnowledgeListPresenter(this, this, SchedulerProvider.getInstatnce()!!)
    }

    private val listAdapter: KnowledgeListAdapter by lazy {
        KnowledgeListAdapter(R.layout.item_knowledge_list, datas)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerLoginStatusReceiver()
        initAdapter()
        initRefresh()
        initData(true)
    }

    private fun initRefresh() {
        sRefresh.setOnRefreshListener {
            getArticleList(true)
        }
    }

    private fun initAdapter() {
        rvList.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
        }
        listAdapter.setPreLoadNumber(0)
        listAdapter.setEnableLoadMore(true)
        listAdapter.setLoadMoreView(CustomLoadMoreView())
        listAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            getArticleList(false)
        }, rvList)
        listAdapter.setOnItemClickListener({ adapter, view, position ->
            val homeData: HomeData = datas.get(position)
            DetailActivity.lunch(activity, homeData, homeData.collect, homeData.id)
        })
        listAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.flLike) {
                if (SharedPreferencesUtils.getInt(SharePreferencesContants.USER_ID) == 0) {
                    Toast.makeText(activity, getString(R.string.collect_fail_pls_login), Toast.LENGTH_SHORT).show()
                } else {
                    var homdata: HomeData = datas[position]
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

    }

    private fun addCollectStatus(homeData: HomeData) {
        homeData.collect = false
        listAdapter.notifyDataSetChanged()
    }

    private fun removeCollectStatus(homeData: HomeData) {
        homeData.collect = true
        listAdapter.notifyDataSetChanged()
    }

    private fun getBundleData() {
        var bundle = arguments
        treeBean = bundle.getParcelable(INTENT_TAG_JAVA_BEAN_TREE_BEAN)
    }

    private fun initData(isRefresh: Boolean) {
        if (userVisibleHint && !init) {
            getBundleData()
            if (treeBean == null) {
                return
            }
            sRefresh?.isRefreshing = true
            getArticleList(isRefresh)
            init = true
        }
    }

    private fun getArticleList(isRefresh: Boolean) {
        listAdapter.setEnableLoadMore(true)
        presenter.getKnowledgeList(treeBean!!.id, isRefresh)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        initData(true)
    }

    override fun loadAllArticles(bean: ProjectListBean, isRefresh: Boolean) {
        if (isRefresh) {
            datas.clear()
        }
        this.datas.addAll(bean.datas!!)
        sRefresh.isRefreshing = false
        listAdapter.loadMoreComplete()
        listAdapter.notifyDataSetChanged()
        listAdapter.setEnableLoadMore(false)
    }

    override fun getKnowledgeSuccess(bean: ProjectListBean, isRefresh: Boolean) {
        if (isRefresh) {
            datas.clear()
        }
        this.datas.addAll(bean.datas!!)
        sRefresh.isRefreshing = false
        listAdapter.loadMoreComplete()
        listAdapter.notifyDataSetChanged()
    }

    override fun getKnowledgeFail(msg: String, isRefresh: Boolean) {
        listAdapter.setEnableLoadMore(false)
        sRefresh.isRefreshing = false
        listAdapter.loadMoreComplete()
    }

    override fun changeThemeRefresh() {
        super.changeThemeRefresh()
        var color: Int = WanAndroidApplication.instance!!.getThemeColor(activity, WanAndroidApplication.instance!!.getTheme(activity)!!)
        sRefresh.setColorSchemeColors(resources.getColor(color), resources.getColor(color), resources.getColor(color))
    }

    override fun collectStatusChange(id: Int, isCollect: Boolean) {
        super.collectStatusChange(id, isCollect)
        datas
                .filter { it -> it.id == id }
                .forEach { it -> it.collect = isCollect }
        listAdapter.notifyDataSetChanged()
    }
}