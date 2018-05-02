package com.mg.axechen.wanandroid.block.collect.website

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.SearchTag
import kotlinx.android.synthetic.main.fragment_home.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/4/19.
 * 收藏列表界面
 */
class CollectWebsiteFragment : Fragment(), CollectWebsiteContract.View {

    private var datas = mutableListOf<SearchTag>()

    override fun getCollectWebSiteFail(msg: String) {
    }

    private val presenter: CollectWebsiteContract.Presenter by lazy {
        CollectWebsitePresenter(this, SchedulerProvider.getInstatnce()!!)
    }

    private val listAdapter: CollectWebsiteListAdapter by lazy {
        CollectWebsiteListAdapter(R.layout.item_collect_website, datas)
    }

    override fun getCollectWebSiteSuccess(list: MutableList<SearchTag>) {
        datas.clear()
        datas.addAll(list)
        listAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.recyclerview_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListAdapter()
        presenter.getCollectWebsite()
    }

    private fun initListAdapter() {
        rvList.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
        }
    }

}