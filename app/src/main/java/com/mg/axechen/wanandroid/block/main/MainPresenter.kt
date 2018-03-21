package com.mg.axechen.wanandroid.block.main

import android.util.Log
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import network.schedules.BaseSchedulerProvider

/**
 * Created by AxeChen on 2018/3/19.
 * 主页Presenter
 */
class MainPresenter(model: MainModel, view: MainContract.View, schedulerProvider: BaseSchedulerProvider) : MainContract.Presenter {

    var model: MainModel? = null

    var view: MainContract.View? = null

    var scheduler: BaseSchedulerProvider? = null

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    init {
        this.model = model
        this.view = view
        this.scheduler = schedulerProvider
    }

    override fun getBannerData() {
        var compose: Disposable? = model?.getBannerData()?.
                compose(scheduler?.applySchedulers())?.
                compose(ResponseTransformer.handleResult())?.
                subscribe(
                        { t ->
                            view?.showBanner(t)
                        },
                        { t ->
                            Log.i("error", t.message.toString())
                            view?.getBannerFail(t.message!!)
                        }
                )

        disposable.add(compose!!)
    }

    override fun unSubscribe() {
        disposable.dispose()
    }


}