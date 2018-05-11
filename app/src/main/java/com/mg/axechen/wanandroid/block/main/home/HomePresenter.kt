package com.mg.axechen.wanandroid.block.main.home

import android.util.Log
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectPresenter
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectView
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import network.schedules.BaseSchedulerProvider

/**
 * Created by AxeChen on 2018/3/23.
 */
class HomePresenter(scheduler: BaseSchedulerProvider, view: HomeContract.View, collectView: BaseCollectView) :
        HomeContract.Presenter, BaseCollectPresenter(collectView, scheduler) {

    private var page: Int = 0

    private var view: HomeContract.View? = null

    private var scheduler: BaseSchedulerProvider? = null

    private val model: HomeModel by lazy {
        HomeModel()
    }

    init {
        this.view = view
        this.scheduler = scheduler
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun getHomeList(isRefresh: Boolean) {
        if (isRefresh) {
            page = 0
        }
        var disposable: Disposable = model.getHomeList(page).compose(ResponseTransformer.handleResult())
                .compose(scheduler?.applySchedulers())
                .subscribe(
                        { listBean ->
                            view?.getHomeListSuccess(listBean, isRefresh)
                            page++
                        },
                        { t ->
                            view?.getHomeListFail(t.message!!, isRefresh)
                        }
                )
        compositeDisposable.add(disposable)
    }

    override fun getBannerData(isRefresh: Boolean) {
        var compose: Disposable? = model?.getBannerData()?.
                compose(scheduler?.applySchedulers())?.
                compose(ResponseTransformer.handleResult())?.
                subscribe(
                        { t ->
                            view?.showBanner(t, isRefresh)
                        },
                        { t ->
                            Log.i("error", t.message.toString())
                            view?.getBannerFail(t.message!!, isRefresh)
                        }
                )
        compositeDisposable.add(compose!!)
    }

    override fun unSubscribe() {
        compositeDisposable.dispose()
    }


}