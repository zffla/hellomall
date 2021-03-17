package com.example.hellomall.fragments.cart

import com.alibaba.fastjson.JSON
import com.example.hellomall.R
import com.example.malllibrary.ui.recycler.DataConverter
import com.example.malllibrary.ui.recycler.MultipleFields
import com.example.malllibrary.ui.recycler.MultipleItemEntity
import com.mall.example.fragments.cart.ShopCartItemFields

class ShopCartDataConverter : DataConverter() {
    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataList = ArrayList<MultipleItemEntity>()
        val dataArray = JSON.parseObject(jsonData)
            .getJSONArray("data")
        val size = dataArray.size
        for (i in 0 until size) {
            /*"title": "小米6 6GB+128GB",
            "desc": "变焦双摄，4 轴防抖 骁龙835",
            "id": 1,
            "price": 2899,
            "count": 1,
            "thumb": "https://i8.mifile.cn/b2c-mimall-media/f0e283f74845939fd24d53f7fb1d35c1.png?width=120&height=120"*/
            val data = dataArray.getJSONObject(i)
            val title=data.getString("title")
            val desc=data.getString("desc")
            val id=data.getInteger("id")
            val price=data.getDouble("price")
            val count=data.getInteger("count")
            val thumb=data.getString("thumb")
            val entity = MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, R.id.id_shop_cart)
                .setField(MultipleFields.ID,id)
                .setField(MultipleFields.IMAGE_URL,thumb)
                .setField(ShopCartItemFields.TITLE,title)
                .setField(ShopCartItemFields.COUNT,count)
                .setField(ShopCartItemFields.PRICE,price)
                .setField(ShopCartItemFields.DESC,desc)
                .setField(ShopCartItemFields.IS_SELECTED,false)
                .setField(ShopCartItemFields.POSITION,i)
                .build()
            dataList.add(entity)
        }
        return dataList
    }
}