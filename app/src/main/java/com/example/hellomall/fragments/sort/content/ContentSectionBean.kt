package com.example.hellomall.fragments.sort.content

import com.chad.library.adapter.base.entity.SectionEntity

class ContentSectionBean(
    private val mIsHeader: Boolean,
    header: String?
) : SectionEntity {

    override val isHeader: Boolean
        get() = mIsHeader

    var isMore = false
    var id = -1

    var mHeader=header
    var item:ContentItemEntity?=null


}