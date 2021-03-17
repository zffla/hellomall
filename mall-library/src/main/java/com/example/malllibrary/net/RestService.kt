package com.example.malllibrary.net

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*


/**
 * 描述网络请求的接口
 */
interface RestService {

    @GET
    fun get(
        @Url url: String?,
        @QueryMap params: WeakHashMap<String, Any>?
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun post(
        @Url url: String?,
        @FieldMap params: WeakHashMap<String, Any>?
    ): Call<String>

    @FormUrlEncoded
    @PUT
    fun put(
        @Url url: String?,
        @QueryMap params: WeakHashMap<String, Any>?
    ): Call<String>

    @DELETE
    fun delete(
        @Url url: String?,
        @QueryMap params: WeakHashMap<String, Any>?
    ): Call<String>

    //本质上是get请求，streaming表示不是一次性将文件下载到内存中，而是边下边写
    @Streaming
    @GET
    fun download(
        @Url url: String?,
        @QueryMap params: WeakHashMap<String, Any>?
    ): Call<ResponseBody>

    fun upload(
        @Url url: String?,
        @Part file: MultipartBody.Part?
    ): Call<String>
}