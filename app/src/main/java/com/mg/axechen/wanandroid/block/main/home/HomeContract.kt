package com.mg.axechen.wanandroid.block.main.home

import com.mg.axechen.wanandroid.base.BasePresenter
import com.mg.axechen.wanandroid.javabean.HomeListBean
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/3/23.
 * 首页数据
 */
interface HomeContract {
    interface View {
        fun getHomeListSuccess(homeListBean: HomeListBean,isRefresh: Boolean)
        fun getHomeListFail(msg: String)
    }

    interface mode {
        fun getHomeList(page: Int): Observable<Response<HomeListBean>>
    }

    interface Presenter : BasePresenter {
        fun getHomeList(isRefresh: Boolean)
    }
}