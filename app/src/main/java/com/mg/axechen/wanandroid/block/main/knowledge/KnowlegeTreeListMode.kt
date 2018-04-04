package com.mg.axechen.wanandroid.block.main.knowledge

import com.mg.axechen.wanandroid.javabean.TreeBean
import com.mg.axechen.wanandroid.network.NetWorkManager
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/3/31.
 */
class KnowlegeTreeListMode:KnowledgeTreeListContract.Mode{

    override fun getKnowledgeTree(): Observable<Response<List<TreeBean>>> {
        return NetWorkManager.getInstance().getRequest()!!.getKnowledgeTreeList()
    }

}