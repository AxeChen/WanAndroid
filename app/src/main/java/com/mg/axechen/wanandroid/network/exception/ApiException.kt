package network.exception


/**
 * Created by AxeChen on 2018/3/19.
 * 异常处理
 */
class ApiException : Exception {

    private var code: Int = 0

    constructor(message: String?, cause: Throwable?, code: Int) : super(message, cause) {
        this.code = code
    }
}