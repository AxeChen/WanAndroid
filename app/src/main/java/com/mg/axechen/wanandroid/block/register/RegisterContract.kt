package com.mg.axechen.wanandroid.block.register

import com.mg.axechen.wanandroid.base.BasePresenter
import io.reactivex.Observable
import network.response.Response
import org.json.JSONObject

/**
 * Created by AxeChen on 2018/3/20.
 */
interface RegisterContract {
    interface View {
        fun userNameIsEmpty()
        fun passwordIsEmpty()
        fun rePasswordFault()
        fun registerSuccess()
        fun registerFail(errorMsg: String)
    }

    interface Presenter : BasePresenter {
        fun register(userName: String, password: String, rePassword: String)
    }

    interface Model {
        fun register(userName: String, password: String, rePassword: String): Observable<Response<JSONObject>>
    }
}