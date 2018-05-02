package com.mg.axechen.wanandroid.block.collect.article

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.block.collect.CollectListAdapter
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.ProjectListBean
import kotlinx.android.synthetic.main.recyclerview_layout.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/4/19.
 */
class CollectArticleFragment : Fragment(), CollectArticleContract.View {

    private var datas = mutableListOf<HomeData>()

    private val presenter: CollectArticleContract.Presenter by lazy {
        CollectArticlePresenter(this, SchedulerProvider.getInstatnce()!!)
    }

    private val listAdapter: CollectListAdapter by lazy {
        CollectListAdapter(R.layout.item_home, datas)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerList()
        presenter.getCollectArticleList()
    }

    override fun getCollectArticleListFail(msg: String) {
    }

    override fun getCollectArticleListSuccess(bean: ProjectListBean) {
        datas.addAll(bean.datas!!)
        listAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerList() {
        rvList.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.recyclerview_layout, container, false)
    }

}