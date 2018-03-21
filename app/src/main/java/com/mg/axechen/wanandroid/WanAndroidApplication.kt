package com.mg.axechen.wanandroid

import android.app.Application
import com.mg.axechen.wanandroid.network.NetWorkManager

/**
 * Created by AxeChen on 2018/3/19.
 * Application
 */

class WanAndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NetWorkManager.getInstance().init()
    }
}
