package com.mg.axechen.wanandroid.block.login

import com.mg.axechen.wanandroid.javabean.LoginBean
import com.mg.axechen.wanandroid.network.NetWorkManager
import com.mg.axechen.wanandroid.utils.SharePreferencesContants
import com.mg.axechen.wanandroid.utils.SharedPreferencesUtils
import io.reactivex.Observable
import network.response.Response

/**
 * Created by AxeChen on 2018/3/20.
 * 登陆model
 */
class LoginModel : LoginContract.Model {

    override fun saveCookies(bean: LoginBean) {
        //保存用户的id
        SharedPreferencesUtils.putInt(SharePreferencesContants.USER_ID, bean.id)
        //保存用户的姓名
        SharedPreferencesUtils.putString(SharePreferencesContants.USER_NAME, bean.username!!)
        //保存用户的邮箱
        SharedPreferencesUtils.putString(SharePreferencesContants.USER_EMAIL, bean.email!!)
    }

    override fun login(userName: String, password: String): Observable<Response<LoginBean>> {
        return NetWorkManager.getInstance().getRequest()!!.userLogin(userName, password)
    }
}