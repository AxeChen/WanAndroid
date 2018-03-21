package com.mg.axechen.wanandroid.block.main

import com.mg.axechen.wanandroid.base.BasePresenter
import com.mg.axechen.wanandroid.javabean.BannerBean
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/3/19.
 * 主页的订阅类
 */
interface MainContract {
    interface View {
        open fun showBanner(banners:List<BannerBean>)
        open fun getBannerFail(errorMsg:String)
    }

    interface Model {
        open fun getBannerData(): Observable<Response<List<BannerBean>>>
    }

    interface Presenter : BasePresenter {
        open fun getBannerData()
    }
}