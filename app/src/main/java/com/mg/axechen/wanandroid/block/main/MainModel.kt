package com.mg.axechen.wanandroid.block.main

import com.mg.axechen.wanandroid.javabean.BannerBean
import com.mg.axechen.wanandroid.network.NetWorkManager
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/3/19.
 */
class MainModel : MainContract.Model {

    override fun getBannerData(): Observable<Response<List<BannerBean>>> {
        return NetWorkManager.getInstance().getRequest()!!.getBanner()
    }


}