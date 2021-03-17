package com.example.malllibrary.net.callback

/**
 * 请求失败时调用
 */
interface IError {
    fun onError(code:Int,msg:String)
}