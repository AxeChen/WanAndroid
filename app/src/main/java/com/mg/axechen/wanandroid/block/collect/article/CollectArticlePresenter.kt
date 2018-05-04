package com.mg.axechen.wanandroid.block.collect.article

import com.mg.axechen.wanandroid.block.collect.base.BaseCollectPresenter
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectView
import com.mg.axechen.wanandroid.javabean.ProjectListBean
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import network.schedules.BaseSchedulerProvider

/**
 * Created by AxeChen on 2018/4/20.
 */
class CollectArticlePresenter(schedulerProvider: BaseSchedulerProvider, view: BaseCollectView,collectView: CollectArticleContract.View) : CollectArticleContract.Presenter,
        BaseCollectPresenter(view, schedulerProvider) {

    var page = 0

    var scheduler: BaseSchedulerProvider? = null

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val model: CollectArticleModel? by lazy {
        CollectArticleModel()
    }

    var view: CollectArticleContract.View? = null

    init {
        this.scheduler = schedulerProvider
        this.view = collectView
    }

    override fun getCollectArticleList() {
        var dispose = model?.getCollectArticleList(page)
                ?.compose(ResponseTransformer.handleResult())
                ?.compose(scheduler?.applySchedulers())
                ?.subscribe(
                        { t: ProjectListBean ->
                            view?.getCollectArticleListSuccess(t)
                            page++
                        },
                        { t ->
                            view?.getCollectArticleListFail(t.message!!)
                        })
        compositeDisposable.add(dispose!!)
    }

    override fun unSubscribe() {
        compositeDisposable.dispose()
    }

}