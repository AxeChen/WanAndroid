package com.mg.axechen.wanandroid.block.search

import com.mg.axechen.wanandroid.javabean.ProjectListBean
import com.mg.axechen.wanandroid.javabean.SearchTag
import com.mg.axechen.wanandroid.network.NetWorkManager
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/4/10.
 */
class SearchModel : SearchContract.Model {

    override fun search(page: Int, text: String): Observable<Response<ProjectListBean>> {
        return NetWorkManager.getInstance().getRequest()!!.search(page, text)
    }

    override fun getSearchTag(): Observable<Response<MutableList<SearchTag>>> {
        return NetWorkManager.getInstance().getRequest()!!.getRecommendSearchTag()
    }

}