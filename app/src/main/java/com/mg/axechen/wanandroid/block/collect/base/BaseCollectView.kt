package com.mg.axechen.wanandroid.block.collect.base

/**
 * Created by AxeChen on 2018/5/3.
 */
interface BaseCollectView {
    // 收藏站内文章
    fun collectInArticleSuccess()

    fun collectInArticleFail()

    // 收藏站外文章
    fun collectOutArticleSuccess()

    fun collectOutArticleFail()

    // 取消收藏文章
    fun unCollectArticleSuccess()

    fun unCollectArticleFail()

    // 收藏网站
    fun collectWebsiteSuccess()

    fun collectWebsiteFail()

    // 取消网站收藏
    fun unCollectWebsiteSuccess()

    fun unCollectWebsiteFail()

    // 修改网站信息
    fun updateWebsiteSuccess()

    fun updateWebsiteFail()
}