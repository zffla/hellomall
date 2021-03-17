package com.example.hellomall.fragments.detail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hellomall.R
import com.example.malllibrary.fragments.MallFragment
import com.example.malllibrary.ui.recycler.MultipleFields
import com.example.malllibrary.ui.recycler.MultipleItemEntity

class ImageFragment:MallFragment() {

    private lateinit var mRecyclerView: RecyclerView

    companion object{
        private const val IMAGE_ARG="image_arg"
        fun create(data:ArrayList<String>):ImageFragment{
            val arg=Bundle()
            arg.putStringArrayList(IMAGE_ARG,data)
            val fragment=ImageFragment()
            fragment.arguments=arg
            return fragment
        }
    }

    override fun setLayout(): Any {
        return R.layout.fragment_image
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mRecyclerView=findView(R.id.rv_image_container)
        mRecyclerView.layoutManager=LinearLayoutManager(context)
        initData()
    }

    private fun initData() {
        val args=arguments
        if(args!=null){
            val data=args.getStringArrayList(IMAGE_ARG)
            val size=data!!.size
            val imagesList=ArrayList<MultipleItemEntity>()
            for (i in 0 until size){
                val imageUrl=data[i]
                val entity=MultipleItemEntity.builder()
                    .setItemType(R.id.id_goods_detail_image)
                    .setField(MultipleFields.IMAGE_URL,imageUrl)
                    .build()
                imagesList.add(entity)
            }
            val adapter=ImageRecyclerAdapter(imagesList)
            mRecyclerView.adapter=adapter
        }

    }
}