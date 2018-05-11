package com.mg.axechen.wanandroid.block.collect.website

import com.mg.axechen.wanandroid.block.collect.base.BaseCollectPresenter
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectView
import com.mg.axechen.wanandroid.javabean.SearchTag
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import network.schedules.BaseSchedulerProvider

/**
 * Created by AxeChen on 2018/4/20.
 */
class CollectWebsitePresenter(collectWebView: CollectWebsiteContract.View,view: BaseCollectView, scheduler: BaseSchedulerProvider)
    : CollectWebsiteContract.Presenter, BaseCollectPresenter(view, scheduler) {

    private var view: CollectWebsiteContract.View? = null

    private var scheduler: BaseSchedulerProvider? = null

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val model: CollectWebsiteContract.Model by lazy {
        CollectWebsiteModel()
    }

    init {
        this.view = collectWebView
        this.scheduler = scheduler
    }

    override fun getCollectWebsite() {
        var disposable = model.getCollectWebsite()
                .compose(ResponseTransformer.handleResult())
                .compose(scheduler?.applySchedulers())
                .subscribe(
                        { t: MutableList<SearchTag> ->
                            view?.getCollectWebSiteSuccess(t)
                        }, { t: Throwable ->
                    view?.getCollectWebSiteFail(t.message!!)
                }
                )

        compositeDisposable.add(disposable)
    }

    override fun unSubscribe() {
        compositeDisposable.dispose()
    }

}