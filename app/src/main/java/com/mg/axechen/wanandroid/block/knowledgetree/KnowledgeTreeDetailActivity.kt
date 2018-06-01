package com.mg.axechen.wanandroid.block.knowledgetree

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.block.splash.SplashActivity
import com.mg.axechen.wanandroid.javabean.TreeBean
import com.mg.axechen.wanandroid.utils.SharedPreferencesUtils
import kotlinx.android.synthetic.main.activity_knowledge_tree_detail.*


/**
 * Created by AxeChen on 2018/4/8.
 * 知识体系详情
 */
class KnowledgeTreeDetailActivity : BaseActivity() {

    var initIndex = 0

    companion object {
        val INTENT_TAG_JAVABEAN_TREE_BEAN = "treeBean"
        val INTENT_TAG_INT_INDEX = "index"
        fun lunch(context: Context, treeBean: TreeBean, index: Int) {
            var intent = Intent(context, KnowledgeTreeDetailActivity::class.java)
            intent.putExtra(INTENT_TAG_JAVABEAN_TREE_BEAN, treeBean)
            intent.putExtra(INTENT_TAG_INT_INDEX, index)
            context.startActivity(intent)
        }
    }

    private var treeBean: TreeBean? = null

    private var fragments = mutableListOf<KnowledgeListFragment>()

    private val viewPagerAdapter: KnowledgeDetailsAdapter by lazy {
        KnowledgeDetailsAdapter(supportFragmentManager, fragments)
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_knowledge_tree_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentData()
        initFrame()
    }

    private fun getIntentData() {
        treeBean = intent.getSerializableExtra(INTENT_TAG_JAVABEAN_TREE_BEAN) as TreeBean?
        initIndex = intent.getIntExtra(INTENT_TAG_INT_INDEX, 0)
    }

    private fun initFrame() {
        if (treeBean == null) {
            finish()
            return
        }
        initActionBar()
        initTabs()
    }

    private fun initTabs() {
        var tabs: List<TreeBean> = treeBean!!.children!!
        for (child in tabs) {
            val fragment = KnowledgeListFragment()
            val bundle = Bundle()
            bundle.putSerializable(KnowledgeListFragment.INTENT_TAG_JAVA_BEAN_TREE_BEAN, child)
            fragment.arguments = bundle
            fragments.add(fragment)
        }
        viewPager.run {
            adapter = viewPagerAdapter
            offscreenPageLimit = fragments.size
            setCurrentItem(initIndex, false)
        }
        tabLayout.run {
            tabMode = TabLayout.MODE_SCROLLABLE
            setBackgroundResource(getThemeColor(this@KnowledgeTreeDetailActivity))
            setupWithViewPager(viewPager)
        }

        for (value in tabs) tabLayout.getTabAt(tabs.indexOf(value))!!.text = value.name
    }

    private fun initActionBar() {
        toolbar.run {
            toolbar.title = treeBean?.name
            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
        }
    }

    fun getThemeColor(activity: Activity): Int {
        var color: Int = WanAndroidApplication.instance!!.getThemeColor(activity, WanAndroidApplication.instance!!.getTheme(activity)!!)
        return color
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, 0, 0, "添加到桌面")!!.setIcon(R.drawable.icon_home).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        } else if (item!!.itemId == 0) {
            addShortcut(this)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addShortcut(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            val sIntent = Intent(Intent.ACTION_MAIN)
            sIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            sIntent.setClass(activity, SplashActivity::class.java)
            sIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            sIntent.action = "android.intent.action.SINGLE_INSTANCE_SHARE"
            sIntent.putExtra("SHORT_CUT", true)
            var beanJson = Gson().toJson(treeBean)
            SharedPreferencesUtils.putString(treeBean!!.name!!, beanJson)
            sIntent.putExtra(INTENT_TAG_JAVABEAN_TREE_BEAN, treeBean!!.name!!)
            sIntent.putExtra(INTENT_TAG_INT_INDEX, initIndex)

            val installer = Intent()
            installer.putExtra("duplicate", false)
            installer.putExtra("android.intent.extra.shortcut.INTENT", sIntent)
            installer.putExtra("android.intent.extra.shortcut.NAME", treeBean!!.name)
            installer.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(activity, R.drawable.ic_launcher))
            installer.action = "com.android.launcher.action.INSTALL_SHORTCUT"
            activity.sendBroadcast(installer)
        } else {
            val mShortcutManager = getSystemService(ShortcutManager::class.java)
            val shortCuts = mShortcutManager.dynamicShortcuts
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.setClass(activity, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.action = "android.intent.action.SINGLE_INSTANCE_SHARE"
            intent.putExtra("SHORT_CUT", true)
            val beanJson = Gson().toJson(treeBean)
            SharedPreferencesUtils.putString(treeBean!!.name!!, beanJson)
            intent.putExtra(INTENT_TAG_JAVABEAN_TREE_BEAN, treeBean!!.name!!)
            intent.putExtra(INTENT_TAG_INT_INDEX, initIndex)

            val info = ShortcutInfo.Builder(this, treeBean!!.name!!)
                    .setShortLabel(treeBean!!.name!!)
                    .setIcon(Icon.createWithResource(this, R.drawable.ic_launcher))
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

    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.classLoader = classLoader
        super.onSaveInstanceState(outState)
    }
}





