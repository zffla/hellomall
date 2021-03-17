package com.example.hellomall.fragments.sort.list

import com.alibaba.fastjson.JSON
import com.example.malllibrary.ui.recycler.DataConverter
import com.example.malllibrary.ui.recycler.ItemType
import com.example.malllibrary.ui.recycler.MultipleFields
import com.example.malllibrary.ui.recycler.MultipleItemEntity

class VerticalListDataConverter : DataConverter() {
    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataList = ArrayList<MultipleItemEntity>()
        val dataArray = JSON.parseObject(jsonData)
            .getJSONObject("data")
            .getJSONArray("list")
        val size = dataArray.size
        for (i in 0 until size) {
            val data = dataArray.getJSONObject(i)
            val id = data.getInteger("id")
            val name = data.getString("name")
            val entity = MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                .setField(MultipleFields.ID, id)
                .setField(MultipleFields.NAME, name)
                //标记是否被选中
                .setField(MultipleFields.TAG, false)
                .build()
            dataList.add(entity)
        }
        //默认选中第一个
        dataList[0].setField(MultipleFields.TAG, true)
        return dataList
    }
}