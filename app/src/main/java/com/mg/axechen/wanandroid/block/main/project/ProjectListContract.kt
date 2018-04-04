package com.mg.axechen.wanandroid.block.main.project

import com.mg.axechen.wanandroid.base.BasePresenter
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.ProjectListBean
import com.mg.axechen.wanandroid.javabean.TreeBean
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/4/2.
 */
interface ProjectListContract {

    interface View {
        fun getProjectTreeSuccess(bean: List<TreeBean>)
        fun getProjectTreeFail(msg: String)
        fun getProjectListByCidSuccess(bean: ProjectListBean,isRefresh: Boolean)
        fun getProjectListByCidFail(msg: String)
    }

    interface Presenter : BasePresenter {
        fun getProjectTree()
        fun getProjectTreeList(cid: Int, isRefresh: Boolean)
    }

    interface Model {
        // 获取项目分类
        fun getProjectTree(): Observable<Response<List<TreeBean>>>

        //根据cid获取项目列表
        fun getProjectListByCid(page: Int, cid: Int): Observable<Response<ProjectListBean>>
    }
}