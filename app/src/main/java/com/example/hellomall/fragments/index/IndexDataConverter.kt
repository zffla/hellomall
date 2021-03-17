package com.example.hellomall.fragments.index

import com.alibaba.fastjson.JSON
import com.example.malllibrary.ui.recycler.DataConverter
import com.example.malllibrary.ui.recycler.ItemType
import com.example.malllibrary.ui.recycler.MultipleFields
import com.example.malllibrary.ui.recycler.MultipleItemEntity

class IndexDataConverter : DataConverter() {

    override fun convert(): ArrayList<MultipleItemEntity> {
        val jsonArray = JSON.parseObject(jsonData).getJSONArray("data")
        val size = jsonArray.size
        for (i in 0 until size) {
            val data = jsonArray.getJSONObject(i)
            val imageUrl = data.getString("imageUrl")
            val text = data.getString("text")
            val spanSize = data.getInteger("spanSize")
            val id = data.getInteger("goodsId")
            val banners = data.getJSONArray("banners")
            val bannerImgs = ArrayList<String>()
            var type = 0

            if (text != null && imageUrl == null && banners == null) {
                type = ItemType.TEXT
            } else if (text == null && imageUrl != null && banners == null) {
                type = ItemType.IMAGE
            } else if (text != null && imageUrl != null && banners == null) {
                type = ItemType.TEXT_IMAGE
            } else if (banners != null) {
                type = ItemType.BANNER
                for (j in 0 until banners.size) {
                    val img = banners.getString(j)
                    bannerImgs.add(img)
                }
            }
            val entity = MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, type)
                .setField(MultipleFields.SPAN_SIZE, spanSize)
                .setField(MultipleFields.BANNERS, bannerImgs)
                .setField(MultipleFields.IMAGE_URL, imageUrl)
                .setField(MultipleFields.TEXT, text)
                .setField(MultipleFields.ID, id)
                .build()

            mEntities.add(entity)
        }
        return mEntities
    }

}