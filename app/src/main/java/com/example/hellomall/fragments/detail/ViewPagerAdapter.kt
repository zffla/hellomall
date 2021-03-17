package com.example.hellomall.fragments.detail

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.alibaba.fastjson.JSONObject

class ViewPagerAdapter(fragmentManager: FragmentManager,data:JSONObject):FragmentStatePagerAdapter(fragmentManager) {


    //存储每个tab的名称
    private val mTabItems=ArrayList<String>()
    //存储每个tab下的图片集合
    private val mImages=ArrayList<ArrayList<String>>()

    init {
        initData(data)
    }

    private fun initData(data:JSONObject) {
        val mData=data.getJSONArray("tabs")
        val size=mData.size
        for (i in 0 until size){
            val tab=mData.getJSONObject(i)
            val name=tab.getString("name")
            mTabItems.add(name)
            val pictures=tab.getJSONArray("pictures")
            val tabImages=ArrayList<String>()
            val pictureSize=pictures.size
            for(j in 0 until pictureSize){
                tabImages.add(pictures.getString(j))
            }
            mImages.add(tabImages)
        }
    }

    override fun getCount(): Int {
        return mTabItems.size
    }

    override fun getItem(position: Int): Fragment {
        return ImageFragment.create(mImages[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTabItems[position]
    }

}