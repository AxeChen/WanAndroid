package com.mg.axechen.wanandroid.block.collect.article

import com.mg.axechen.wanandroid.javabean.ProjectListBean
import com.mg.axechen.wanandroid.network.NetWorkManager
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/4/20.
 */
class CollectArticleModel : CollectArticleContract.Model {
    override fun getCollectArticleList(page: Int): Observable<Response<ProjectListBean>> {
        return NetWorkManager.getInstance().getRequest()!!.getCollectArticleList(page)
    }
}