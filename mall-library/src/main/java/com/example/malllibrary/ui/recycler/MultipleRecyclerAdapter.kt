package com.example.malllibrary.ui.recycler

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.listener.GridSpanSizeLookup
import com.example.malllibrary.R
import com.example.malllibrary.ui.banner.BannerCreator
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter

open class MultipleRecyclerAdapter constructor(data:MutableList<MultipleItemEntity>):
    BaseMultiItemQuickAdapter<MultipleItemEntity,MultiViewHolder>(data),
    GridSpanSizeLookup {

    companion object{
        //Glide请求选项
        private val REQUEST_OPTIONS=RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()

        fun create(data: MutableList<MultipleItemEntity>):MultipleRecyclerAdapter{
            return MultipleRecyclerAdapter(data)
        }

        fun create(converter: DataConverter):MultipleRecyclerAdapter{
            return MultipleRecyclerAdapter(converter.convert())
        }

    }

    init {
        initView()
    }

    private fun initView(){
        addItemType(ItemType.TEXT,R.layout.item_multiple_text)
        addItemType(ItemType.IMAGE,R.layout.item_multiple_image)
        addItemType(ItemType.TEXT_IMAGE,R.layout.item_multiple_image_text)
        addItemType(ItemType.BANNER,R.layout.item_multiple_banner)

        //设置间隔监听
        setGridSpanSizeLookup(this)
        //打开动画
        animationEnable=true
        //设置可以反复动画
        isAnimationFirstOnly=false
    }

    override fun createBaseViewHolder(view: View): MultiViewHolder {
        return MultiViewHolder.create(view)
    }
    /**
     * 数据与UI绑定
     */
    override fun convert(holder: MultiViewHolder, item: MultipleItemEntity) {
        val text:String
        val imageUrl:String
        val bannerImgs:ArrayList<String>
        when(holder.itemViewType){
            ItemType.TEXT->{
                text=item.getField(MultipleFields.TEXT)
                holder.setText(R.id.text_single,text)
            }
            ItemType.IMAGE->{
                imageUrl=item.getField(MultipleFields.IMAGE_URL)
                val imgView=holder.getView<ImageView>(R.id.img_single)
                Glide.with(context)
                    .load(imageUrl)
                    .apply(REQUEST_OPTIONS)
                    .into(imgView)
            }
            ItemType.TEXT_IMAGE->{
                text=item.getField(MultipleFields.TEXT)
                holder.setText(R.id.tv_multiple,text)
                imageUrl=item.getField(MultipleFields.IMAGE_URL)
                val imgView=holder.getView<ImageView>(R.id.img_multiple)
                Glide.with(context)
                    .load(imageUrl)
                    .apply(REQUEST_OPTIONS)
                    .into(imgView)
            }
            ItemType.BANNER->{
                bannerImgs=item.getField(MultipleFields.BANNERS)
                val banners=holder
                    .getView<Banner<String, BannerImageAdapter<String>>>(R.id.banner_recycler_item)
                //设置默认的轮播样式
                BannerCreator.setDefault(context,bannerImgs,banners)
            }
        }
    }

    override fun getSpanSize(
        gridLayoutManager: GridLayoutManager,
        viewType: Int,
        position: Int
    ): Int {
        return data[position].getField(MultipleFields.SPAN_SIZE)
    }


}