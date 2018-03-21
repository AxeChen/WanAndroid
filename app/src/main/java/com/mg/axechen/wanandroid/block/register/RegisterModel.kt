package com.mg.axechen.wanandroid.block.register

import com.mg.axechen.wanandroid.network.NetWorkManager
import io.reactivex.Observable
import network.response.Response
import org.json.JSONObject

/**
 * Created by AxeChen on 2018/3/20.
 */
class RegisterModel : RegisterContract.Model{

    override fun register(userName: String, password: String, rePassword: String): Observable<Response<JSONObject>> {
        return NetWorkManager.getInstance().getRequest()!!.userRegister(userName, password, rePassword)
    }

}