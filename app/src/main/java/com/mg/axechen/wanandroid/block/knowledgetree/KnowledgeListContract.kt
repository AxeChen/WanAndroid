package com.mg.axechen.wanandroid.block.knowledgetree

import com.mg.axechen.wanandroid.base.BasePresenter
import com.mg.axechen.wanandroid.javabean.ProjectListBean
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/4/8.
 */
class KnowledgeListContract {
    interface View {
        fun getKnowledgeSuccess(bean: ProjectListBean,isRefresh:Boolean)
        fun getKnowledgeFail(msg: String,isRefresh:Boolean)
        fun loadAllArticles(bean: ProjectListBean,isRefresh:Boolean)
    }

    interface Presenter : BasePresenter {
        fun getKnowledgeList(cid: Int,isRefresh:Boolean)
    }

    interface Model {
        fun getKnowledgeList(page: Int, cid: Int): Observable<Response<ProjectListBean>>
    }
}