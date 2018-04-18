package com.mg.axechen.wanandroid.block.navi

import com.mg.axechen.wanandroid.javabean.NaviBean
import com.mg.axechen.wanandroid.network.NetWorkManager
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/4/11.
 */
class NaviWebsiteModel : NaviWebsiteContract.Model {

    override fun getWebsiteNavi(): Observable<Response<MutableList<NaviBean>>> {
        return NetWorkManager.getInstance().getRequest()!!.getNaviJson()
    }

}