package com.example.hellomall.fragments.detail

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.example.hellomall.fragments.MallMainFragment
import com.example.malllibrary.ui.recycler.MultipleFields
import com.example.malllibrary.ui.recycler.MultipleItemEntity

class IndexItemClickListener private constructor(private val fragment: MallMainFragment):OnItemClickListener{


    companion object{
        fun create(fragment: MallMainFragment):IndexItemClickListener{
            return IndexItemClickListener(fragment)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        //数据有问题，有的点击无返回数据
        val entity= adapter.data[position] as MultipleItemEntity
        val goodsId=entity.getField<Int>(MultipleFields.ID)
        val detailFragment=DetailFragment.create(goodsId)
        fragment.start(detailFragment)
    }
}