package com.example.hellomall.fragments.cart

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hellomall.R
import com.example.malllibrary.fragments.bottom.BottomItemFragment
import com.example.malllibrary.net.RestClient
import com.example.malllibrary.net.callback.ISuccess
import com.example.malllibrary.ui.recycler.MultipleItemEntity
import com.joanzapata.iconify.widget.IconTextView
import com.mall.example.fragments.cart.ShopCartItemFields

class ShopCartFragment : BottomItemFragment(), View.OnClickListener, ITotalPriceChangedListener {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mIconSelectAll: IconTextView
    private lateinit var mAdapter: ShopCartAdapter
    private lateinit var mTvClearAll: TextView
    private lateinit var mTvDelete: TextView
    private lateinit var mTotalPrice: TextView
    private lateinit var mStubNoItem:ViewStub

    override fun setLayout(): Any {
        return R.layout.activity_cart
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mRecyclerView = findView(R.id.rv_shop_cart)
        val manager = LinearLayoutManager(context)
        mRecyclerView.layoutManager = manager
        initData()

        //全选
        mIconSelectAll = findView(R.id.icon_shop_cart_select_all)
        mIconSelectAll.setOnClickListener(this)
        mIconSelectAll.tag = false

        //清空和删除
        mTvClearAll = findView(R.id.tv_top_shop_cart_clear)
        mTvClearAll.setOnClickListener(this)
        mTvDelete = findView(R.id.tv_shop_cart_remove_selected)
        mTvDelete.setOnClickListener(this)

        mTotalPrice = findView(R.id.tv_total_price)

        mStubNoItem=findView(R.id.stub_no_item)
    }

    private fun initData() {
        RestClient.builder()
            .url("shop_cart.php")
            .success(object : ISuccess {
                override fun onSuccess(response: String) {
                    val data = ShopCartDataConverter().setJsonData(response).convert()
                    mAdapter = ShopCartAdapter(data)
                    mAdapter.setTotalPriceChangedListener(this@ShopCartFragment)
                    mRecyclerView.adapter = mAdapter
                }
            })
            .build()
            .post()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.icon_shop_cart_select_all -> onClickSelectAll()
            R.id.tv_top_shop_cart_clear -> onClickClearAll()
            R.id.tv_shop_cart_remove_selected -> onClickDelete()
        }
    }


    /**
     * 删除点击事件：
     * 删除已选中商品
     */
    private fun onClickDelete() {
        val deleteList = ArrayList<MultipleItemEntity>()
        mAdapter.data.forEach {
            val entity = it
            val isSelected = entity.getField<Boolean>(ShopCartItemFields.IS_SELECTED)
            if (isSelected) {
                deleteList.add(entity)
            }
        }
        if (deleteList.size == 0) {
            Toast.makeText(context, "您尚未选中任何商品", Toast.LENGTH_SHORT).show()
        } else {
            AlertDialog.Builder(context!!)
                .setTitle("提示")
                .setMessage("确认删除已选中的商品吗？")
                .setPositiveButton(
                    "确认"
                ) { _, _ ->
                    deleteList.forEach {
                        mAdapter.data.remove(it)
                    }
                    mAdapter.setTotalPrice(0.00)
                    mAdapter.notifyDataSetChanged()
                    mTotalPrice.text = "￥0.00"
                    checkItemCount()
                }
                .setNegativeButton("取消", null)
                .create()
                .show()


        }

    }


    /**
     * 清空点击事件
     */
    private fun onClickClearAll() {
        AlertDialog.Builder(context!!)
            .setTitle("提示")
            .setMessage("确认清空购物车吗？")
            .setPositiveButton(
                "确认"
            ) { _, _ ->
                mAdapter.data.clear()
                mAdapter.setTotalPrice(0.00)
                mAdapter.notifyDataSetChanged()
                mTotalPrice.text = "￥0.00"
                checkItemCount()
            }
            .setNegativeButton("取消", null)
            .create()
            .show()
    }

    /**
     * 全选点击事件
     */
    private fun onClickSelectAll() {
        val selectAll = mIconSelectAll.tag as Boolean
        if (!selectAll) {
            //点击前未选中
            mAdapter.setSelectAll(true)
            mAdapter.notifyItemRangeChanged(0, mAdapter.itemCount)
            mIconSelectAll.tag = true
            mIconSelectAll.setTextColor(ContextCompat.getColor(context!!, R.color.app_main))
            mAdapter.setTotalPrice(getTotalSelectedPrice())
            mTotalPrice.text = "￥" + getTotalSelectedPrice().toString()
        } else {
            mAdapter.setSelectAll(false)
            mAdapter.notifyItemRangeChanged(0, mAdapter.itemCount)
            mIconSelectAll.tag = false
            mIconSelectAll.setTextColor(Color.GRAY)
            mAdapter.setTotalPrice(0.00)
            mTotalPrice.text = "￥0.00"
        }

    }

    override fun onPriceChanged(price: Double) {
        mTotalPrice.text = "￥" + price.toString()
    }

    private fun getTotalSelectedPrice(): Double {
        var total = 0.00
        mAdapter.data.forEach {
            val price = it.getField<Double>(ShopCartItemFields.PRICE)
            val count = it.getField<Int>(ShopCartItemFields.COUNT)
            total += price * count
        }
        return total
    }

    private fun checkItemCount(){
        val itemCount=mAdapter.itemCount
        if (itemCount==0){
            val stub=mStubNoItem.inflate()
            val goBuy=stub.findViewById<TextView>(R.id.tv_stub_to_buy)
            goBuy.setOnClickListener{
                //跳转至首页
                TODO()
            }
            mRecyclerView.visibility = View.GONE
        }else{
            mRecyclerView.visibility = View.VISIBLE
        }
    }
}