package com.mg.axechen.wanandroid.block.collect.article

import com.mg.axechen.wanandroid.javabean.ProjectListBean
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/4/20.
 */
class CollectArticleContract {
    interface View {
        fun getCollectArticleListSuccess(bean: ProjectListBean,isRefresh: Boolean)
        fun getCollectArticleListFail(msg: String,isRefresh: Boolean)
        fun loadAllCollectArticle(bean: ProjectListBean,isRefresh: Boolean)
    }

    interface Presenter {
        fun getCollectArticleList(isRefresh:Boolean)
    }

    interface Model {
        fun getCollectArticleList(page: Int): Observable<Response<ProjectListBean>>
    }
}