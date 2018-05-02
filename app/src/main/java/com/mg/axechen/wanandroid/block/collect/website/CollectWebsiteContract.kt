package com.mg.axechen.wanandroid.block.collect.website

import com.mg.axechen.wanandroid.base.BasePresenter
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.SearchTag
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/4/20.
 */
class CollectWebsiteContract {
    interface View {
        fun getCollectWebSiteSuccess(list: MutableList<SearchTag>)
        fun getCollectWebSiteFail(msg: String)
    }

    interface Presenter : BasePresenter {
        fun getCollectWebsite()
    }

    interface Model {
        fun getCollectWebsite(): Observable<Response<MutableList<SearchTag>>>
    }
}