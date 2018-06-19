package com.mg.axechen.wanandroid.utils

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.mg.axechen.wanandroid.R


object ShareUtils {

    public fun shareApp(context: Context) {
        shareText(context, context.getString(R.string.share_app_content), context.getString(R.string.share_app_title), context.getString(R.string.share_app_title))
    }

    /**
     *
     */
    public fun shareText(context: Context, content: String, subject: String?, dlgTitle: String?) {
        if (TextUtils.isEmpty(content)) {
            return
        }
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        if (TextUtils.isEmpty(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        }

        intent.putExtra(Intent.EXTRA_TEXT, content)

        // 设置弹出框标题
        if (TextUtils.isEmpty(dlgTitle)) { // 自定义标题
            context.startActivity(Intent.createChooser(intent, dlgTitle))
        } else { // 系统默认标题
            context.startActivity(intent)
        }
    }
}