package com.mg.axechen.wanandroid.block.main.home

import com.mg.axechen.wanandroid.javabean.BannerBean
import com.mg.axechen.wanandroid.javabean.HomeListBean
import com.mg.axechen.wanandroid.network.NetWorkManager
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/3/23.
 */
class HomeModel : HomeContract.mode {

    override fun getHomeList(page: Int): Observable<Response<HomeListBean>> {
        return NetWorkManager.getInstance().getRequest()!!.getHomeList(page)
    }

    override fun getBannerData(): Observable<Response<List<BannerBean>>> {
        return NetWorkManager.getInstance().getRequest()!!.getBanner()
    }

}