package com.mg.axechen.wanandroid.block.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.block.main.home.HomeFragment
import com.mg.axechen.wanandroid.block.main.knowledge.KnowledgeTreeListFragment
import com.mg.axechen.wanandroid.block.main.profile.ProfileFragment
import com.mg.axechen.wanandroid.block.main.project.ProjectListFragment
import com.mg.axechen.wanandroid.javabean.BannerBean
import com.mg.axechen.wanandroid.theme.ChangeThemeActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference


/**
 *  * Created by AxeChen on 2018/3/16.
 *
 *  主页
 */
class MainActivity : BaseActivity(), MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    private var toolbar: Toolbar? = null

    private var viewPager: ViewPager? = null

    private var fragments = mutableListOf<Fragment>()

    private var homeViewPagerAgapter: HomeViewPagerAgapter? = null

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolBar()
        setViewPager()
    }

    private fun setViewPager() {
        viewPager = findViewById(R.id.viewPager)

        var homeFragment: HomeFragment = HomeFragment()
        var knowledgeFragment: KnowledgeTreeListFragment = KnowledgeTreeListFragment()
        var projectListFragment: ProjectListFragment = ProjectListFragment()
        var profileFragment: ProfileFragment = ProfileFragment()

        fragments.add(homeFragment)
        fragments.add(knowledgeFragment)
        fragments.add(projectListFragment)
        fragments.add(profileFragment)

        homeViewPagerAgapter = HomeViewPagerAgapter(supportFragmentManager, fragments)
        viewPager.run {
            this!!.adapter = homeViewPagerAgapter
            addOnPageChangeListener(PagerChangeListener(this@MainActivity))
        }
    }

    class PagerChangeListener(activity: MainActivity) : ViewPager.OnPageChangeListener {

        var weakActivity: WeakReference<MainActivity>? = null

        init {
            this.weakActivity = WeakReference(activity)
        }

        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            var activity: MainActivity? = weakActivity?.get()
            if (activity != null) {
                when (position) {
                    0 -> {
                        activity.tvHome.setTextColor(activity.resources.getColor(getColor(activity)))
                        activity.ivHome.setBackgroundTintList(getColor(activity))
                        activity.supportActionBar!!.title = "首页"
                    }
                    1 -> {
                        activity.tvHome.setTextColor(activity.resources.getColor(R.color.wan_dark))
                        activity.ivHome.setBackgroundTintList(android.R.color.transparent)
                        activity.supportActionBar!!.title = "知识体系"
                    }
                    2 -> {
                        activity.supportActionBar!!.title = "项目"
                    }

                    3 -> {
                        activity.supportActionBar!!.title = "用户"
                    }
                }
            }


        }

        fun getColor(activity: MainActivity): Int {
            var color: Int = WanAndroidApplication.instance!!.getThemeColor(activity, WanAndroidApplication.instance!!.getTheme(activity)!!)
            return color
        }

    }


    private fun initToolBar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.run {
            setSupportActionBar(toolbar)
            supportActionBar!!.title = "首页"
        }
    }

    override fun showBanner(banners: List<BannerBean>) {
        Toast.makeText(this, "请求成功", Toast.LENGTH_SHORT).show()
    }

    override fun getBannerFail(errorMsg: String) {
        Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navTheme -> {
                ChangeThemeActivity.launch(this)
            }
        }
        return true
    }

}
