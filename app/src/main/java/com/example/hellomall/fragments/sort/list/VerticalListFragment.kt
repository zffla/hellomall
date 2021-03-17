package com.example.hellomall.fragments.sort.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hellomall.R
import com.example.hellomall.fragments.sort.SortFragment
import com.example.malllibrary.fragments.MallFragment
import com.example.malllibrary.net.RestClient
import com.example.malllibrary.net.callback.ISuccess

class VerticalListFragment:MallFragment() {

    private lateinit var mRecyclerView: RecyclerView

    override fun setLayout(): Any {
        return R.layout.fragment_vertical_list
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mRecyclerView=findView(R.id.rv_vertical_list)
        val manager=LinearLayoutManager(context)
        mRecyclerView.layoutManager=manager
        //屏蔽动画
        mRecyclerView.itemAnimator=null
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        RestClient.builder()
            .url("sort_list.php")
            .loader(context!!)
            .success(object :ISuccess{
                override fun onSuccess(response: String) {
                    val data=VerticalListDataConverter()
                        .setJsonData(response)
                        .convert()
                    val adapter=VerticalListAdapter(data,parentFragment as SortFragment)
                    mRecyclerView.adapter=adapter
                }

            })
            .build()
            .get()
    }
}