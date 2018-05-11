package com.mg.axechen.wanandroid.block.main.project

import com.mg.axechen.wanandroid.block.collect.base.BaseCollectPresenter
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectView
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import network.schedules.BaseSchedulerProvider

/**
 * Created by AxeChen on 2018/4/2.
 */
class ProjectListPresenter(collectView: BaseCollectView,scheduler: BaseSchedulerProvider, view: ProjectListContract.View)
    :BaseCollectPresenter(collectView,scheduler), ProjectListContract.Presenter {

    private var view: ProjectListContract.View? = null

    private var scheduler: BaseSchedulerProvider? = null

    private var page: Int = 0

    init {
        this.scheduler = scheduler
        this.view = view
    }

    private val model: ProjectListContract.Model by lazy {
        ProjectListMode()
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun getProjectTreeList(cid: Int, isRefresh: Boolean) {
        if (isRefresh) {
            page = 0
        }
        var disposable: Disposable = model.getProjectListByCid(page, cid)
                .compose(ResponseTransformer.handleResult())
                .compose(scheduler?.applySchedulers())
                .subscribe(
                        { t ->
                            view?.getProjectListByCidSuccess(t, isRefresh)
                            page++
                        },
                        { t -> view?.getProjectListByCidFail(t.message!!) }
                )
        compositeDisposable.add(disposable)
    }

    override fun getProjectTree() {
        var disposable: Disposable = model.getProjectTree().compose(ResponseTransformer.handleResult())
                .compose(scheduler?.applySchedulers())
                .subscribe(
                        { t -> view?.getProjectTreeSuccess(t) },
                        { t -> view?.getProjectTreeFail(t.message!!) })
        compositeDisposable.add(disposable)
    }

    override fun unSubscribe() {
        compositeDisposable.dispose()
    }

}