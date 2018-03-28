package com.mg.axechen.wanandroid.block.main.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.HomeListBean
import com.mg.axechen.wanandroid.javabean.HomeViewType
import kotlinx.android.synthetic.main.fragment_home.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/3/23.
 * 主页文章列表
 */
class HomeFragment : Fragment(), HomeContract.View {

    /**
     * HomeItemList
     */
    private val datas = mutableListOf<HomeViewType>()

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(datas)
    }

    private val inflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    private val presenter: HomeContract.Presenter by lazy {
        HomePresenter(SchedulerProvider.getInstatnce()!!, this)
    }

    override fun getHomeListFail(msg: String) {
        homeAdapter.loadMoreComplete()
    }

    override fun getHomeListSuccess(homeListBean: HomeListBean, isRefresh: Boolean) {
        var homedatas: List<HomeData> = homeListBean.datas
        if (isRefresh) {
            datas.clear()
        }
        sRefresh.isRefreshing = false
        for (it in homedatas) {
            datas.add(HomeViewType(HomeViewType.VIEW_TYPE_ITEM, it))
        }
        homeAdapter.loadMoreComplete()
        homeAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    private fun initRefresh() {
        sRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            presenter.getHomeList(true)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.getHomeList(true)
        initRefresh()
        rvList.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = homeAdapter
        }
        homeAdapter.setOnItemClickListener { adapter, view, position ->
            Toast.makeText(activity, "点击Item", Toast.LENGTH_SHORT).show()
        }

        homeAdapter.setPreLoadNumber(0)
        homeAdapter.setEnableLoadMore(true)
        homeAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.ivMore) {
                var builder: AlertDialog.Builder = AlertDialog.Builder(activity).apply {
                    setTitle("更多操作")
                    setMessage("呵呵")
                    setNegativeButton("确认", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                }
                builder.create().show()
            } else if (view.id == R.id.ivLike) {

            }
        }

        homeAdapter.setLoadMoreView(CustomLoadMoreView())
        homeAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            presenter.getHomeList(false)
        })
    }


}