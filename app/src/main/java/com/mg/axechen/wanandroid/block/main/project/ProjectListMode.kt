package com.mg.axechen.wanandroid.block.main.project

import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.ProjectListBean
import com.mg.axechen.wanandroid.javabean.TreeBean
import com.mg.axechen.wanandroid.network.NetWorkManager
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/4/2.
 */
class ProjectListMode : ProjectListContract.Model {

    override fun getProjectListByCid(page: Int, cid: Int): Observable<Response<ProjectListBean>> {
        return NetWorkManager.getInstance().getRequest()!!.getProjectListByCid(page, cid)
    }

    override fun getProjectTree(): Observable<Response<List<TreeBean>>> {
        return NetWorkManager.getInstance().getRequest()!!.getProjectTree()
    }

}