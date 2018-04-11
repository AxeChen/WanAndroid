package com.mg.axechen.wanandroid.block.search

import com.mg.axechen.wanandroid.base.BasePresenter
import com.mg.axechen.wanandroid.javabean.ProjectListBean
import com.mg.axechen.wanandroid.javabean.SearchTag
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/4/10.
 */
class SearchContract {

    interface View {
        fun getSearchTagSuccess(lists: MutableList<SearchTag>)
        fun getSearchTagFail(msg: String)
        fun getSearchResultSuccess(bean: ProjectListBean,isRefresh: Boolean)
        fun getSearchResultFail(msg: String)
        fun loadAllResult(bean: ProjectListBean, isRefresh: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getSearchTag()
        fun search(text: String,isRefresh: Boolean)
    }

    interface Model {
        fun getSearchTag(): Observable<Response<MutableList<SearchTag>>>
        fun search(page: Int, text: String): Observable<Response<ProjectListBean>>
    }

}