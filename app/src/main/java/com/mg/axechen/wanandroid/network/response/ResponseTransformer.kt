package com.mg.axechen.wanandroid.network.response

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function
import network.exception.ApiException
import network.exception.CustomException
import network.response.Response
import org.json.JSONObject

/**
 * Created by AxeChen on 2018/3/20.
 * rxjava2变换封装，分离数据和异常
 */
class ResponseTransformer {

    companion object {
        fun <T> handleResult(): ObservableTransformer<Response<T>, T> {
            return MyObservableTransformer()
        }
    }

    class MyObservableTransformer<T> : ObservableTransformer<Response<T>, T> {
        override fun apply(upstream: Observable<Response<T>>): ObservableSource<T> {
            return upstream.
                    onErrorResumeNext(ErrorResumeFunction())
                    .flatMap(ResponseFunction())
        }
    }

    /**
     * 非服务器产生的异常，比如解析错误，网络连接错误
     */
    class ErrorResumeFunction<T> : Function<Throwable, ObservableSource<out Response<T>>> {
        override fun apply(t: Throwable): ObservableSource<out Response<T>> {
            return Observable.error(CustomException.handleException(t))
        }
    }

    /**
     * 服务器产生的错误：HTTP错误代码
     */
    class ResponseFunction<T> : Function<Response<T>, ObservableSource<T>> {
        override fun apply(t: Response<T>): ObservableSource<T> {
            val code: Int = t.errorCode
            return if (code >= 0) {
                Observable.just(t.data)
            } else {
                Observable.error(ApiException(t.errorMsg, null, code))
            }
        }

    }

}