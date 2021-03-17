package com.example.malllibrary.net.callback

import android.os.Handler
import com.example.malllibrary.global.GlobalKey
import com.example.malllibrary.global.Mall
import com.example.malllibrary.ui.loader.LoaderStyles
import com.example.malllibrary.ui.loader.MallLoader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * 请求后的回调
 */
class RequestCallbacks(
    private val request: IRequest?,
    private val success: ISuccess?,
    private val error: IError?,
    private val failure: IFailure?,
    private val complete: IComplete?,
    private val loaderStyle: LoaderStyles?
) : Callback<String>{

    override fun onFailure(call: Call<String>, t: Throwable) {
        failure?.onFailure()
        request?.onRequestEnd()
    }

    override fun onResponse(call: Call<String>, response: Response<String>) {
        if (response.isSuccessful) {
            if (call.isExecuted) {
                if (success != null) {
                    if (response.body() != null) {
                        success.onSuccess(response.body()!!)
                    }
                }
            }
        } else {
            error?.onError(response.code(), response.message())
        }
        onRequestFinish()
    }

    /**
     * 请求结束后调用
     */
    private fun onRequestFinish() {
//        val delay = Mall.getConfiguration<Long>(GlobalKey.LOADER_DELAY)
        if (loaderStyle!=null){
//            HANDLER.postDelayed({ MallLoader.stopLoading()},delay)
            HANDLER.post {MallLoader.stopLoading()}
        }
    }

    companion object {
        private val HANDLER = Mall.getConfiguration<Handler>(GlobalKey.HANDLER)
    }

}