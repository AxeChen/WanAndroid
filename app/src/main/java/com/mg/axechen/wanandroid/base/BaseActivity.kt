package com.mg.axechen.wanandroid.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import com.mg.axechen.wanandroid.R

/**
 * Created by AxeChen on 2018/3/19.
 *
 * BaseActivity 基类activity
 */
abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var immersionBar: ImmersionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
        initImmersionBar()
    }

    protected abstract fun setLayoutId(): Int

    open fun initImmersionBar() {
        immersionBar = ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.colorPrimaryDark)
    }

    override fun onDestroy() {
        super.onDestroy()
        immersionBar.destroy()
    }

    override fun finish() {
        if (!isFinishing) {
            super.finish()
        }
    }


}