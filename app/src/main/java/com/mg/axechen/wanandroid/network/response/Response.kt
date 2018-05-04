package network.response

/**
 * Created by AxeChen on 2018/3/19.
 * 返回参数封装
 */
class Response<T> {

    var data: T? = null

    var errorCode: Int = 0

    var errorMsg: String = ""

    constructor(data: T?, errorCode: Int, errorMsg: String) {
        this.data = data
        this.errorCode = errorCode
        this.errorMsg = errorMsg
    }
}