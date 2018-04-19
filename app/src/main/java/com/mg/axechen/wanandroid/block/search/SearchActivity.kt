package com.mg.axechen.wanandroid.block.search

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.block.details.WebViewActivity
import com.mg.axechen.wanandroid.block.main.home.CustomLoadMoreView
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.ProjectListBean
import com.mg.axechen.wanandroid.javabean.SearchTag
import com.mg.axechen.wanandroid.javabean.SearchViewType
import kotlinx.android.synthetic.main.activity_search.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/4/10.
 * 搜索
 */
class SearchActivity : BaseActivity(), SearchContract.View {

    private var searTags = mutableListOf<SearchTag>()

    private var items = mutableListOf<SearchViewType>()

    private val listAdapter: SearchListAdapter by lazy {
        SearchListAdapter(items, this)
    }

    private val presenter: SearchContract.Presenter by lazy {
        SearchPresenter(this, SchedulerProvider.getInstatnce()!!)
    }

    companion object {
        fun lunch(context: Context) {
            context.startActivity(Intent(context, SearchActivity::class.java))
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
        initAdapter()
        initEditTextInputListener()
        initClear()
        getSearchTag()

    }

    private fun initClear() {
        ivClear.setOnClickListener({
            edSearch.setText("")
            ivClear.visibility = View.GONE
            showRecommend()
        })
    }

    private fun showRecommend() {
        items.clear()
        if (searTags.size > 0) {
            items.add(SearchViewType(SearchViewType.VIEW_TYPE_SELECTION, "热门搜索"))
            items.add(SearchViewType(SearchViewType.VIEW_TYPE_RECOMMEND, searTags))
        }
        // 测试数据
        items.add(SearchViewType(SearchViewType.VIEW_TYPE_SELECTION, "历史搜索"))
        items.add(SearchViewType(SearchViewType.VIEW_TYPE_HISTORY, "安卓开发"))
        items.add(SearchViewType(SearchViewType.VIEW_TYPE_HISTORY, "PHP开发"))
        items.add(SearchViewType(SearchViewType.VIEW_TYPE_HISTORY, "JAVA开发"))
        items.add(SearchViewType(SearchViewType.VIEW_TYPE_HISTORY, "IOS开发"))
        listAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getSearchTag() {
        presenter.getSearchTag()
    }

    private fun initActionBar() {
        toolbar.run {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        }
    }

    private fun initEditTextInputListener() {
        edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var str = s.toString()
                if (str.isNotEmpty()) {
                    ivClear.visibility = View.VISIBLE
                } else {
                    ivClear.visibility = View.GONE
                }
            }
        })

        edSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var str = edSearch.text.toString()
                if (str.isNotEmpty()) {
                    // 调用接口，开始搜索
                    presenter.search(str, true)
                } else {
                    Toast.makeText(this@SearchActivity, "搜索文字为空", Toast.LENGTH_SHORT).show()
                }
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun getSearchTagSuccess(lists: MutableList<SearchTag>) {
        if (lists.size > 0) {
            searTags.clear()
            searTags.addAll(lists)
        }
        showRecommend()
    }

    private fun initAdapter() {
        rvList.run {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = listAdapter
        }
        listAdapter.setEnableLoadMore(true)
        listAdapter.setLoadMoreView(CustomLoadMoreView())

        listAdapter.setOnItemClickListener { adapter, view, position ->
            if (items[position].itemType == SearchViewType.VIEW_TYPE_RESULT) {
                var homeData = items[position].item as HomeData
                WebViewActivity.lunch(this@SearchActivity, homeData.link!!, homeData.title!!)
            }
        }

        listAdapter.recommendClickListener = object : SearchListAdapter.RecommendClickListener {
            override fun recommendClick(tag: SearchTag) {
                presenter.search(tag.name!!, true)
                edSearch.setText(tag.name)
            }

        }

        listAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.ivMore) {
                var builder: AlertDialog.Builder = AlertDialog.Builder(this@SearchActivity).apply {
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

        listAdapter.setOnLoadMoreListener({
            presenter.search(edSearch.text.toString(), false)
        }, rvList)


    }

    override fun getSearchResultSuccess(bean: ProjectListBean, isRefresh: Boolean) {
        if (isRefresh) {
            items.clear()
        }
        for (result in bean.datas!!) {
            items.add(SearchViewType(SearchViewType.VIEW_TYPE_RESULT, result))
        }
        listAdapter.loadMoreComplete()
        listAdapter.notifyDataSetChanged()
    }

    override fun loadAllResult(bean: ProjectListBean, isRefresh: Boolean) {
        if (isRefresh) {
            items.clear()
        }
        listAdapter.setEnableLoadMore(false)
        for (result in bean.datas!!) {
            items.add(SearchViewType(SearchViewType.VIEW_TYPE_RESULT, result))
        }
        listAdapter.loadMoreComplete()
        listAdapter.notifyDataSetChanged()
    }


    override fun getSearchResultFail(msg: String) {
        listAdapter.setEnableLoadMore(false)
        listAdapter.loadMoreComplete()
    }

    override fun getSearchTagFail(msg: String) {
    }

}