package com.mg.axechen.wanandroid.block.collect.article

import com.mg.axechen.wanandroid.base.BasePresenter
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectImplView
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectPresenter
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectView
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

    interface Presenter {
        fun getCollectArticleList()
    }

    interface Model {
        fun getCollectArticleList(page: Int): Observable<Response<ProjectListBean>>
    }
}