package com.example.malllibrary.net

import android.content.Context
import com.example.malllibrary.net.callback.*
import com.example.malllibrary.ui.loader.LoaderStyles
import com.example.malllibrary.ui.loader.MallLoader
import retrofit2.Call
import retrofit2.Callback
import java.util.*


/**
 * 在所有依赖mall-library的类中对外暴露直接使用的客户端
 * 建造者模式构建
 */
class RestClient internal constructor(
    private val url: String?,
    private val params: WeakHashMap<String, Any>?,
    private val request: IRequest?,
    private val success: ISuccess?,
    private val error: IError?,
    private val failure: IFailure?,
    private val complete: IComplete?,
    private val context: Context?,
    private val style: LoaderStyles?
) {
    companion object {
        fun builder(): RestClientBuilder {
            return RestClientBuilder()
        }
    }

    private fun request(method: HttpMethod) {
        val service = RestCreator.restService
        val call: Call<String>?
        request?.onRequestStart()
        if(style!=null){
            MallLoader.showLoading(context,style)
        }
        call = when (method) {
            HttpMethod.GET -> service.get(url, params)
            HttpMethod.POST -> service.post(url, params)
            HttpMethod.PUT -> service.put(url, params)
            HttpMethod.DELETE -> service.delete(url, params)
            HttpMethod.UPLOAD -> TODO()
            HttpMethod.DOWNLOAD -> TODO()
        }
        call.enqueue(callback)
    }

    private val callback: Callback<String>
        get() = RequestCallbacks(request, success, error, failure, complete,style)

    fun get() {
        request(HttpMethod.GET)
    }

    fun post() {
        request(HttpMethod.POST)
    }

    fun put() {
        request(HttpMethod.PUT)
    }

    fun delete() {
        request(HttpMethod.DELETE)
    }

}