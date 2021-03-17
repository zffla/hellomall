package com.example.hellomall.fragments.index

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.hellomall.R
import com.example.malllibrary.global.GlobalKey
import com.example.malllibrary.global.Mall

@Suppress("unused")
class TranslucentBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<Toolbar>(context, attrs) {

    companion object {
        //延长滑动
        private const val MORE = 100
    }

    //注意，这里一定要是类变量
    private var mOffset = 0

    //确定我们所依赖的动作载体
    override fun layoutDependsOn(parent: CoordinatorLayout, child: Toolbar, dependency: View): Boolean {
        return dependency.id == R.id.rv_index
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: Toolbar,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return true
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        toolbar: Toolbar,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        val startOffset = 0
        val context =
            Mall.getConfiguration<Context>(GlobalKey.APPLICATION_CONTEXT)
        val endOffset = context.resources.getDimensionPixelOffset(
            R.dimen.header_height
        ) + MORE
        mOffset += dyConsumed
        when {
            mOffset <= startOffset -> toolbar.background.alpha = 0
            mOffset in (startOffset + 1)..(endOffset - 1) -> {
                val percent =
                    (mOffset - startOffset).toFloat() / endOffset
                val alpha = Math.round(percent * 255)
                toolbar.background.alpha = alpha
            }
            mOffset >= endOffset -> toolbar.background
        }
    }
}