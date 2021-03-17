package com.example.malllibrary.fragments.bottom

/**
 * 添加fragment页面的构造器
 */
class ItemBuilder {

    //LinkedHashMap有序，用来存储页面
    private val mItems=LinkedHashMap<BottomTabBean,BottomItemFragment>()

    companion object{
        internal fun builder():ItemBuilder{
            return ItemBuilder()
        }
    }

    fun addItem(bean: BottomTabBean,item:BottomItemFragment):ItemBuilder{
        mItems[bean]=item
        return this
    }

    fun addItems(items:LinkedHashMap<BottomTabBean,BottomItemFragment>):ItemBuilder{
        mItems.putAll(items)
        return this
    }

    fun build():LinkedHashMap<BottomTabBean,BottomItemFragment>{
        return mItems
    }
}