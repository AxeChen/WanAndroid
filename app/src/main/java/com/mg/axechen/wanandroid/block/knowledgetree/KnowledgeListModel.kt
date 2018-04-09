package com.mg.axechen.wanandroid.block.knowledgetree

import com.mg.axechen.wanandroid.javabean.ProjectListBean
import com.mg.axechen.wanandroid.network.NetWorkManager
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/4/8.
 */
class KnowledgeListModel : KnowledgeListContract.Model {
    override fun getKnowledgeList(page: Int, cid: Int): Observable<Response<ProjectListBean>> {
        return NetWorkManager.getInstance().getRequest()!!.getKnowledgeList(page, cid)
    }
}