package com.mg.axechen.wanandroid.block.login

import com.mg.axechen.wanandroid.base.BasePresenter
import com.mg.axechen.wanandroid.javabean.LoginBean
import io.reactivex.Observable
import network.response.Response
import org.json.JSONObject

/**
 * Created by AxeChen on 2018/3/20.
 * 登陆注册Activity
 */
interface LoginContract {

    interface View {
        fun loginSuccess()
        fun loginFail(errorMsg:String)
        fun userNameIsEmpty()
        fun passwordIsEmpty()
    }

    interface Presenter : BasePresenter {
        fun login(userName: String, password: String)
    }

    interface Model {
        fun login(userName: String, password: String): Observable<Response<LoginBean>>

        fun saveCookies(bean: LoginBean)
    }
}