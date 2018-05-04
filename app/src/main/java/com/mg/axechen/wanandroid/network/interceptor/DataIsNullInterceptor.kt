package com.mg.axechen.wanandroid.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.Okio
import org.json.JSONObject
import java.io.EOFException
import java.nio.charset.Charset


/**
 * Created by AxeChen on 2018/5/4.
 */
class DataIsNullInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain?.request()
        val response = chain?.proceed(request)
        var jsonObject = JSONObject(readResponseStr(response!!))
        // 这就就是code == 0 data == null的情况
        if ((jsonObject.optInt("errorCode") == 0) && (jsonObject.optJSONObject("data")==null) ) {
        }
        return response
    }


    /**
     * 读取Response返回String内容
     * @param response
     * @return
     */
    private fun readResponseStr(response: Response): String? {
        val body = response.body()
        val source = body!!.source()
        try {
            source.request(java.lang.Long.MAX_VALUE)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        val contentType = body.contentType()
        var charset = Charset.forName("UTF-8")
        if (contentType != null) {
            charset = contentType.charset(charset)
        }
        var s: String? = null
        val buffer = source.buffer()
        if (isPlaintext(buffer)) {
            s = buffer.clone().readString(charset)
        }
        return s
    }

    fun isPlaintext(buffer: Buffer): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = (if (buffer.size() < 64) buffer.size() else 64).toLong()
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (e: EOFException) {
            return false // Truncated UTF-8 sequence.
        }

    }
}