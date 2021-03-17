package com.example.hellomall.fragments.cart

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.hellomall.R
import com.example.malllibrary.net.RestClient
import com.example.malllibrary.net.callback.ISuccess
import com.example.malllibrary.ui.recycler.MultiViewHolder
import com.example.malllibrary.ui.recycler.MultipleFields
import com.example.malllibrary.ui.recycler.MultipleItemEntity
import com.example.malllibrary.ui.recycler.MultipleRecyclerAdapter
import com.joanzapata.iconify.widget.IconTextView
import com.mall.example.fragments.cart.ShopCartItemFields

class ShopCartAdapter(data:ArrayList<MultipleItemEntity>):MultipleRecyclerAdapter(data) {

    private var mIsSelectAll=false
    private var mTotalPrice=0.00
    lateinit var mITotalPriceChangedListener:ITotalPriceChangedListener

    companion object{
        //Glide请求选项
        private val REQUEST_OPTIONS= RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
    }

    init {
        addItemType(R.id.id_shop_cart,R.layout.item_shop_cart)

    }

    fun setSelectAll(isSelected:Boolean){
        this.mIsSelectAll=isSelected
    }

    fun setTotalPrice(price:Double){
        this.mTotalPrice=price
    }

    fun setTotalPriceChangedListener(listener: ITotalPriceChangedListener){
        this.mITotalPriceChangedListener=listener
    }

    override fun convert(holder: MultiViewHolder, item: MultipleItemEntity) {
        super.convert(holder, item)
        when(holder.itemViewType){
            R.id.id_shop_cart->{
                val thumb = item.getField<String>(MultipleFields.IMAGE_URL)
                val title = item.getField<String>(ShopCartItemFields.TITLE)
                val desc = item.getField<String>(ShopCartItemFields.DESC)
                val count = item.getField<Int>(ShopCartItemFields.COUNT)
                val price = item.getField<Double>(ShopCartItemFields.PRICE)

                //取出控件
                val imgThumb = holder.getView<ImageView>(R.id.image_item_shop_cart)
                val tvTitle = holder.getView<TextView>(R.id.tv_item_shop_cart_title)
                val tvDesc = holder.getView<TextView>(R.id.tv_item_shop_cart_desc)
                val tvPrice = holder.getView<TextView>(R.id.tv_item_shop_cart_price)
                val iconMinus = holder.getView<IconTextView>(R.id.icon_item_minus)
                val iconPlus = holder.getView<IconTextView>(R.id.icon_item_plus)
                val tvCount = holder.getView<TextView>(R.id.tv_shop_cart_count)
                val iconIsSelected = holder.getView<IconTextView>(R.id.icon_item_shop_cart)

                //绑定数据
                tvTitle.text=title
                tvDesc.text=desc
                tvPrice.text=price.toString()
                tvCount.text=count.toString()
                Glide.with(context)
                    .load(thumb)
                    .apply(REQUEST_OPTIONS)
                    .into(imgThumb)

                item.setField(ShopCartItemFields.IS_SELECTED,mIsSelectAll)

                val isSelected=item.getField<Boolean>(ShopCartItemFields.IS_SELECTED)
                //设置勾选框选中状态
                if (isSelected){
                    iconIsSelected.setTextColor(ContextCompat.getColor(context,R.color.app_main))
                }else{
                    iconIsSelected.setTextColor(Color.GRAY)
                }
                //设置勾选框点击事件
                iconIsSelected.setOnClickListener {
                    val currentState=item.getField<Boolean>(ShopCartItemFields.IS_SELECTED)
                    if (currentState){
                        //之前为选中状态
                        iconIsSelected.setTextColor(Color.GRAY)
                        item.setField(ShopCartItemFields.IS_SELECTED,false)
                        mTotalPrice-=price*count
                        //通知fragment总价发生变化
                        mITotalPriceChangedListener.onPriceChanged(mTotalPrice)
                    }else{
                        //之前未选中
                        iconIsSelected.setTextColor(ContextCompat.getColor(context,R.color.app_main))
                        item.setField(ShopCartItemFields.IS_SELECTED,true)
                        mTotalPrice+=price*count
                        //通知fragment总价发生变化
                        mITotalPriceChangedListener.onPriceChanged(mTotalPrice)
                    }

                }

                //设置加减事件
                iconPlus.setOnClickListener {
                    var currentCount=item.getField<Int>(ShopCartItemFields.COUNT)
                    RestClient.builder()
                        .url("shop_cart_count.php")
                        .params("count",currentCount)
                        .success(object :ISuccess{
                            override fun onSuccess(response: String) {
                                currentCount++
                                tvCount.text = currentCount.toString()
                                item.setField(ShopCartItemFields.COUNT,currentCount)
                            }
                        })
                        .build()
                        .post()
                }

                iconMinus.setOnClickListener {
                    var currentCount=item.getField<Int>(ShopCartItemFields.COUNT)
                    RestClient.builder()
                        .url("shop_cart_count.php")
                        .params("count",currentCount)
                        .success(object :ISuccess{
                            override fun onSuccess(response: String) {
                                currentCount--
                                tvCount.text = currentCount.toString()
                                item.setField(ShopCartItemFields.COUNT,currentCount)
                            }
                        })
                        .build()
                        .post()
                }
            }
        }
    }
}