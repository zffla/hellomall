package com.example.hellomall.fragments

import android.graphics.Color
import com.example.hellomall.fragments.cart.ShopCartFragment
import com.example.hellomall.fragments.index.IndexFragment
import com.example.hellomall.fragments.personal.PersonalFragment
import com.example.hellomall.fragments.sort.SortFragment
import com.example.malllibrary.fragments.bottom.BaseBottomFragment
import com.example.malllibrary.fragments.bottom.BottomItemFragment
import com.example.malllibrary.fragments.bottom.BottomTabBean
import com.example.malllibrary.fragments.bottom.ItemBuilder

class MallMainFragment : BaseBottomFragment() {
    override fun setItems(builder: ItemBuilder): LinkedHashMap<BottomTabBean, BottomItemFragment> {
        val items = LinkedHashMap<BottomTabBean, BottomItemFragment>()
        //添加item
        items[BottomTabBean("{fa-home}", "主页")] = IndexFragment()
        items[BottomTabBean("{fa-sort}", "分类")] = SortFragment()
        items[BottomTabBean("{fa-shopping-cart}", "购物车")] = ShopCartFragment()
        items[BottomTabBean("{fa-user}", "我的")] = PersonalFragment()
        return builder.addItems(items).build()
    }

    override fun setClickedColor(): Int {
        return Color.parseColor("#ffff8800")
    }

    override fun setIndexFragment(): Int {
        return 0
    }
}