package com.mg.axechen.wanandroid.utils

import android.content.Context
import com.mg.axechen.wanandroid.WanAndroidApplication

/**
 * Created by AxeChen on 2018/4/19.
 */
class SharedPreferencesUtils {

    companion object {
        private val SYSTEM_DATA = "systemPre"
        private val USER_PRE = "userPre"

        private val sysPref = WanAndroidApplication.instance?.getSharedPreferences(SYSTEM_DATA, Context.MODE_PRIVATE)
        private val userPref = WanAndroidApplication.instance?.getSharedPreferences(USER_PRE, Context.MODE_PRIVATE)
        private val sysEditor = sysPref!!.edit()
        private val userEditor = userPref!!.edit()

        fun userDataclear() {
            userEditor.clear()
            userEditor.apply()
        }

        fun systemDataClear() {
            sysEditor.clear()
            sysEditor.apply()
        }

        fun putInt(key: String, data: Int) {
            userEditor.putInt(key, data)
            userEditor.apply()
        }

        fun putBoolean(key: String, data: Boolean) {
            userEditor.putBoolean(key, data)
            userEditor.apply()
        }

        fun putString(key: String, data: String) {
            userEditor.putString(key, data)
            userEditor.apply()
        }

        fun putSysInt(key: String, data: Int) = {
            sysEditor.putInt(key, data)
            sysEditor.apply()
        }

        fun putSysBoolean(key: String, data: Boolean) {
            sysEditor.putBoolean(key, data)
            sysEditor.apply()
        }

        fun putSysString(key: String, data: String) {
            sysEditor.putString(key, data)
            sysEditor.apply()
        }

        fun getInt(key: String): Int {
            return userPref!!.getInt(key, 0)
        }

        fun getString(key: String): String {
            return userPref!!.getString(key, "")
        }

        fun getBoolean(key: String): Boolean {
            return userPref!!.getBoolean(key, false)
        }

        fun getSysInt(key: String): Int {
            return sysPref!!.getInt(key, 0)
        }

        fun getSysString(key: String): String {
            return sysPref!!.getString(key, "")
        }

        fun getSysBoolean(key: String): Boolean {
            return sysPref!!.getBoolean(key, false)
        }
    }


}