package com.example.hellomall.fragments.sort.content

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.hellomall.R

class ContentSectionAdapter constructor(sectionHeaderResId:Int,layoutResId:Int,data:MutableList<ContentSectionBean>)
    :BaseSectionQuickAdapter<ContentSectionBean,BaseViewHolder>(sectionHeaderResId,layoutResId,data) {

    override fun convert(holder: BaseViewHolder, item: ContentSectionBean) {
        val goodsThumb=item.item?.goodsThumb
        val goodsName=item.item?.goodsName
        holder.setText(R.id.tv,goodsName)
        val imageView=holder.getView<ImageView>(R.id.iv)
        Glide.with(context)
            .load(goodsThumb)
            .into(imageView)
    }

    override fun convertHeader(helper: BaseViewHolder, item: ContentSectionBean) {
        helper.setText(R.id.header,item.mHeader)
        helper.setVisible(R.id.more,item.isHeader)
    }
}