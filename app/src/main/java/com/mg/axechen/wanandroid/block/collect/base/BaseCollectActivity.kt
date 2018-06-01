package com.mg.axechen.wanandroid.block.collect.base

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity

/**
 * Created by AxeChen on 2018/5/4.
 */
open class BaseCollectActivity : BaseActivity(), BaseCollectView {
    override fun setLayoutId(): Int {
        return 0
    }

    override fun collectInArticleSuccess() {
        Toast.makeText(this, getString(R.string.collect_success), Toast.LENGTH_SHORT).show()
    }

    override fun collectInArticleFail() {
        Toast.makeText(this, getString(R.string.collect_fail), Toast.LENGTH_SHORT).show()
    }


    override fun collectOutArticleSuccess() {
        Toast.makeText(this, getString(R.string.collect_success), Toast.LENGTH_SHORT).show()
    }

    override fun collectOutArticleFail() {
        Toast.makeText(this, getString(R.string.collect_fail), Toast.LENGTH_SHORT).show()
    }

    override fun unCollectArticleSuccess() {
        Toast.makeText(this, getString(R.string.un_collect_success), Toast.LENGTH_SHORT).show()
    }

    override fun unCollectArticleFail() {
        Toast.makeText(this, getString(R.string.un_collect_fail), Toast.LENGTH_SHORT).show()
    }

    override fun collectWebsiteSuccess() {
        Toast.makeText(this, getString(R.string.collect_success), Toast.LENGTH_SHORT).show()
    }

    override fun collectWebsiteFail() {
        Toast.makeText(this, getString(R.string.collect_fail), Toast.LENGTH_SHORT).show()
    }

    override fun unCollectWebsiteSuccess() {
        Toast.makeText(this, getString(R.string.un_collect_success), Toast.LENGTH_SHORT).show()
    }

    override fun unCollectWebsiteFail() {
        Toast.makeText(this, getString(R.string.un_collect_fail), Toast.LENGTH_SHORT).show()
    }

    override fun updateWebsiteSuccess() {
    }

    override fun updateWebsiteFail() {
    }

}