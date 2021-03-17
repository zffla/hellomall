package com.example.malllibrary.net

import com.example.malllibrary.global.GlobalKey
import com.example.malllibrary.global.Mall
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 创建Retrofit的一系列实例，初始化
 */
object RestCreator {

    private object OkHttpHolder {
        private const val TIME_OUT = 60
        private val BUILDER = OkHttpClient.Builder()
        internal val OK_HTTP_CLIENT = BUILDER
            .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        .build()
    }

    /**
     * 创建Retrofit实例
     */
    private object RetrofitHolder {
        private val BASE_URL = Mall.getConfiguration<String>(GlobalKey.API_HOST)
        internal val RETROFIT_CLIENT = Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .baseUrl("http://mock.fulingjie.com/mock/api/")
            .client(OkHttpHolder.OK_HTTP_CLIENT)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    /**
     * 创建网络请求实例
     */
    private object RestServiceHolder {
        internal val REST_SERVICE = RetrofitHolder
            .RETROFIT_CLIENT
            .create(RestService::class.java)
    }

    val restService: RestService
        get() =
            RestServiceHolder.REST_SERVICE


}