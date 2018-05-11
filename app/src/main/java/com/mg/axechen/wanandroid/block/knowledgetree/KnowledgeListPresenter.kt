package com.mg.axechen.wanandroid.block.knowledgetree

import com.mg.axechen.wanandroid.block.collect.base.BaseCollectPresenter
import com.mg.axechen.wanandroid.block.collect.base.BaseCollectView
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import network.schedules.BaseSchedulerProvider

/**
 * Created by AxeChen on 2018/4/8.
 */
class KnowledgeListPresenter(collectView:BaseCollectView,view: KnowledgeListContract.View, schedulerProvider: BaseSchedulerProvider)
    :BaseCollectPresenter(collectView,schedulerProvider), KnowledgeListContract.Presenter {

    var view: KnowledgeListContract.View? = null

    var scheduler: BaseSchedulerProvider? = null

    var page: Int = 0

    var isRequest: Boolean = false

    init {
        this.view = view
        this.scheduler = schedulerProvider
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val model: KnowledgeListContract.Model by lazy {
        KnowledgeListModel()
    }

    override fun getKnowledgeList(cid: Int, isRefresh: Boolean) {

        if (isRequest) {
            return
        }
        isRequest = true

        if (isRefresh) {
            page = 0
        }
        var disposable: Disposable = model.getKnowledgeList(page, cid)
                .compose(ResponseTransformer.handleResult())
                .compose(scheduler?.applySchedulers())
                .subscribe(
                        { t ->
                            isRequest = false
                            if (t.size * (page + 1) >= t.total) {
                                view?.loadAllArticles(t, isRefresh)
                            } else {
                                view?.getKnowledgeSuccess(t, isRefresh)
                                page++
                            }
                        },
                        { t ->
                            isRequest = false
                            view?.getKnowledgeFail(t.message!!, isRefresh)
                        }
                )
        compositeDisposable.add(disposable)
    }

    override fun unSubscribe() {
        compositeDisposable.dispose()
    }

}