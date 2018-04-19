package com.mg.axechen.wanandroid.block.navi

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.block.details.WebViewActivity
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.NaviBean
import kotlinx.android.synthetic.main.activity_navi.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/4/11.
 */
class NaviWebsiteActivity : BaseActivity(), NaviWebsiteContract.View {

    companion object {
        fun lunch(context: Context) {
            context.startActivity(Intent(context, NaviWebsiteActivity::class.java))
        }
    }

    private var datas = mutableListOf<NaviBean>()

    private val kindsAdapter: NaviKindAdapter by lazy {
        NaviKindAdapter(R.layout.item_nav_web_kind, datas)
    }

    private val naviContentAdapter: NaviContentAdapter by lazy {
        NaviContentAdapter(R.layout.item_web_navi, datas, this)
    }

    private val presenter: NaviWebsiteContract.Presenter by lazy {
        NaviWebsitePresneter(this, SchedulerProvider.getInstatnce()!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        drawerLayout.setScrimColor(Color.TRANSPARENT)
        initToolbar()
        initKindsAdapter()
        initContentAdapter()
        presenter.getWebsiteNavi()
    }

    private fun initToolbar() {
        toolbar.run {
            title = "网址导航"
            setSupportActionBar(toolbar)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initContentAdapter() {
        rvList.run {
            layoutManager = LinearLayoutManager(this@NaviWebsiteActivity)
            adapter = naviContentAdapter
        }
        naviContentAdapter.itemClickLister = object : NaviContentAdapter.ItemClickListener {
            override fun itemClick(data: HomeData) {
                WebViewActivity.lunch(this@NaviWebsiteActivity, data.link!!, data.title!!)
            }
        }
    }

    private fun initKindsAdapter() {
        rvNavList.run {
            layoutManager = LinearLayoutManager(this@NaviWebsiteActivity)
            adapter = kindsAdapter
        }
        kindsAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            if (position !== -1) {
                rvList.scrollToPosition(position)
                val mLayoutManager = rvList.layoutManager as LinearLayoutManager
                mLayoutManager.scrollToPositionWithOffset(position, 0)
            }
        }
    }

    override fun getNaviWebSiteSuccess(beans: MutableList<NaviBean>) {
        datas.clear()
        datas.addAll(beans)
        kindsAdapter.notifyDataSetChanged()
        naviContentAdapter.notifyDataSetChanged()
    }

    override fun getNaiWebSiteFail(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_navi
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navi_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        } else if (item?.itemId == R.id.actionMenu) {
            changeRightPage()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeRightPage() {
        if (drawerLayout.isDrawerOpen(flRight)) {
            drawerLayout.closeDrawer(flRight)
        } else {
            drawerLayout.openDrawer(flRight)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unSubscribe()
    }

}