package com.example.malllibrary.util

class MemoryStorage{
    /**
     * 线程安全的单例类
     */
    private object Holder{
        internal val INSTANCE=MemoryStorage()
    }

    companion object{
        val instance:MemoryStorage
            get() = Holder.INSTANCE
    }

    private val mDataMap=HashMap<String,Any> ()

    fun <T> getData(key:String):T{
        @Suppress("UNCHECKED_CAST")
        return mDataMap[key] as T
    }

    fun addData(key:String,value:Any):MemoryStorage{
        mDataMap[key]=value
        return this
    }

    fun <T> getData(key: Enum<*>):T{
        return getData(key.name)
    }

    fun addData(key:Enum<*>,value: Any):MemoryStorage{
        mDataMap[key.name]=value
        return this
    }
}