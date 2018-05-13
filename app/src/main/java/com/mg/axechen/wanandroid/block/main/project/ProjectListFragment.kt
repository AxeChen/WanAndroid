package com.mg.axechen.wanandroid.block.main.project

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import kotlinx.android.synthetic.main.fragment_project_list.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/4/2.
 *
 * 项目列表
 */
class ProjectListFragment : BaseCollectFragment(), ProjectListContract.View {

    private var kinds = mutableListOf<TreeBean>()

    private var projects = mutableListOf<HomeData>()

    private var selectProject: TreeBean? = null

    private var selectId: Int = 0

    private val listAdapter: ProjectListAdapter by lazy {
        ProjectListAdapter(R.layout.item_project_list, projects)
    }

    private val kindsAdapter: KindsAdapter by lazy {
        KindsAdapter(R.layout.item_spinner_kinds, kinds)
    }


    private val presenter: ProjectListPresenter by lazy {
        ProjectListPresenter(this, SchedulerProvider.getInstatnce()!!, this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_project_list, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerLoginStatusReceiver()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        drawerLayout.setScrimColor(Color.TRANSPARENT)
        initProjectsAdapter()
        initKindsClick()
        initRefresh()
        presenter.getProjectTree()
    }

    override fun getProjectTreeSuccess(bean: List<TreeBean>) {
        kinds = bean as MutableList<TreeBean>
        selectProject = kinds[0]
        initKindsAdapter()
        kindsAdapter.setSelect(selectProject!!)
        tvKind.text = selectProject!!.name
        presenter.getProjectTreeList(selectProject!!.id, true)
        kindsAdapter.setOnItemClickListener { adapter, view, position ->
            // 关闭侧滑。请求数据
            drawerLayout.closeDrawer(flRight)
            selectProject = kinds[position]
            listAdapter?.loadMoreEnd(true)
            kindsAdapter.setSelect(selectProject!!)
            tvKind.text = selectProject!!.name
            presenter.getProjectTreeList(selectProject!!.id, true)
            kindsAdapter.notifyDataSetChanged()
        }
    }

    private fun initKindsClick() {
        tvKind.setOnClickListener { view ->
            changeRightPage()
        }
    }

    private fun changeRightPage() {
        if (drawerLayout.isDrawerOpen(flRight)) {
            drawerLayout.closeDrawer(flRight)
        } else {
            drawerLayout.openDrawer(flRight)
        }
    }

    private fun initKindsAdapter() {
        rvKinds.layoutManager = LinearLayoutManager(activity)
        rvKinds.adapter = kindsAdapter
    }

    private fun initProjectsAdapter() {
        rvList.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter

        }
        listAdapter.setPreLoadNumber(0)
        listAdapter.setEnableLoadMore(true)
        listAdapter.setLoadMoreView(CustomLoadMoreView())
        listAdapter.setOnLoadMoreListener({
            presenter.getProjectTreeList(selectProject!!.id, false)
        }, rvList)
        listAdapter.setOnItemClickListener { adapter, view, position ->
            val homeData: HomeData = adapter.data.get(position) as HomeData
            DetailActivity.lunch(activity, homeData, homeData.collect, homeData.id)
        }
        listAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.flLike) {
                if (SharedPreferencesUtils.getInt(SharePreferencesContants.USER_ID) == 0) {
                    Toast.makeText(activity, getString(R.string.collect_fail_pls_login), Toast.LENGTH_SHORT).show()
                } else {
                    var homdata: HomeData = projects[position]
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

    override fun getProjectTreeFail(msg: String) {

    }

    private fun initRefresh() {
        sRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            presenter.getProjectTreeList(selectProject!!.id, true)
        })
    }

    override fun getProjectListByCidSuccess(bean: ProjectListBean, isRefresh: Boolean) {
        sRefresh.isRefreshing = false
        listAdapter.loadMoreComplete()
        if (isRefresh) {
            projects.clear()
            projects.addAll(bean.datas!!)
            listAdapter.notifyDataSetChanged()

            // 计算页数，是否开启加载下一页
            if (bean.size >= bean.total) {
                listAdapter.setEnableLoadMore(false)
            }
        } else {
            if (bean.datas!!.size != 0) {
                listAdapter.loadMoreEnd(false)
                projects.addAll(bean.datas!!)
                listAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun getProjectListByCidFail(msg: String) {
        listAdapter?.loadMoreComplete()
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun loginSuccess() {
        super.loginSuccess()
        if (selectProject != null) {
            presenter.getProjectTreeList(selectProject!!.id, true)
        }
    }

    override fun collectStatusChange(id: Int, isCollect: Boolean) {
        super.collectStatusChange(id, isCollect)
        projects
                .filter { it -> it.id == id }
                .forEach { it -> it.collect = isCollect }
        listAdapter.notifyDataSetChanged()
    }

    override fun changeThemeRefresh() {
        super.changeThemeRefresh()
        listAdapter?.notifyDataSetChanged()
        var color: Int = WanAndroidApplication.instance!!.getThemeColor(activity, WanAndroidApplication.instance!!.getTheme(activity)!!)
        sRefresh.setColorSchemeColors(resources.getColor(color), resources.getColor(color), resources.getColor(color))
    }
}