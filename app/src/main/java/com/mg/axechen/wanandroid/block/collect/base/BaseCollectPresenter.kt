package com.mg.axechen.wanandroid.block.collect.base

import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import network.schedules.BaseSchedulerProvider

/**
 * Created by AxeChen on 2018/5/3.
 */
open class BaseCollectPresenter(view: BaseCollectView, schedulerProvider: BaseSchedulerProvider) : BaseCollectContract.Presenter {

    override fun unSubscribe() {
        compositeDisposable.dispose()
    }

    private var view: BaseCollectView? = null

    private var schedulerProvider: BaseSchedulerProvider? = null

    init {
        this.view = view
        this.schedulerProvider = schedulerProvider
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    open override fun collectOutArticle(title: String, author: String, link: String) {
        var disposable = model.collectOutArticle(title, author, link)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider?.applySchedulers())
                .subscribe({
                    view?.collectOutArticleSuccess()

                }, {
                    view?.collectOutArticleFail()
                })
        compositeDisposable.add(disposable)
    }

    override fun unCollectArticle(id: Int) {
        var disposable = model.unCollectArticle(id)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider?.applySchedulers())
                .subscribe(
                        {
                            view?.unCollectArticleSuccess()
                        }
                        ,
                        {
                            view?.unCollectArticleFail()
                        }
                )
        compositeDisposable.add(disposable)
    }

    override fun collectWebsite(name: String, link: String) {
        var disposable = model.collectWebsite(name, link)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider?.applySchedulers())
                .subscribe({
                    view?.collectWebsiteSuccess()
                },
                        {
                            view?.collectWebsiteFail()
                        })
        compositeDisposable.add(disposable)
    }

    override fun unCollectWebsite(id: Int) {
        var disposable = model.unCollectWebsite(id)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider?.applySchedulers())
                .subscribe(
                        {
                            view?.unCollectWebsiteSuccess()
                        }, {
                    view?.unCollectWebsiteFail()
                }
                )
        compositeDisposable.add(disposable)
    }

    override fun updateWebsite(id: String, name: String, link: String) {
        var disposable = model.updateWebsite(id, name, link)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider?.applySchedulers())
                .subscribe(
                        {
                            view?.updateWebsiteSuccess()
                        }, {
                    view?.updateWebsiteFail()
                }
                )
        compositeDisposable.add(disposable)
    }

    override fun collectInArticle(id: Int) {
        var disposable = model.collectInArticle(id)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider?.applySchedulers())
                .subscribe(
                        {
                            view?.collectInArticleSuccess()
                        }, { t: Throwable ->
                    view?.collectInArticleFail()
                }
                )
        compositeDisposable.add(disposable)
    }

    private val model: BaseCollectModel by lazy {
        BaseCollectModel()
    }
}