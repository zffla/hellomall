package com.example.hellomall.fragments.detail

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.example.hellomall.R
import com.example.malllibrary.fragments.MallFragment

class GoodsInfoFragment:MallFragment() {

    private lateinit var mData:JSONObject

    companion object{
        private const val GOODS_INFO_ARG="goods_info_arg"

        fun create(data:String):GoodsInfoFragment{
            val arg=Bundle()
            arg.putString(GOODS_INFO_ARG,data)
            val fragment=GoodsInfoFragment()
            fragment.arguments=arg
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arg=arguments?.getString(GOODS_INFO_ARG)
        mData= JSON.parseObject(arg)
    }

    private lateinit var mTvTitle:TextView
    private lateinit var mTvPrice:TextView
    private lateinit var mTvDesc:TextView

    override fun setLayout(): Any {
        return R.layout.fragment_goods_info
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mTvTitle=findView(R.id.tv_goods_info_title)
        mTvPrice=findView(R.id.tv_goods_info_price)
        mTvDesc=findView(R.id.tv_goods_info_desc)

        initData()
    }

    private fun initData(){
        val title=mData.getString("name")
        val desc=mData.getString("description")
        val price=mData.getInteger("price")

        mTvTitle.text=title
        mTvDesc.text=desc
        mTvPrice.text=price.toString()
    }
}