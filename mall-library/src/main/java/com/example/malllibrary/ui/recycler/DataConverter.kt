package com.example.malllibrary.ui.recycler

import java.lang.NullPointerException

/**
 * JSON数据转换
 */
abstract class DataConverter {
    protected val mEntities=ArrayList<MultipleItemEntity>()

    private lateinit var mJsonData:String

    protected val jsonData:String
        get() {
            if (mJsonData.isEmpty()){
                throw NullPointerException("Data is null!")
            }
            return mJsonData
        }

    abstract fun convert():ArrayList<MultipleItemEntity>

    fun setJsonData(data:String):DataConverter{
        mJsonData=data
        return this
    }

    fun clearData(){
        mEntities.clear()
    }
}