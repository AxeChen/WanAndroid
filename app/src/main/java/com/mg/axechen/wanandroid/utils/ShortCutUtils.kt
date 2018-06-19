package com.mg.axechen.wanandroid.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import com.google.gson.Gson
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.block.knowledgetree.KnowledgeTreeDetailActivity
import com.mg.axechen.wanandroid.block.splash.SplashActivity
import com.mg.axechen.wanandroid.javabean.TreeBean

object ShortCutUtils{
    public fun addShortcut(activity: Activity,treeBean:TreeBean,initIndex:Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            val sIntent = Intent(Intent.ACTION_MAIN)
            sIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            sIntent.setClass(activity, SplashActivity::class.java)
            sIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            sIntent.action = "android.intent.action.SINGLE_INSTANCE_SHARE"
            sIntent.putExtra("SHORT_CUT", true)
            var beanJson = Gson().toJson(treeBean)
            SharedPreferencesUtils.putString(treeBean!!.name!!, beanJson)
            sIntent.putExtra(KnowledgeTreeDetailActivity.INTENT_TAG_JAVABEAN_TREE_BEAN, treeBean!!.name!!)
            sIntent.putExtra(KnowledgeTreeDetailActivity.INTENT_TAG_INT_INDEX, initIndex)

            val installer = Intent()
            installer.putExtra("duplicate", false)
            installer.putExtra("android.intent.extra.shortcut.INTENT", sIntent)
            installer.putExtra("android.intent.extra.shortcut.NAME", treeBean!!.name)
            installer.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(activity, R.drawable.ic_launcher))
            installer.action = "com.android.launcher.action.INSTALL_SHORTCUT"
            activity.sendBroadcast(installer)
        } else {
            val mShortcutManager = activity.getSystemService(ShortcutManager::class.java)
            val shortCuts = mShortcutManager.dynamicShortcuts
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.setClass(activity, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.action = "android.intent.action.SINGLE_INSTANCE_SHARE"
            intent.putExtra("SHORT_CUT", true)
            val beanJson = Gson().toJson(treeBean)
            SharedPreferencesUtils.putString(treeBean!!.name!!, beanJson)
            intent.putExtra(KnowledgeTreeDetailActivity.INTENT_TAG_JAVABEAN_TREE_BEAN, treeBean!!.name!!)
            intent.putExtra(KnowledgeTreeDetailActivity.INTENT_TAG_INT_INDEX, initIndex)

            val info = ShortcutInfo.Builder(activity, treeBean!!.name!!)
                    .setShortLabel(treeBean!!.name!!)
                    .setIcon(Icon.createWithResource(activity, R.drawable.ic_launcher))
                    .setIntent(intent)
                    .build()
            if (shortCuts.size == mShortcutManager.maxShortcutCountPerActivity) {
                shortCuts.removeAt(0)
            }
            shortCuts.add(info)
            mShortcutManager.addDynamicShortcuts(shortCuts)
            mShortcutManager.updateShortcuts(shortCuts)
        }
    }
}