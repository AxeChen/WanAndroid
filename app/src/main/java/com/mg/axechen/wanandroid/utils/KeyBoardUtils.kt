package com.mg.axechen.wanandroid.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.text.InputFilter
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

import java.lang.reflect.Method


object KeyBoardUtils {

    private val TAG = "KeyBoardUtils"


    /**
     * 关闭软键盘
     *
     * @param activity 上下文Activity
     */
    fun closeKeyboard(activity: Activity) {
        val view = activity.window.peekDecorView()
        if (view != null) {
            val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * 打开软键盘
     *
     * @param context  上下文
     * @param editText 聚焦的editText
     */
    fun openKeyboard(context: Context, editText: EditText?) {
        if (editText != null) {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(editText, 0)
        }
    }

    /**
     * 判断软键盘是否显示
     *
     * @param rootView 根View
     * @return 软键盘是否显示：1 显示;0不显示
     */
    fun isKeyboardShown(rootView: View): Boolean {
        val softKeyboardHeight = 100
        val r = Rect()
        rootView.getWindowVisibleDisplayFrame(r)
        val dm = rootView.resources.displayMetrics
        val heightDiff = rootView.bottom - r.bottom
        return heightDiff > softKeyboardHeight * dm.density
    }

    /**
     * /禁止EditText弹出软件盘，光标依然正常显示
     *
     * @param editText 禁止的EditText
     */
    fun disableShowSoftInput(editText: EditText) {
        val cls = EditText::class.java
        var method: Method
        try {
            method = cls.getMethod("setShowSoftInputOnFocus", Boolean::class.javaPrimitiveType)
            method.isAccessible = true
            method.invoke(editText, false)

            method = cls.getMethod("setSoftInputShownOnFocus", Boolean::class.javaPrimitiveType)
            method.isAccessible = true
            method.invoke(editText, false)
        } catch (e: Exception) {
            Log.e(TAG, "disableShowSoftInput error=" + e)
        }

    }


}
