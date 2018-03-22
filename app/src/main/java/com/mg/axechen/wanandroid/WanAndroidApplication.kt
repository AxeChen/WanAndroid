package com.mg.axechen.wanandroid

import android.app.Application
import android.content.Context
import android.support.annotation.ColorRes
import com.bilibili.magicasakura.utils.ThemeUtils
import com.mg.axechen.wanandroid.network.NetWorkManager
import com.mg.axechen.wanandroid.theme.ThemeHelper

/**
 * Created by AxeChen on 2018/3/19.
 * Application
 */

class WanAndroidApplication : Application(), ThemeUtils.switchColor {

    override fun onCreate() {
        super.onCreate()
        NetWorkManager.getInstance().init()
        ThemeUtils.setSwitchColor(this)
    }

    override fun replaceColorById(context: Context?, colorId: Int): Int {
        if (ThemeHelper.isDefaultTheme(context)) {
            return context?.resources!!.getColor(colorId)
        }
        val theme = getTheme(context!!)
        var cId: Int = colorId
        if (theme != null) {
            cId = getThemeColorId(context, cId, theme)
        }
        return context.resources.getColor(cId)
    }

    private fun getTheme(context: Context): String? {
        return when {
            ThemeHelper.getTheme(context) == ThemeHelper.CARD_STORM -> "blue"
            ThemeHelper.getTheme(context) == ThemeHelper.CARD_HOPE -> "purple"
            ThemeHelper.getTheme(context) == ThemeHelper.CARD_WOOD -> "green"
            ThemeHelper.getTheme(context) == ThemeHelper.CARD_WAN -> "wan"
            ThemeHelper.getTheme(context) == ThemeHelper.CARD_THUNDER -> "black"
            ThemeHelper.getTheme(context) == ThemeHelper.CARD_SAND -> "orange"
            ThemeHelper.getTheme(context) == ThemeHelper.CARD_FIREY -> "red"
            ThemeHelper.getTheme(context) == ThemeHelper.CARD_PINK -> "pink"
            else -> null
        }
    }

    @ColorRes
    private fun getThemeColorId(context: Context, colorId: Int, theme: String): Int {
        when (colorId) {
            R.color.theme_color_primary -> return context.resources.getIdentifier(theme, "color", packageName)
            R.color.theme_color_primary_dark -> return context.resources.getIdentifier(theme + "_dark", "color", packageName)
            R.color.theme_color_primary_trans -> return context.resources.getIdentifier(theme + "_trans", "color", packageName)
        }
        return colorId
    }

    override fun replaceColor(context: Context?, color: Int): Int {
        if (ThemeHelper.isDefaultTheme(context)) {
            return color
        }
        val theme = getTheme(context!!)
        var colorId = -1

        if (theme != null) {
            colorId = getThemeColor(context, color, theme)
        }
        return if (colorId != -1) resources.getColor(colorId) else color
    }

    @ColorRes
    private fun getThemeColor(context: Context, color: Int, theme: String): Int {
        when (color) {
            -0x48d67 -> return context.resources.getIdentifier(theme, "color", packageName)
            -0x47a98f -> return context.resources.getIdentifier(theme + "_dark", "color", packageName)
            -0x660fb794 -> return context.resources.getIdentifier(theme + "_trans", "color", packageName)
        }
        return -1
    }
}
