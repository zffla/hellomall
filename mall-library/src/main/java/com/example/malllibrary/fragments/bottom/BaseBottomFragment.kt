package com.example.malllibrary.fragments.bottom

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.view.get
import com.example.malllibrary.R
import com.example.malllibrary.fragments.MallFragment
import com.joanzapata.iconify.widget.IconTextView

abstract class BaseBottomFragment : MallFragment(),View.OnClickListener {

    private val mItemFragments = ArrayList<BottomItemFragment>()
    private val mTabBeans = ArrayList<BottomTabBean>()
    private val mItems = LinkedHashMap<BottomTabBean, BottomItemFragment>()

    //当前展示的fragment
    private var mCurrentFragment = 0
    //首页显示的fragment
    private var mIndexFragment = 0

    //点击时的颜色
    private var mClickedColor = Color.RED

    private lateinit var mBottomBar: LinearLayout

    abstract fun setItems(builder: ItemBuilder): LinkedHashMap<BottomTabBean, BottomItemFragment>

    @ColorInt
    abstract fun setClickedColor(): Int

    abstract fun setIndexFragment(): Int

    override fun setLayout(): Any {
        return R.layout.fragment_bottom
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置首页fragment
        mIndexFragment = setIndexFragment()
        //设置点击颜色
        if (setClickedColor() != 0) {
            mClickedColor =setClickedColor()
        }
        val itemBuilder=ItemBuilder.builder()
        mItems.putAll(setItems(itemBuilder))
        for ((key,value) in mItems){
            mTabBeans.add(key)
            mItemFragments.add(value)
        }
    }

    /**
     * 绑定控件并初始化数据
     */
    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mBottomBar=findView(R.id.bottom_bar)
        val size=mItems.size
        for (i in 0 until size){
            LayoutInflater.from(context).inflate(R.layout.bottom_item,mBottomBar)
            val itemLayout=mBottomBar.getChildAt(i) as RelativeLayout
            itemLayout.tag=i
            itemLayout.setOnClickListener(this)
            val itemIcon=itemLayout.getChildAt(0) as IconTextView
            val itemText=itemLayout.getChildAt(1) as TextView
            val itemBean=mTabBeans[i]
            itemIcon.text=itemBean.icon
            itemText.text = itemBean.title

            if (i==mIndexFragment){
                itemIcon.setTextColor(mClickedColor)
                itemText.setTextColor(mClickedColor)
            }
        }

        val fragments=mItemFragments.toTypedArray()
        supportDelegate.loadMultipleRootFragment(R.id.fl_fragment_container,mIndexFragment,*fragments)
    }

    private fun resetColor(){
        val size=mItems.size
        for (i in 0 until size){
            val itemLayout=mBottomBar.getChildAt(i) as RelativeLayout
            val itemIcon=itemLayout.getChildAt(0) as IconTextView
            val itemText=itemLayout.getChildAt(1) as TextView
            itemIcon.setTextColor(Color.GRAY)
            itemText.setTextColor(Color.GRAY)
        }
    }

    private fun changeColor(index:Int){
        resetColor()
        val itemLayout=mBottomBar.getChildAt(index) as RelativeLayout
        val itemIcon=itemLayout.getChildAt(0) as IconTextView
        val itemText=itemLayout.getChildAt(1) as TextView
        itemIcon.setTextColor(mClickedColor)
        itemText.setTextColor(mClickedColor)
    }


    override fun onClick(v: View) {
        val itemIndex=v.tag as Int
        changeColor(itemIndex)
        supportDelegate.showHideFragment(mItemFragments[itemIndex],mItemFragments[mCurrentFragment])
        mCurrentFragment=itemIndex
    }
}