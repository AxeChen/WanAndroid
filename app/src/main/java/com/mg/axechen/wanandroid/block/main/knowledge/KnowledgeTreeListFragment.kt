package com.mg.axechen.wanandroid.block.main.knowledge

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.KnowledgeTreeBean
import kotlinx.android.synthetic.main.fragment_knowledge_tree.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/3/31.
 * 知识体系列表
 */
class KnowledgeTreeListFragment : Fragment(), KnowledgeTreeListContract.View {

    private var datas: List<KnowledgeTreeBean> = mutableListOf<KnowledgeTreeBean>()

    private val presenter: KnowledgeTreeListContract.Presenter by lazy {
        KnowledgeTreeListPresenter(SchedulerProvider.getInstatnce()!!, this)
    }

    private val listAdapter: KnowledgeTreeListAdapter by lazy {
        KnowledgeTreeListAdapter(R.layout.item_knowledge_tree_list, datas)
    }

    override fun getTreeFail() {
    }

    override fun getTreeSuccess(data: List<KnowledgeTreeBean>) {
        datas = data as MutableList<KnowledgeTreeBean>
        setRecycler()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater!!.inflate(R.layout.fragment_knowledge_tree, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.getKnowledgeTree()
    }

    private fun setRecycler() {
        rvList.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
        }

        listAdapter.setOnItemClickListener { adapter, view, position ->
            Toast.makeText(activity,"点击知识体系的一项",Toast.LENGTH_SHORT).show()
        }
    }

}