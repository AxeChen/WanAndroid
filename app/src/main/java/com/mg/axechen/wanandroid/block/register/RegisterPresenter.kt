package com.mg.axechen.wanandroid.block.register

import android.text.TextUtils
import com.mg.axechen.wanandroid.network.response.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import network.schedules.BaseSchedulerProvider

/**
 * Created by AxeChen on 2018/3/20.
 */
class RegisterPresenter(view: RegisterContract.View, schedulerProvider: BaseSchedulerProvider) : RegisterContract.Presenter {

    private val model: RegisterContract.Model by lazy {
        RegisterModel()
    }

    var view: RegisterContract.View? = null

    var scheduler: BaseSchedulerProvider? = null

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    init {
        this.view = view
        this.scheduler = schedulerProvider
    }


    override fun register(userName: String, password: String, rePassword: String) {
        if (TextUtils.isEmpty(userName)) {
            view?.userNameIsEmpty()
            return
        }

        if (TextUtils.isEmpty(password)) {
            view?.passwordIsEmpty()
            return
        }

        if (rePassword != password) {
            view?.rePasswordFault()
            return
        }

        var compose = model?.register(userName, password, rePassword)?.
                compose(scheduler?.applySchedulers())?.
                compose(ResponseTransformer.handleResult())?.subscribe(
                { t -> view?.registerSuccess() },
                { t -> view?.registerFail(t.message!!) }
        )
        disposable.add(compose!!)
    }

    override fun unSubscribe() {
        disposable.dispose()
    }

}