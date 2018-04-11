package com.mg.axechen.wanandroid.block.search

import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import network.schedules.BaseSchedulerProvider

/**
 * Created by AxeChen on 2018/4/10.
 * 搜索Presenter
 */
class SearchPresenter(view: SearchContract.View, schedulerProvider: BaseSchedulerProvider) : SearchContract.Presenter {

    private var view: SearchContract.View? = null
    private var scheduler: BaseSchedulerProvider? = null
    private var page: Int = 0

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    init {
        this.view = view
        this.scheduler = schedulerProvider
    }

    private val model: SearchModel by lazy {
        SearchModel()
    }

    override fun search(text: String, isRefresh: Boolean) {
        if (isRefresh) {
            page = 0
        }
        var disposable = model.search(page, text).compose(ResponseTransformer.handleResult())
                .compose(scheduler?.applySchedulers())
                .subscribe(
                        { t ->
                            if (t.size * (page + 1) >= t.total) {
                                view?.loadAllResult(t, isRefresh)
                            } else {
                                view?.getSearchResultSuccess(t, isRefresh)
                                page++
                            }

                        },
                        { t: Throwable -> view?.getSearchResultFail(t.message!!) }
                )
        compositeDisposable.add(disposable)
    }

    override fun getSearchTag() {
        var disposable: Disposable = model.getSearchTag().compose(ResponseTransformer.handleResult())
                .compose(scheduler?.applySchedulers())
                .subscribe(
                        { t -> view?.getSearchTagSuccess(t) },
                        { t -> view?.getSearchTagFail(t.message!!) }
                )
        compositeDisposable.add(disposable)
    }

    override fun unSubscribe() {
        compositeDisposable.dispose()
    }

}