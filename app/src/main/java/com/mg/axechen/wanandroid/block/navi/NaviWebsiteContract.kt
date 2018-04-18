package com.mg.axechen.wanandroid.block.navi

import com.mg.axechen.wanandroid.base.BasePresenter
import com.mg.axechen.wanandroid.javabean.NaviBean
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/4/11.
 */
class NaviWebsiteContract {

    interface View {
        fun getNaviWebSiteSuccess(beans: MutableList<NaviBean>)
        fun getNaiWebSiteFail(msg: String)
    }

    interface Model {
        fun getWebsiteNavi(): Observable<Response<MutableList<NaviBean>>>
    }

    interface Presenter : BasePresenter {
        fun getWebsiteNavi()
    }
}
