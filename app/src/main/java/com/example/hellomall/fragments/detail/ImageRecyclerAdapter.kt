package com.example.hellomall.fragments.detail

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.hellomall.R
import com.example.malllibrary.ui.recycler.MultiViewHolder
import com.example.malllibrary.ui.recycler.MultipleFields
import com.example.malllibrary.ui.recycler.MultipleItemEntity
import com.example.malllibrary.ui.recycler.MultipleRecyclerAdapter

class ImageRecyclerAdapter(data:ArrayList<MultipleItemEntity>) :MultipleRecyclerAdapter(data){


    companion object{
        private val options=RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
    }

    init {
        addItemType(R.id.id_goods_detail_image,R.layout.item_image)
    }

    override fun convert(holder: MultiViewHolder, item: MultipleItemEntity) {
        super.convert(holder, item)
        val imageUrl=item.getField<String>(MultipleFields.IMAGE_URL)
        val imageView=holder.getView<ImageView>(R.id.image_rv_item)
        Glide.with(context)
            .load(imageUrl)
            .apply(options)
            .into(imageView)
    }
}