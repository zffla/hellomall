package com.example.malllibrary.net

import android.content.Context
import com.example.malllibrary.net.callback.*
import com.example.malllibrary.ui.loader.LoaderStyles
import java.util.*

/**
 * 构建RestClient并初始化参数和回调
 */
class RestClientBuilder(
    private var url: String? = null,
    private var request: IRequest? = null,
    private var success: ISuccess? = null,
    private var error: IError? = null,
    private var failure: IFailure? = null,
    private var complete: IComplete? = null,
    private var context: Context? = null,
    private var loadingStyle: LoaderStyles? = null
) {
    private val mParams = WeakHashMap<String, Any>()

    fun url(url: String): RestClientBuilder {
        this.url = url
        return this
    }

    fun params(key: String, value: Any): RestClientBuilder {
        mParams[key] = value
        return this
    }

    fun params(params: WeakHashMap<String, Any>): RestClientBuilder {
        mParams.putAll(params)
        return this
    }

    fun request(request: IRequest): RestClientBuilder {
        this.request = request
        return this
    }

    fun success(success: ISuccess): RestClientBuilder {
        this.success = success
        return this
    }

    fun failure(failure: IFailure): RestClientBuilder {
        this.failure = failure
        return this
    }

    fun error(error: IError): RestClientBuilder {
        this.error = error
        return this
    }

    fun complete(complete: IComplete): RestClientBuilder {
        this.complete = complete
        return this
    }

    fun loader(context: Context, style: LoaderStyles): RestClientBuilder {
        this.context = context
        this.loadingStyle = style
        return this
    }

    fun loader(context: Context): RestClientBuilder {
        this.context = context
        this.loadingStyle = LoaderStyles.BallClipRotateMultipleIndicator
        return this
    }

    fun build(): RestClient {
        return RestClient(
            url,
            mParams,
            request,
            success,
            error,
            failure,
            complete,
            context,
            loadingStyle
        )
    }
}