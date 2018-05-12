package com.mg.axechen.wanandroid.network.interceptor

import com.mg.axechen.wanandroid.utils.SharePreferencesContants
import com.mg.axechen.wanandroid.utils.SharedPreferencesUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by AxeChen on 2018/4/21.
 * 添加cookie做持久化
 */
class AddCookieInterceptor : Interceptor {

    private val COOKIE_NAME = "Cookie"

    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain?.request()
        val domain = request?.url()?.host()
        val builder = request?.newBuilder()
        val userId = SharedPreferencesUtils.getInt(SharePreferencesContants.USER_ID)
        if (domain!!.isNotEmpty() && userId != 0) {
            var cookies = SharedPreferencesUtils.getString(domain)
            if (cookies.isNotEmpty()) {
                builder?.addHeader(COOKIE_NAME, cookies)
            }
        }
        return chain.proceed(builder?.build())
    }

}