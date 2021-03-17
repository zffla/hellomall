package com.example.hellomall.fragments.personal

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.hellomall.R
import com.example.malllibrary.fragments.bottom.BottomItemFragment
import com.example.malllibrary.util.AssetFileUtil
import de.hdodenhof.circleimageview.CircleImageView

class PersonalFragment: BottomItemFragment() {
    private lateinit var mListView: ListView
    private lateinit var mIvPhoto:CircleImageView
    private lateinit var mTvName:TextView

    private val mRvData= arrayOf("个人信息","收货地址","我的收藏")

    companion object{
        private val options=RequestOptions()
            .centerCrop()
            .dontAnimate()
    }

    override fun setLayout(): Any {
        return R.layout.activity_personal
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mListView=findView(R.id.lv_personal_list)
        mIvPhoto=findView(R.id.cv_personal_photo)
        mTvName=findView(R.id.tv_user_name)
        initData()
        initUser()
    }

    private fun initUser() {
        val data=AssetFileUtil.getAssetFileStr("user.json",context!!)
        val dataUser=JSON.parseObject(data).getJSONObject("data")
        mTvName.text=dataUser.getString("name")
        Glide.with(context!!)
            .load(dataUser.getString("avatar"))
            .apply(options)
            .into(mIvPhoto)
    }

    private fun initData() {
        val adapter=ArrayAdapter(context!!,android.R.layout.simple_list_item_1,mRvData)
        mListView.adapter=adapter
    }
}