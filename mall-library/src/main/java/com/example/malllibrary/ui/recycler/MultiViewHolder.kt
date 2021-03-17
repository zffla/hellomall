package com.example.malllibrary.ui.recycler

import android.view.View
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class MultiViewHolder constructor(view:View):BaseViewHolder(view) {
    companion object{
        fun create(view: View):MultiViewHolder{
            return MultiViewHolder(view)
        }
    }
}