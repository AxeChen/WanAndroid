package com.mg.axechen.wanandroid.block.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.bilibili.magicasakura.widgets.TintImageView
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.block.login.LoginActivity
import com.mg.axechen.wanandroid.block.main.home.HomeFragment
import com.mg.axechen.wanandroid.block.main.knowledge.KnowledgeTreeListFragment
import com.mg.axechen.wanandroid.block.main.profile.ProfileFragment
import com.mg.axechen.wanandroid.block.main.project.ProjectListFragment
import com.mg.axechen.wanandroid.block.navi.NaviWebsiteActivity
import com.mg.axechen.wanandroid.block.search.SearchActivity
import com.mg.axechen.wanandroid.block.splash.SplashActivity
import com.mg.axechen.wanandroid.javabean.BannerBean
import com.mg.axechen.wanandroid.theme.ChangeThemeActivity
import com.mg.axechen.wanandroid.utils.SharePreferencesContants
import com.mg.axechen.wanandroid.utils.SharedPreferencesUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference


/**
 *  * Created by AxeChen on 2018/3/16.
 *
 *  主页
 */
class MainActivity : BaseActivity(), MainContract.View {

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    private var toolbar: Toolbar? = null

    private var viewPager: ViewPager? = null

    private var fragments = mutableListOf<Fragment>()

    private var homeViewPagerAdapter: HomeViewPagerAgapter? = null

    private var index: Int = 0

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLoginStatusReceiver()
        initToolBar()
        setViewPager()
        selectByIndex(0)
        initClickListener()
    }

    private fun setViewPager() {
        viewPager = findViewById(R.id.viewPager)
        fragments.add(HomeFragment())
        fragments.add(KnowledgeTreeListFragment())
        fragments.add(ProjectListFragment())
        fragments.add(ProfileFragment())
        homeViewPagerAdapter = HomeViewPagerAgapter(supportFragmentManager, fragments)
        viewPager.run {
            this!!.adapter = homeViewPagerAdapter
            addOnPageChangeListener(PagerChangeListener(this@MainActivity))
            offscreenPageLimit = fragments.size
        }
    }

    class PagerChangeListener(activity: MainActivity) : ViewPager.OnPageChangeListener {

        private var weakActivity: WeakReference<MainActivity>? = null

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
                activity.index = position
                activity.selectByIndex(position)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        if (SharedPreferencesUtils.getInt(SharePreferencesContants.USER_ID) == 0) {
            menu?.add(0, 1, 0, "登录")
        } else {
            menu?.add(0, 2, 0, "注销")
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.actionSearch -> {
                SearchActivity.lunch(this)
            }
            R.id.actionUrlNav -> {
                NaviWebsiteActivity.lunch(this)
            }
            1 -> {
                LoginActivity.lunch(this)
            }
            2 -> {
                SharedPreferencesUtils.userDataclear()
                SharedPreferencesUtils.systemDataClear()
                SplashActivity.lunch(this)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initClickListener() {
        llHome.setOnClickListener {
            viewPager?.currentItem = 0
        }

        llKnowledge.setOnClickListener({
            viewPager?.currentItem = 1
        })

        llProject.setOnClickListener({
            viewPager?.currentItem = 2
        })

        llUser.setOnClickListener({
            viewPager?.currentItem = 3
        })
    }


    private fun selectByIndex(index: Int) {
        when (index) {
            0 -> {
                selectTab(tvHome, ivHome)
                noSelectTab(tvKnowledge, ivKnowledge)
                noSelectTab(tvProject, ivProject)
                noSelectTab(tvUser, ivUser)
                supportActionBar!!.title = "首页"
            }
            1 -> {
                selectTab(tvKnowledge, ivKnowledge)
                noSelectTab(tvHome, ivHome)
                noSelectTab(tvProject, ivProject)
                noSelectTab(tvUser, ivUser)
                supportActionBar!!.title = "知识体系"
            }
            2 -> {
                selectTab(tvProject, ivProject)
                noSelectTab(tvHome, ivHome)
                noSelectTab(tvKnowledge, ivKnowledge)
                noSelectTab(tvUser, ivUser)
                supportActionBar!!.title = "项目"
            }
            3 -> {
                selectTab(tvUser, ivUser)
                noSelectTab(tvHome, ivHome)
                noSelectTab(tvKnowledge, ivKnowledge)
                noSelectTab(tvProject, ivProject)
                supportActionBar!!.title = "用户"
            }
        }
    }

    private fun selectTab(textView: TextView, imageView: TintImageView) {
        textView.setTextColor(resources.getColor(getColor(this)))
        imageView.setBackgroundTintList(getColor(this))
    }

    private fun noSelectTab(textView: TextView, imageView: TintImageView) {
        textView.setTextColor(resources.getColor(R.color.tab_icon_no_select))
        imageView.setBackgroundTintList(R.color.tab_icon_no_select)
        imageView.invalidate()
    }

    fun getColor(activity: MainActivity): Int {
        var color: Int = WanAndroidApplication.instance!!.getThemeColor(activity, WanAndroidApplication.instance!!.getTheme(activity)!!)
        return color
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


    override fun changeThemeRefresh() {
        selectByIndex(index)
    }

    override fun loginSuccess() {
        super.loginSuccess()
        invalidateOptionsMenu()
    }
}
