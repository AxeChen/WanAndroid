package com.mg.axechen.wanandroid.block.details

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectPresenter
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.utils.Contacts
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/3/19.
 */
class DetailActivity : WebViewActivity() {

    private var isCollect: Boolean = false

    private var homeData: HomeData? = null

    private var articleId: Int = 0

    private val presenter: BaseCollectPresenter by lazy {
        BaseCollectPresenter(this, SchedulerProvider.getInstatnce()!!)
    }

    companion object {
        val INTENT_TAG_HOMEDATA = "homeData"
        val INTENT_TAG_BOOLEAN_IS_COLLECT = "isCollect"
        val INTENT_TAG_INT_ARTICLE_ID = "articleId"
        fun lunch(context: Context, homeData: HomeData, isCollect: Boolean, articleId: Int) {
            var intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(INTENT_TAG_HOMEDATA, homeData)
            intent.putExtra(INTENT_TAG_BOOLEAN_IS_COLLECT, isCollect)
            intent.putExtra(INTENT_TAG_INT_ARTICLE_ID, articleId)
            context.startActivity(intent)
        }

    }

    override fun getIntentData() {
        homeData = intent.getParcelableExtra(INTENT_TAG_HOMEDATA)
        isCollect = intent.getBooleanExtra(INTENT_TAG_BOOLEAN_IS_COLLECT, false)
        articleId = intent.getIntExtra(INTENT_TAG_INT_ARTICLE_ID, 0)
        if (homeData == null) {
            finish()
            return
        }
        url = homeData?.link
        title = homeData?.title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (loadComplete) {
            if (isCollect) {
                menu?.add(0, 10, 0, "unCollect")?.setIcon(R.drawable.ic_like)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            } else {
                menu?.add(0, 10, 0, "collect")?.setIcon(R.drawable.ic_like_empty)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == 10) {
            if (isCollect) {
                presenter.unCollectArticle(articleId)
            } else {
                presenter.collectInArticle(articleId)
            }
            isCollect = !isCollect

            var intent = Intent()
            intent.putExtra(Contacts.INTENT_IS_COLLECT, isCollect)
            intent.putExtra(Contacts.COLLECT_ID, articleId)
            intent.action = Contacts.COLLECT_STATUS
            sendBroadcast(intent)

            invalidateOptionsMenu()
        }

        return super.onOptionsItemSelected(item)
    }
}