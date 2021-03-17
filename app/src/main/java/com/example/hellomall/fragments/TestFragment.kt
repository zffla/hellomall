package com.example.hellomall.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.hellomall.R
import com.example.malllibrary.fragments.MallFragment

class TestFragment:MallFragment() {
    override fun setLayout(): Any {
        return R.layout.activity_detail
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        Toast.makeText(context,"初始化完成",Toast.LENGTH_SHORT).show()
    }
}