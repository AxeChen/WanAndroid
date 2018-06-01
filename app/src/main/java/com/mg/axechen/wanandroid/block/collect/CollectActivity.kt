package com.mg.axechen.wanandroid.block.collect

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.MenuItem
import android.view.View
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.block.collect.article.CollectArticleFragment
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectActivity
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectPresenter
import com.mg.axechen.wanandroid.block.collect.website.CollectWebsiteFragment
import com.mg.axechen.wanandroid.views.dialog.CollectArticleDialog
import com.mg.axechen.wanandroid.views.dialog.CollectWebsiteDialog
import kotlinx.android.synthetic.main.activity_collect.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/4/19.
 * 收藏页面
 */
class CollectActivity : BaseCollectActivity(), View.OnClickListener {


    companion object {
        fun lunch(context: Context) {
            context.startActivity(Intent(context, CollectActivity::class.java))
        }
    }

    private var fragments = mutableListOf<Fragment>()

    private val pageAdapter: CollectPagerAdapter by lazy {
        CollectPagerAdapter(supportFragmentManager, fragments)
    }

    private val presenter: BaseCollectPresenter by lazy {
        BaseCollectPresenter(this, SchedulerProvider.getInstatnce()!!)
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_collect
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
        initFragment()
        initFloatMenu()
    }

    private fun initFloatMenu() {
        fabCollectArticle.setOnClickListener(this)
        fabCollectWebSite.setOnClickListener(this)
    }

    private fun initFragment() {
        var articleFragment = CollectArticleFragment()
        var websiteFragment = CollectWebsiteFragment()
        fragments.add(articleFragment)
        fragments.add(websiteFragment)

        viewPager.run {
            adapter = pageAdapter
        }

        tabLayout.run {
            tabMode = TabLayout.MODE_FIXED
            tabGravity = TabLayout.GRAVITY_FILL
            setBackgroundResource(getThemeColor(this@CollectActivity))
            setupWithViewPager(viewPager)
        }

        tabLayout.getTabAt(0)?.text = "文章"
        tabLayout.getTabAt(1)?.text = "网站"

    }

    private fun getThemeColor(activity: Activity): Int {
        var color: Int = WanAndroidApplication.instance!!.getThemeColor(activity, WanAndroidApplication.instance!!.getTheme(activity)!!)
        return color
    }

    private fun initActionBar() {
        toolbar.run {
            title = "我的收藏"
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCollectArticleDialog() {
        var collectArticle = CollectArticleDialog()
        collectArticle.show(supportFragmentManager, "CollectArticleDialog")
        collectArticle.clickListener = object : CollectArticleDialog.OnPositiveClickListener {
            override fun onPositiveClick(title: String, author: String, edLink: String) {
                presenter.collectOutArticle(title, author, edLink)
            }
        }
    }

    private fun showCollectWebSiteDialog() {
        var collectWebsite = CollectWebsiteDialog()
        collectWebsite.show(supportFragmentManager, "CollectWebsiteDialog")
        collectWebsite.listener = object : CollectWebsiteDialog.CollectWebSiteListener {
            override fun collectWebsite(title: String, link: String) {
                presenter.collectWebsite(title, link)
            }

        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fabCollectArticle -> {
                fabMenu.collapse()
                showCollectArticleDialog()
            }
            R.id.fabCollectWebSite -> {
                fabMenu.collapse()
                showCollectWebSiteDialog()
            }
        }
    }

}