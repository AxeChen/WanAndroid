package com.mg.axechen.wanandroid.block.navi

import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import network.schedules.BaseSchedulerProvider

/**
 * Created by AxeChen on 2018/4/11.
 */
class NaviWebsitePresneter(view: NaviWebsiteContract.View, schedulerProvider: BaseSchedulerProvider) : NaviWebsiteContract.Presenter {

    private var view: NaviWebsiteContract.View? = null

    private var scheduler: BaseSchedulerProvider? = null

    private val model: NaviWebsiteContract.Model by lazy {
        NaviWebsiteModel()
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    init {
        this.view = view
        this.scheduler = schedulerProvider
    }

    override fun getWebsiteNavi() {

        var disapose: Disposable = model.getWebsiteNavi().compose(scheduler?.applySchedulers())
                .compose(ResponseTransformer.handleResult())
                .subscribe(
                        { t ->
                            view?.getNaviWebSiteSuccess(t)
                        }
                        ,
                        { t: Throwable ->
                            view?.getNaiWebSiteFail(t.message!!)
                        })
        compositeDisposable.add(disapose)
    }


    override fun unSubscribe() {
        compositeDisposable.dispose()
    }

}