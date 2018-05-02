package com.mg.axechen.wanandroid.block.collect.article

import com.mg.axechen.wanandroid.base.BasePresenter
import com.mg.axechen.wanandroid.javabean.ProjectListBean
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/4/20.
 */
class CollectArticleContract {
    interface View {
        fun getCollectArticleListSuccess(bean: ProjectListBean)
        fun getCollectArticleListFail(msg: String)
    }

    interface Presenter : BasePresenter {
        fun getCollectArticleList()
    }

    interface Model {
        fun getCollectArticleList(page: Int): Observable<Response<ProjectListBean>>
    }
}