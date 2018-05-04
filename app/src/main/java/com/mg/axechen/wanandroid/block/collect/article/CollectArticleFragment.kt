package com.mg.axechen.wanandroid.block.collect.article

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectFragment
import com.mg.axechen.wanandroid.block.details.WebViewActivity
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.ProjectListBean
import kotlinx.android.synthetic.main.recyclerview_layout.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/4/19.
 */
class CollectArticleFragment : BaseCollectFragment(), CollectArticleContract.View {

    private var datas = mutableListOf<HomeData>()

    private val presenter: CollectArticlePresenter by lazy {
        CollectArticlePresenter(SchedulerProvider.getInstatnce()!!, this, this)
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
        listAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            WebViewActivity.lunch(activity, datas[position].link!!, datas[position].title!!)
        }
        listAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.ivLike -> presenter.collectInArticle(datas[position].id)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.recyclerview_layout, container, false)
    }

}