package com.example.hellomall.fragments.sort.content

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hellomall.R
import com.example.malllibrary.fragments.MallFragment
import com.example.malllibrary.net.RestClient
import com.example.malllibrary.net.callback.ISuccess

class ContentFragment:MallFragment() {

    private var mContentId=-1
    private lateinit var mRecyclerView: RecyclerView

    //简单工厂模式创建
    companion object{
        private const val CONTENT_ID="content_id"
        fun newInstance(contentId:Int):ContentFragment{
            val bundle=Bundle()
            bundle.putInt(CONTENT_ID,contentId)
            val fragment=ContentFragment()
            fragment.arguments=bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args=arguments
        if (args!=null){
            mContentId=args.getInt(CONTENT_ID)
        }
    }

    override fun setLayout(): Any {
        return R.layout.fragment_list_content
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mRecyclerView=findView(R.id.rv_list_content)
        val manager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        mRecyclerView.layoutManager=manager
        initData()
    }

    private fun initData() {
        RestClient.builder()
            .url("sort_content_list.php?contentId=$mContentId")
            .loader(context!!)
            .success(object :ISuccess{
                override fun onSuccess(response: String) {
                    val data=ContentDataConverter().convert(response)
                    val adapter=ContentSectionAdapter(R.layout.item_section_header,R.layout.item_section_content,data)
                    mRecyclerView.adapter=adapter
                }
            })
            .build()
            .get()
    }

}