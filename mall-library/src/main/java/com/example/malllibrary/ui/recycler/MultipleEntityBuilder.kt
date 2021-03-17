package com.example.malllibrary.ui.recycler

class MultipleEntityBuilder {

    companion object {
        val FIELDS = LinkedHashMap<Any, Any>()
    }

    init {
        FIELDS.clear()
    }

    fun setItemType(type: Int): MultipleEntityBuilder {
        FIELDS[MultipleFields.ITEM_TYPE] = type
        return this
    }

    fun setField(key: Any, value: Any?): MultipleEntityBuilder {
        if (value != null) {
            FIELDS[key] = value
        }
        return this
    }

    fun setFields(fields: LinkedHashMap<Any, Any>): MultipleEntityBuilder {
        FIELDS.putAll(fields)
        return this
    }

    fun build(): MultipleItemEntity {
        return MultipleItemEntity(FIELDS)
    }
}