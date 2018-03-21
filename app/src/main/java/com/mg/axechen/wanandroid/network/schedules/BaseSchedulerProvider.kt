package network.schedules

import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler

/**
 * Created by AxeChen on 2018/3/19.
 * 线程切换拓展类
 */
interface BaseSchedulerProvider {
    open fun computation(): Scheduler
    open fun io() : Scheduler
    open fun ui(): Scheduler
    open fun <T> applySchedulers(): ObservableTransformer<T, T>
}