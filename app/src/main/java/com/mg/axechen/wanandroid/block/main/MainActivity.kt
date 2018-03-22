package com.mg.axechen.wanandroid.block.main

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.bilibili.magicasakura.utils.ThemeUtils
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.javabean.BannerBean
import com.mg.axechen.wanandroid.theme.CardPickerDialog
import com.mg.axechen.wanandroid.theme.ThemeHelper
import kotlinx.android.synthetic.main.activity_main.*


/**
 * 主页
 */
class MainActivity : BaseActivity(), MainContract.View, NavigationView.OnNavigationItemSelectedListener {
    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }


    private var toolbar: Toolbar? = null

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolBar()
        initDrawer()
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navTheme -> {
                val dialog = CardPickerDialog()
                dialog.setClickListener(CardPickerDialog.ClickListener { it ->
                    if (ThemeHelper.getTheme(this@MainActivity) !== it) {
                        ThemeHelper.setTheme(this@MainActivity, it)
                        ThemeUtils.refreshUI(this@MainActivity, object : ThemeUtils.ExtraRefreshable {
                            override fun refreshGlobal(activity: Activity) {
                                //for global setting, just do once
                                if (Build.VERSION.SDK_INT >= 21) {
                                    val context = this@MainActivity
                                    val taskDescription = ActivityManager.TaskDescription(null, null,
                                            ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary))
                                    setTaskDescription(taskDescription)
                                    window.statusBarColor = ThemeUtils.getColorById(context, R.color.theme_color_primary_dark)
                                }
                            }

                            override fun refreshSpecificView(view: View) {
                                //TODO: will do this for each traversal
                            }
                        }
                        )
                    }
                })
                dialog.show(supportFragmentManager, CardPickerDialog.TAG)
            }
        }
        return true
    }

}
