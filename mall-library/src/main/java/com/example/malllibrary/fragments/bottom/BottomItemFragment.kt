package com.example.malllibrary.fragments.bottom

import android.widget.Toast
import com.example.malllibrary.fragments.MallFragment

abstract class BottomItemFragment:MallFragment() {

    private var mTouchTime:Long=0

    companion object{
        private const val WAIT_TIME=1000L
    }

    override fun onBackPressedSupport(): Boolean {
        if (System.currentTimeMillis()-mTouchTime<= WAIT_TIME){
            _mActivity.finish()
        }else{
            mTouchTime=System.currentTimeMillis()
            Toast.makeText(_mActivity,"双击退出", Toast.LENGTH_SHORT).show()
        }
        return true
    }

}