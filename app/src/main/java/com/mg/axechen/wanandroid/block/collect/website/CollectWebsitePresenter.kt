package com.mg.axechen.wanandroid.block.collect.website

import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.SearchTag
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import network.schedules.BaseSchedulerProvider

/**
 * Created by AxeChen on 2018/4/20.
 */
class CollectWebsitePresenter(view: CollectWebsiteContract.View, scheduler: BaseSchedulerProvider) : CollectWebsiteContract.Presenter {

    private var view: CollectWebsiteContract.View? = null

    private var scheduler: BaseSchedulerProvider? = null

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val model: CollectWebsiteContract.Model by lazy {
        CollectWebsiteModel()
    }

    init {
        this.view = view
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