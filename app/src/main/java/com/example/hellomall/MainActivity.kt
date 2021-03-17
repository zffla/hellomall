package com.example.hellomall

import android.os.Bundle
import com.example.hellomall.fragments.MallMainFragment
import com.example.malllibrary.activities.ProxyActivity
import com.example.malllibrary.fragments.MallFragment

class MainActivity : ProxyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }

    override fun setRootFragment(): MallFragment {
        return MallMainFragment()
    }

}
