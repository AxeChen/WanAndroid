package com.mg.axechen.wanandroid.block.collect.website

import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.SearchTag
import com.mg.axechen.wanandroid.network.NetWorkManager
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/4/20.
 */
class CollectWebsiteModel:CollectWebsiteContract.Model{
    override fun getCollectWebsite(): Observable<Response<MutableList<SearchTag>>> {
        return NetWorkManager.getInstance().getRequest()!!.getCollectWebList()
    }

}