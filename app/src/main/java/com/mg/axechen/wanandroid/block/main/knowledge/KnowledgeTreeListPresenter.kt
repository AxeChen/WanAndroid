package com.mg.axechen.wanandroid.block.main.knowledge

import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import network.schedules.BaseSchedulerProvider
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/3/31.
 */
class KnowledgeTreeListPresenter(schedulerProvider: BaseSchedulerProvider, view: KnowledgeTreeListContract.View) : KnowledgeTreeListContract.Presenter {

    var view: KnowledgeTreeListContract.View? = null

    var scheduler: BaseSchedulerProvider? = null

    init {
        this.view = view
        this.scheduler = schedulerProvider
    }

    private val model: KnowledgeTreeListContract.Mode by lazy {
        KnowlegeTreeListMode()
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun getKnowledgeTree() {
        var disposable: Disposable = model.getKnowledgeTree().compose(ResponseTransformer.handleResult())
                .compose(scheduler!!.applySchedulers())
                .subscribe({ t ->
                    view!!.getTreeSuccess(t)
                }, { t ->
                    view!!.getTreeFail()
                })
        compositeDisposable.add(disposable)

    }

    override fun unSubscribe() {
        compositeDisposable.dispose()
    }

}