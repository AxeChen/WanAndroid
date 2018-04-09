package com.mg.axechen.wanandroid.base

import android.support.v4.app.Fragment

/**
 * Created by AxeChen on 2018/4/9.
 */
class BaseFragment : Fragment() {


    override fun onResume() {
        super.onResume()
        changeThemeRefresh()
    }

    /**
     * 修改主题之后，onResume刷新特定的控件
     */
    open fun changeThemeRefresh() {
    }
}