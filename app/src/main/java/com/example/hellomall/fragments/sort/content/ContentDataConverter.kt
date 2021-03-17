package com.example.hellomall.fragments.sort.content

import com.alibaba.fastjson.JSON

class ContentDataConverter {

    fun convert(json:String):ArrayList<ContentSectionBean>{
        val data=ArrayList<ContentSectionBean>()
        val dataArray= JSON.parseObject(json)
            .getJSONArray("data")
        val arraySize=dataArray.size
        for (i in 0 until arraySize){
            val dataObject=dataArray.getJSONObject(i)
            val id=dataObject.getInteger("id")
            val title=dataObject.getString("section")
            val sectionBean=ContentSectionBean(true,title)
            sectionBean.id=id
            sectionBean.isMore=true
            data.add(sectionBean)

            val section=dataArray.getJSONObject(i).getJSONArray("goods")
            val sectionSize=section.size

            for(j in 0 until sectionSize){
                val item=section.getJSONObject(j)
                val itemEntity=ContentItemEntity()
                itemEntity.goodsId=item.getInteger("goods_id")
                itemEntity.goodsThumb=item.getString("goods_thumb")
                itemEntity.goodsName=item.getString("goods_name")
                val contentSectionBean=ContentSectionBean(false,null)
                contentSectionBean.item=itemEntity
                data.add(contentSectionBean)
            }
        }
        return data
    }
}