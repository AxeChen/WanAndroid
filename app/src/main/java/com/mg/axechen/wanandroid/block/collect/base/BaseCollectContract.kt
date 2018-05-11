package com.mg.axechen.wanandroid.block.collect.base

import com.mg.axechen.wanandroid.base.BasePresenter
import io.reactivex.Observable
import network.response.Response
import org.json.JSONObject
import retrofit2.http.Query


/**
 * Created by AxeChen on 2018/5/3.
 */
interface BaseCollectContract {
    interface Model {
        fun collectInArticle(id: Int): Observable<Response<JSONObject>>

        fun collectOutArticle(title: String, author: String, link: String): Observable<Response<JSONObject>>

        fun unCollectArticle(id: Int): Observable<Response<JSONObject>>

        fun collectWebsite(name: String, link: String): Observable<Response<JSONObject>>

        fun unCollectWebsite(id: Int): Observable<Response<JSONObject>>

        fun updateWebsite(id: String, name: String, link: String): Observable<Response<JSONObject>>
    }

    interface Presenter : BasePresenter {
        fun collectInArticle(id: Int)

        fun collectOutArticle(title: String, author: String, link: String)

        fun unCollectArticle(id: Int)

        fun collectWebsite(name: String, link: String)

        fun unCollectWebsite(id: Int)

        fun updateWebsite(id: String, name: String, link: String)
    }
}