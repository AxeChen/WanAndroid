package com.mg.axechen.wanandroid.block.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.block.main.knowledge.KnowledgeTreeListFragment
import com.mg.axechen.wanandroid.block.main.project.ProjectListFragment
import com.mg.axechen.wanandroid.javabean.BannerBean
import com.mg.axechen.wanandroid.theme.ChangeThemeActivity
import kotlinx.android.synthetic.main.activity_main.*


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

    private var mCurrentFragment: Fragment? = null

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolBar()
        initDrawer()
        //展示主页
        showFragment(ProjectListFragment())
    }

    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        if (mCurrentFragment != null) {
            if (fragment.isAdded) {
                transaction.hide(mCurrentFragment).show(fragment)
            } else {
                transaction.hide(mCurrentFragment).add(R.id.flContent, fragment)
            }
        } else {
            transaction.add(R.id.flContent, fragment)
        }
        mCurrentFragment = fragment
        transaction.commitAllowingStateLoss()
    }

    private fun initToolBar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.run {
            setSupportActionBar(toolbar)
        }
    }

    private fun initDrawer() {
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
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
