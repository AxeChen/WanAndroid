package com.mg.axechen.wanandroid.block.main.home

import android.view.View
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import network.schedules.BaseSchedulerProvider
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/3/23.
 */
class HomePresenter(scheduler: BaseSchedulerProvider, view: HomeContract.View) : HomeContract.Presenter {

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
                            view?.getHomeListSuccess(listBean,isRefresh)
                            page++
                        },
                        { t ->
                            view?.getHomeListFail(t.message!!)
                        }
                )
        compositeDisposable.add(disposable)

    }

    override fun unSubscribe() {
        compositeDisposable.dispose()
    }

}