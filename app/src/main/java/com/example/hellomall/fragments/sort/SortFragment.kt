package com.example.hellomall.fragments.sort

import android.os.Bundle
import android.view.View
import com.example.hellomall.R
import com.example.hellomall.fragments.sort.content.ContentFragment
import com.example.hellomall.fragments.sort.list.VerticalListFragment
import com.example.malllibrary.fragments.bottom.BottomItemFragment

class SortFragment : BottomItemFragment() {

    override fun setLayout(): Any {
        return R.layout.activity_sort
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val verticalListFragment = VerticalListFragment()
        val contentFragment = ContentFragment.newInstance(1)
        supportDelegate.loadRootFragment(R.id.vertical_list_container, verticalListFragment)
        supportDelegate.loadRootFragment(R.id.sort_content_container, contentFragment)
    }
}