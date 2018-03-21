package com.mg.axechen.wanandroid.block.login

import com.mg.axechen.wanandroid.network.NetWorkManager
import io.reactivex.Observable
import network.response.Response
import org.json.JSONObject

/**
 * Created by AxeChen on 2018/3/20.
 * 登陆model
 */
class LoginModel : LoginContract.Model {
    override fun login(userName: String, password: String): Observable<Response<JSONObject>> {
        return NetWorkManager.getInstance().getRequest()!!.userLogin(userName, password)
    }
}