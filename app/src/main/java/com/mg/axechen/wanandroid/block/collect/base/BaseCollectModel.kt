package com.mg.axechen.wanandroid.block.collect.base

import com.mg.axechen.wanandroid.network.NetWorkManager
import io.reactivex.Observable
import network.response.Response
import org.json.JSONObject

/**
 * Created by AxeChen on 2018/5/3.
 */
class BaseCollectModel : BaseCollectContract.Model {
    override fun collectInArticle(id: Int): Observable<Response<JSONObject>> {
        return NetWorkManager.getInstance().getRequest()!!.collectInArticle(id)
    }

    override fun collectOutArticle(title: String, author: String, link: String): Observable<Response<JSONObject>> {
        return NetWorkManager.getInstance().getRequest()!!.collectOutArticle(title, author, link)
    }

    override fun unCollectArticle(id: Int): Observable<Response<JSONObject>> {
        return NetWorkManager.getInstance().getRequest()!!.unCollectArticle(id)
    }

    override fun collectWebsite(name: String, link: String): Observable<Response<JSONObject>> {
        return NetWorkManager.getInstance().getRequest()!!.collectWebsite(name, link)
    }

    override fun unCollectWebsite(id: Int): Observable<Response<JSONObject>> {
        return NetWorkManager.getInstance().getRequest()!!.unCollectWebsite(id)
    }

    override fun updateWebsite(id: String, name: String, link: String): Observable<Response<JSONObject>> {
        return NetWorkManager.getInstance().getRequest()!!.updateWebsite(id, name, link)
    }

}