package com.example.malllibrary.ui.banner

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

object BannerCreator {
    private val options = RequestOptions()
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    fun setDefault(
        context:Context,
        mData: List<String>,
        banner: Banner<String, BannerImageAdapter<String>>
    ) {
        banner.setAdapter(object : BannerImageAdapter<String>(mData) {
            override fun onBindView(
                holder: BannerImageHolder,
                data: String,
                position: Int,
                size: Int
            ) {
                Glide.with(holder.itemView)
                    .load(data)
                    .apply(options)
                    .into(holder.imageView)
            }

        })
            .isAutoLoop(true)
            .setIndicator(CircleIndicator(context))
            .setIndicatorGravity(IndicatorConfig.Direction.CENTER)
            .start()
    }
}