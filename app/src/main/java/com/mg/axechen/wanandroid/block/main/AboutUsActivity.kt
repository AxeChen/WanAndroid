package com.mg.axechen.wanandroid.block.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.block.details.WebViewActivity
import kotlinx.android.synthetic.main.activity_about_us.*

/**
 * Created by AxeChen on 2018/5/12.
 */
class AboutUsActivity : BaseActivity() {

    private val HOME_URL = "http://www.wanandroid.com/index"
    private val OPEN_API = "http://www.wanandroid.com/blog/show/2"
    private val PROJECT_URL = "https://github.com/AxeChen/WanAndroid"
    private val DEVELOPER_BLOG = "https://www.jianshu.com/u/05f7d21f41ed"
    private val DEVELOPER_GITHUB = "https://github.com/AxeChen"

    private var toolbar: Toolbar? = null

    companion object {
        fun lunch(context: Context) {
            context.startActivity(Intent(context, AboutUsActivity::class.java))
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_about_us
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
        initClickListener()
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.apply {
            toolbar?.title = "关于软件"
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initClickListener() {
        tvHome.setOnClickListener({
            WebViewActivity.lunch(this@AboutUsActivity, HOME_URL, "WanAndroid主页")
        })
        tvOpenApi.setOnClickListener({
            WebViewActivity.lunch(this@AboutUsActivity, OPEN_API, "WanAndroid开放API")
        })
        tvSrc.setOnClickListener({
            WebViewActivity.lunch(this@AboutUsActivity, PROJECT_URL, "本软件源码地址")
        })
        tvBlog.setOnClickListener({
            WebViewActivity.lunch(this@AboutUsActivity, DEVELOPER_BLOG, "开发者简书地址")
        })
        tvGitHub.setOnClickListener({
            WebViewActivity.lunch(this@AboutUsActivity, DEVELOPER_GITHUB, "开发者GitHub主页")
        })
    }


}