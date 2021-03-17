package com.example.malllibrary.ui.recycler

import com.chad.library.adapter.base.entity.MultiItemEntity

class MultipleItemEntity constructor(fields: LinkedHashMap<Any, Any>) : MultiItemEntity {

    private val mFields = LinkedHashMap<Any, Any>()

    companion object{
        fun builder():MultipleEntityBuilder{
            return MultipleEntityBuilder()
        }
    }

    init {
        mFields.putAll(fields)
    }

    override val itemType: Int
        get() = mFields[MultipleFields.ITEM_TYPE] as Int

    @Suppress("UNCHECKED_CAST")
    fun <T> getField(key: Any): T {
        return mFields[key] as T
    }

    fun setField(key: Any, value: Any): MultipleItemEntity {
        mFields[key] = value
        return this
    }



}