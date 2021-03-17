package com.example.hellomall.fragments.index

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.example.hellomall.R
import com.example.hellomall.fragments.MallMainFragment
import com.example.hellomall.fragments.detail.IndexItemClickListener
import com.example.malllibrary.fragments.MallFragment
import com.example.malllibrary.fragments.bottom.BottomItemFragment
import com.example.malllibrary.net.RestClient
import com.example.malllibrary.net.callback.ISuccess
import com.example.malllibrary.ui.recycler.MultipleFields
import com.example.malllibrary.ui.recycler.MultipleItemEntity
import com.example.malllibrary.ui.recycler.MultipleRecyclerAdapter

class IndexFragment: BottomItemFragment() {

    private lateinit var mRecyclerView:RecyclerView

    override fun setLayout(): Any {
        return R.layout.activity_index
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mRecyclerView=findView(R.id.rv_index)
    }


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initRecyclerView()
        initData()
    }

    private fun initRecyclerView(){
        val manager=GridLayoutManager(context,4)
        mRecyclerView.layoutManager=manager
    }

    private fun initData(){
        //从服务器获取数据
        RestClient.builder()
            .url("index.php")
            .loader(context!!)
            .success(object :ISuccess{
                override fun onSuccess(response: String) {
                    val mAdapter=MultipleRecyclerAdapter
                        .create(IndexDataConverter().setJsonData(response))
                    val fragment=parentFragment as MallMainFragment
                    mAdapter.setOnItemClickListener(IndexItemClickListener.create(fragment))
                    mRecyclerView.adapter=mAdapter

                }
            })
            .build()
            .get()

    }

}