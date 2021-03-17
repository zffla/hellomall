package com.example.malllibrary.net.callback

/**
 * 请求开始及结束时调用的方法
 */
interface IRequest {
    fun onRequestStart()
    fun onRequestEnd()
}