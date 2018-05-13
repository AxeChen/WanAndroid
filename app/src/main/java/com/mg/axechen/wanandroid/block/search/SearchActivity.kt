package com.mg.axechen.wanandroid.block.search

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.block.details.WebViewActivity
import com.mg.axechen.wanandroid.block.main.home.CustomLoadMoreView
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.ProjectListBean
import com.mg.axechen.wanandroid.javabean.SearchTag
import com.mg.axechen.wanandroid.javabean.SearchViewType
import com.mg.axechen.wanandroid.utils.KeyBoardUtils
import com.mg.axechen.wanandroid.utils.SharePreferencesContants
import com.mg.axechen.wanandroid.utils.SharedPreferencesUtils
import kotlinx.android.synthetic.main.activity_search.*
import network.schedules.SchedulerProvider


/**
 * Created by AxeChen on 2018/4/10.
 * 搜索
 */
class SearchActivity : BaseActivity(), SearchContract.View {

    private var searTags = mutableListOf<SearchTag>()

    private var historyTags = mutableListOf<String>()

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
        loadHistory()
        getSearchTag()

    }

    private fun initClear() {
        ivClear.setOnClickListener({
            edSearch.setText("")
            ivClear.visibility = View.GONE
            showRecommend()
        })
    }

    private fun loadHistory() {
        var jsonString = SharedPreferencesUtils.getString(SharePreferencesContants.HISTORY_SEARCH)
        if (!TextUtils.isEmpty(jsonString)) {
            historyTags = Gson().fromJson(jsonString, object : TypeToken<MutableList<String>>() {}.type)
        }
    }

    private fun addHistory(tag: String) {
        if (historyTags.size == 10) {
            historyTags.removeAt(historyTags.size - 1)
        }
        if (historyTags.contains(tag)) {
            return
        }
        historyTags.add(tag)
        var jsonString = Gson().toJson(historyTags, object : TypeToken<MutableList<String>>() {}.type)
        SharedPreferencesUtils.putString(SharePreferencesContants.HISTORY_SEARCH, jsonString)
    }

    private fun showRecommend() {
        items.clear()
        if (searTags.size > 0) {
            items.add(SearchViewType(SearchViewType.VIEW_TYPE_SELECTION, "热门搜索"))
            items.add(SearchViewType(SearchViewType.VIEW_TYPE_RECOMMEND, searTags))
        }

        if (historyTags.size > 0) {
            items.add(SearchViewType(SearchViewType.VIEW_TYPE_HISTORY_SELECTION, "历史记录"))
        }
        // 展示历史搜索
        for (it in historyTags) {
            items.add(SearchViewType(SearchViewType.VIEW_TYPE_HISTORY, it))
        }
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
                    addHistory(str)
                    // 调用接口，开始搜索
                    KeyBoardUtils.closeKeyboard(this@SearchActivity)
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
            } else if (items[position].itemType == SearchViewType.VIEW_TYPE_HISTORY) {
                var str = items[position].item as String
                presenter.search(str, true)
                edSearch.setText(str)
                KeyBoardUtils.closeKeyboard(this@SearchActivity)
            }
        }

        listAdapter.recommendClickListener = object : SearchListAdapter.RecommendClickListener {
            override fun recommendClick(tag: SearchTag) {
                presenter.search(tag.name!!, true)
                edSearch.setText(tag.name)
                KeyBoardUtils.closeKeyboard(this@SearchActivity)
            }

        }

        listAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.tvClear) {
                SharedPreferencesUtils.putString(SharePreferencesContants.HISTORY_SEARCH, "")
                historyTags.clear()
                KeyBoardUtils.closeKeyboard(this@SearchActivity)
                showRecommend()
                Toast.makeText(this@SearchActivity, getString(R.string.clear_history_success), Toast.LENGTH_SHORT).show()
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