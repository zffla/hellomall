package com.example.hellomall.fragments.sort.list

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.hellomall.R
import com.example.hellomall.fragments.sort.SortFragment
import com.example.hellomall.fragments.sort.content.ContentFragment
import com.example.malllibrary.ui.recycler.*
import me.yokeyword.fragmentation.SupportHelper

class VerticalListAdapter constructor(data:MutableList<MultipleItemEntity>,private val sortFragment: SortFragment) :MultipleRecyclerAdapter(data){

    private var mPrePosition=0

    init {
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_list)
    }

    override fun convert(holder: MultiViewHolder, item: MultipleItemEntity) {
        super.convert(holder, item)
        when(holder.itemViewType){
            ItemType.VERTICAL_MENU_LIST->{
                val name=item.getField<String>(MultipleFields.NAME)
                val isClicked=item.getField<Boolean>(MultipleFields.TAG)
                //取出控件
                val line=holder.getView<View>(R.id.view_line)
                val itemText=holder.getView<TextView>(R.id.tv_vertical_item_name)
                //点击事件
                holder.itemView.setOnClickListener {
                    val currentPosition=holder.adapterPosition
                    if (mPrePosition!=currentPosition){
                        //切换颜色
                        data[mPrePosition].setField(MultipleFields.TAG,false)
                        notifyItemChanged(mPrePosition)
                        item.setField(MultipleFields.TAG,true)
                        notifyItemChanged(currentPosition)
                        mPrePosition=currentPosition
                        //切换右侧content
                        val contentId=data[currentPosition].getField<Int>(MultipleFields.ID)
                        showFragment(contentId)
                    }
                }

                //设置点击事件
                if (!isClicked){
                    //未被点击
                    line.visibility=View.INVISIBLE
                    itemText.setTextColor(ContextCompat.getColor(context,R.color.we_chat_black))
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.item_background))
                }else{
                    line.visibility=View.VISIBLE
                    itemText.setTextColor(ContextCompat.getColor(context,R.color.app_main))
                    line.setBackgroundColor(ContextCompat.getColor(context,R.color.app_main))
                    holder.itemView.setBackgroundColor(Color.WHITE)
                }
                itemText.text = name
            }
        }
    }

    private fun switchContentFragment(fragment:ContentFragment){
        val contentFragment=SupportHelper
            .findFragment(sortFragment.childFragmentManager,ContentFragment::class.java)
        contentFragment.replaceFragment(fragment,false)
    }

    private fun showFragment(contentId:Int){
        val contentFragment=ContentFragment.newInstance(contentId)
        switchContentFragment(contentFragment)
    }
}