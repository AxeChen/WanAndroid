package com.mg.axechen.wanandroid.block.collect

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.block.collect.article.CollectArticleFragment
import com.mg.axechen.wanandroid.block.collect.website.CollectWebsiteFragment
import kotlinx.android.synthetic.main.activity_collect.*

/**
 * Created by AxeChen on 2018/4/19.
 * 收藏页面
 */
class CollectActivity : BaseActivity() {

    companion object {

        fun lunch(context: Context) {
            context.startActivity(Intent(context, CollectActivity::class.java))
        }

    }

    private var fragments = mutableListOf<Fragment>()

    private val pageAdapter: CollectPagerAdapter by lazy {
        CollectPagerAdapter(supportFragmentManager, fragments)
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_collect
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
        initFragment()
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

}