package com.mg.axechen.wanandroid.theme

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences


/**
 * @author xyczero
 * @time 16/5/2
 */
object ThemeHelper {

    private val CURRENT_THEME = "theme_current"

    val CARD_WAN = 0x1
    val CARD_HOPE = 0x2
    val CARD_STORM = 0x3
    val CARD_WOOD = 0x4
    val CARD_PINK = 0x5
    val CARD_THUNDER = 0x6
    val CARD_SAND = 0x7
    val CARD_FIREY = 0x8

    private fun getSharePreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("multiple_theme", Context.MODE_PRIVATE)
    }

    @SuppressLint("ApplySharedPref")
    fun setTheme(context: Context, themeId: Int) {
        getSharePreference(context).edit()
                .putInt(CURRENT_THEME, themeId)
                .commit()
    }

    fun getTheme(context: Context): Int {
        return getSharePreference(context).getInt(CURRENT_THEME, CARD_WAN)
    }

    fun isDefaultTheme(context: Context): Boolean {
        return getTheme(context) == CARD_WAN
    }

    fun getName(currentTheme: Int): String {
        when (currentTheme) {
            CARD_PINK -> return "THE SAKURA"
            CARD_STORM -> return "THE STORM"
            CARD_WOOD -> return "THE WOOD"
            CARD_WAN -> return "THE LIGHT"
            CARD_HOPE -> return "THE HOPE"
            CARD_THUNDER -> return "THE THUNDER"
            CARD_SAND -> return "THE SAND"
            CARD_FIREY -> return "THE FIREY"
        }
        return "THE RETURN"
    }
}
