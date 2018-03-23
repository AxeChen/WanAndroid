package com.mg.axechen.wanandroid.theme

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.bilibili.magicasakura.utils.ThemeUtils
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_change_theme.*

/**
 * Created by AxeChen on 2018/3/21.
 * 主题切换
 */
class ChangeThemeActivity : BaseActivity(), View.OnClickListener {

    var toolbar: Toolbar? = null
    private var mCurrentTheme: Int = 0

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, ChangeThemeActivity::class.java))
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_change_theme
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolBar()
        initClickListener()
        setRadioCheck(ThemeHelper.getTheme(this))
    }

    private fun initToolBar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.run {
            title = "修改主题"
            setSupportActionBar(toolbar)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initClickListener() {
        flWan.setOnClickListener(this)
        flPurple.setOnClickListener(this)
        flBlue.setOnClickListener(this)
        flGreen.setOnClickListener(this)
        flPink.setOnClickListener(this)
        flBlack.setOnClickListener(this)
        flOrange.setOnClickListener(this)
        flRed.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.flWan -> {
                mCurrentTheme = ThemeHelper.CARD_WAN
            }
            R.id.flPurple -> {
                mCurrentTheme = ThemeHelper.CARD_HOPE
            }
            R.id.flBlue -> {
                mCurrentTheme = ThemeHelper.CARD_STORM
            }
            R.id.flGreen -> {
                mCurrentTheme = ThemeHelper.CARD_WOOD
            }
            R.id.flPink -> {
                mCurrentTheme = ThemeHelper.CARD_PINK
            }
            R.id.flBlack -> {
                mCurrentTheme = ThemeHelper.CARD_THUNDER
            }
            R.id.flOrange -> {
                mCurrentTheme = ThemeHelper.CARD_SAND
            }
            R.id.flRed -> {
                mCurrentTheme = ThemeHelper.CARD_FIREY
            }
        }
        changeTheme(mCurrentTheme)
        setRadioCheck(mCurrentTheme)
    }

    private fun setRadioCheck(theme: Int) {

        if (theme == ThemeHelper.CARD_WAN) {
            rbWan.visibility = View.VISIBLE
        } else {
            rbWan.visibility = View.GONE
        }

        if (theme == ThemeHelper.CARD_HOPE) {
            rbPurple.visibility = View.VISIBLE
        } else {
            rbPurple.visibility = View.GONE
        }

        if (theme == ThemeHelper.CARD_STORM) {
            rbBlue.visibility = View.VISIBLE
        } else {
            rbBlue.visibility = View.GONE
        }

        if (theme == ThemeHelper.CARD_WOOD) {
            rbGreen.visibility = View.VISIBLE
        } else {
            rbGreen.visibility = View.GONE
        }
        if (theme == ThemeHelper.CARD_PINK) {
            rbPink.visibility = View.VISIBLE
        } else {
            rbPink.visibility = View.GONE
        }
        if (theme == ThemeHelper.CARD_THUNDER) {
            rbBlack.visibility = View.VISIBLE
        } else {
            rbBlack.visibility = View.GONE
        }
        if (theme == ThemeHelper.CARD_SAND) {
            rbOrange.visibility = View.VISIBLE
        } else {
            rbOrange.visibility = View.GONE
        }
        if (theme == ThemeHelper.CARD_FIREY) {
            rbRed.visibility = View.VISIBLE
        } else {
            rbRed.visibility = View.GONE
        }
    }

    override fun changeTheme(currentTheme: Int) {
        if (ThemeHelper.getTheme(this) !== currentTheme) {
            ThemeHelper.setTheme(this, currentTheme)
            ThemeUtils.refreshUI(this, object : ThemeUtils.ExtraRefreshable {
                override fun refreshGlobal(activity: Activity) {
                    //for global setting, just do once
                    if (Build.VERSION.SDK_INT >= 21) {
                        val context = this
                        val taskDescription = ActivityManager.TaskDescription(null, null,
                                ThemeUtils.getThemeAttrColor(this@ChangeThemeActivity, android.R.attr.colorPrimary))
                        setTaskDescription(taskDescription)
                        window.statusBarColor = ThemeUtils.getColorById(this@ChangeThemeActivity, R.color.theme_color_primary_dark)
                    }
                }

                override fun refreshSpecificView(view: View) {
                    //TODO: will do this for each traversal
                }
            }
            )
        }
    }

}