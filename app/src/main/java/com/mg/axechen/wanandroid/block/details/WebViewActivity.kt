package com.mg.axechen.wanandroid.block.details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.MenuItem
import android.widget.FrameLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.ChromeClientCallbackManager
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectActivity
import com.mg.axechen.wanandroid.block.main.MainActivity
import kotlinx.android.synthetic.main.activity_web_view.*

/**
 * Created by AxeChen on 2018/3/31.
 *
 * webViewActivity
 * 基本的访问操作
 */
open class WebViewActivity : BaseCollectActivity() {

    companion object {
        val INTENT_TAG_URL: String = "intentTagStringUrl"
        val INTENT_TAG_TITLE: String = "intentTagStringTitle"
        fun lunch(context: Activity, url: String, title: String) {
            var intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(INTENT_TAG_URL, url)
            intent.putExtra(INTENT_TAG_TITLE, title)
            context.startActivity(intent)
        }
    }

    /**
     * 标题
     */
    var title: String? = null

    /**
     * url
     */
    var url: String? = null

    /**
     * webView
     */
    var agentWebView: AgentWeb? = null

    var loadComplete:Boolean = false

    var toolbar: Toolbar? = null

    override fun setLayoutId(): Int {
        return R.layout.activity_web_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
        getIntentData()
        showWebContent()
    }

    fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.apply {
            toolbar?.title = "正在加载"
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

    /**
     * 获取传入的数据
     */
   open fun getIntentData() {
        title = intent.getStringExtra(INTENT_TAG_TITLE)
        url = intent.getStringExtra(INTENT_TAG_URL)
    }

    /**
     * 展示webView的内容
     */
    fun showWebContent() {
        if (TextUtils.isEmpty(url)) {
            throw RuntimeException("传入的地址不存在")
        }
        var builder: AgentWeb.AgentBuilder = AgentWeb.with(this)
        builder.setAgentWebParent(flWebContent, FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setIndicatorColor(resources.getColor(getColor(this)))
                .setReceivedTitleCallback(receivedTitleCallback)
                .createAgentWeb()
                .ready().go(url)
    }

    /**
     * 加载成功之后，修改标题
     */
    private val receivedTitleCallback =
            ChromeClientCallbackManager.ReceivedTitleCallback { _, title ->
                title?.let {
                    toolbar?.title = title
                    loadComplete = true
                    invalidateOptionsMenu()
                }
            }

    fun getColor(activity: Activity): Int {
        var color: Int = WanAndroidApplication.instance!!.getThemeColor(activity, WanAndroidApplication.instance!!.getTheme(activity)!!)
        return color
    }


}