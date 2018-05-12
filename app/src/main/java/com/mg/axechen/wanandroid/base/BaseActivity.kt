package com.mg.axechen.wanandroid.base

import android.app.Activity
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.bilibili.magicasakura.utils.ThemeUtils
import com.gyf.barlibrary.ImmersionBar
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.theme.ThemeHelper
import android.content.IntentFilter
import com.mg.axechen.wanandroid.utils.Contacts
import java.lang.ref.WeakReference


/**
 * Created by AxeChen on 2018/3/19.
 *
 * BaseActivity 基类activity
 */
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var immersionBar: ImmersionBar

    private var mCurrentTheme: Int = 0

    private var receiver: MyLoginStatusReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCurrentTheme = ThemeHelper.getTheme(this)
        setContentView(setLayoutId())
        initImmersionBar()
    }

    protected fun registerLoginStatusReceiver() {
        receiver = MyLoginStatusReceiver(this)//广播接受者实例
        val intentFilter = IntentFilter()
        intentFilter.addAction(Contacts.LOGIN_SUCCESS)
        registerReceiver(receiver, intentFilter)
    }

    protected abstract fun setLayoutId(): Int

    /**
     * 修改主题之后，onResume刷新特定的控件(有些控件是通过代码设置的颜色因此需要主动去刷新控件)
     */
    open fun changeThemeRefresh() {
    }

    open fun loginSuccess() {
    }


    open fun initImmersionBar() {
        immersionBar = ImmersionBar.with(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        immersionBar.destroy()
        if (receiver != null) {
            unregisterReceiver(receiver)
        }
    }

    override fun finish() {
        if (!isFinishing) {
            super.finish()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ThemeUtils.getColorById(this, R.color.theme_color_primary_dark)
            val description = ActivityManager.TaskDescription(null, null,
                    ThemeUtils.getThemeAttrColor(this, android.R.attr.colorPrimary))
            setTaskDescription(description)
        }
    }

    open fun changeTheme(currentTheme: Int) {
        if (ThemeHelper.getTheme(this) !== currentTheme) {
            ThemeUtils.refreshUI(this, object : ThemeUtils.ExtraRefreshable {
                override fun refreshGlobal(activity: Activity) {
                    //for global setting, just do once
                    if (Build.VERSION.SDK_INT >= 21) {
                        val context = this
                        val taskDescription = ActivityManager.TaskDescription(null, null,
                                ThemeUtils.getThemeAttrColor(this@BaseActivity, android.R.attr.colorPrimary))
                        setTaskDescription(taskDescription)
                        window.statusBarColor = ThemeUtils.getColorById(this@BaseActivity, R.color.theme_color_primary_dark)
                    }
                    mCurrentTheme = ThemeHelper.getTheme(this@BaseActivity)
                }

                override fun refreshSpecificView(view: View) {
                    //TODO: will do this for each traversal
                }
            }
            )
        }
    }

    /**
     * 登录状态广播接收
     */
    class MyLoginStatusReceiver(activity: BaseActivity) : BroadcastReceiver() {
        private var weakActivity: WeakReference<BaseActivity>? = null

        init {
            this.weakActivity = WeakReference(activity)
        }

        override fun onReceive(context: Context?, intent: Intent?) {
            weakActivity?.get()?.loginSuccess()
        }
    }

    override fun onResume() {
        super.onResume()
        changeThemeRefresh()
        changeTheme(mCurrentTheme)
    }
}