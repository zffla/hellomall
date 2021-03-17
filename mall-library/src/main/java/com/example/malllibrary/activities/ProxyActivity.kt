package com.example.malllibrary.activities

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.malllibrary.R
import com.example.malllibrary.fragments.MallFragment
import me.yokeyword.fragmentation.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 * 承载fragments的代理activity
 */
abstract class ProxyActivity : SupportActivity() {

    abstract fun setRootFragment(): MallFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer(savedInstanceState)
    }

    private fun initContainer(savedInstanceState: Bundle?) {
        val container = FrameLayout(this)
        container.id = R.id.fragment_container
        setContentView(container)
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fragment_container,setRootFragment())
        }
    }

}