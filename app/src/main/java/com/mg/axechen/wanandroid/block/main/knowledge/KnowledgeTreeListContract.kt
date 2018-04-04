package com.mg.axechen.wanandroid.block.main.knowledge

import com.mg.axechen.wanandroid.base.BasePresenter
import com.mg.axechen.wanandroid.javabean.TreeBean
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/3/31.
 */
interface KnowledgeTreeListContract {

    interface View {
        fun getTreeSuccess(data:List<TreeBean>)
        fun getTreeFail()
    }

    interface Presenter : BasePresenter {
        fun getKnowledgeTree()
    }

    interface Mode {
        fun getKnowledgeTree(): Observable<Response<List<TreeBean>>>
    }
}