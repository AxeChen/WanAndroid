package com.mg.axechen.wanandroid.block.collect.base

import android.widget.Toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseFragment

/**
 * Created by AxeChen on 2018/5/4.
 */
open class BaseCollectFragment : BaseFragment(), BaseCollectView {
    override fun collectInArticleSuccess() {
        Toast.makeText(activity, getString(R.string.collect_success), Toast.LENGTH_SHORT).show()
    }

    override fun collectInArticleFail() {
        Toast.makeText(activity, getString(R.string.collect_fail), Toast.LENGTH_SHORT).show()
    }

    override fun collectOutArticleSuccess() {
    }

    override fun collectOutArticleFail() {
    }

    override fun unCollectArticleSuccess() {
        Toast.makeText(activity, getString(R.string.un_collect_success), Toast.LENGTH_SHORT).show()
    }

    override fun unCollectArticleFail() {
        Toast.makeText(activity, getString(R.string.un_collect_fail), Toast.LENGTH_SHORT).show()
    }

    override fun collectWebsiteSuccess() {
    }

    override fun collectWebsiteFail() {
    }

    override fun unCollectWebsiteSuccess() {
    }

    override fun unCollectWebsiteFail() {
    }

    override fun updateWebsiteSuccess() {
    }

    override fun updateWebsiteFail() {
    }

}