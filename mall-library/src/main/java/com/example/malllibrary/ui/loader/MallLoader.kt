package com.example.malllibrary.ui.loader

import android.content.Context
import android.view.Gravity
import androidx.appcompat.app.AppCompatDialog
import com.blankj.utilcode.util.ScreenUtils
import com.example.malllibrary.R
import com.wang.avi.AVLoadingIndicatorView
import com.wang.avi.Indicator
import com.wang.avi.indicators.BallClipRotateMultipleIndicator

/**
 * loading对话框
 */
object MallLoader {
    private val LOADER_SIZE_SCALE = 8
    private val LOADER_OFFSET_SCALE = 10

    //管理dialog的容器
    private val LOADERS = ArrayList<AppCompatDialog>()
    //默认的loading类型
    private val DEFAULT_STYLE= BallClipRotateMultipleIndicator()

    private fun createDialog(
        context: Context?,
        loadingView: AVLoadingIndicatorView
    ): AppCompatDialog {
        val dialog = AppCompatDialog(context, R.style.dialog)
        //获取屏幕宽高，以保证dialog正常铺满屏幕
        val screenWidth = ScreenUtils.getScreenWidth()
        val screenHeight = ScreenUtils.getScreenHeight()
        val dialogWindow = dialog.window
        dialog.setContentView(loadingView)
        if (dialogWindow != null) {
            val lp = dialogWindow.attributes
            lp.width = screenWidth / LOADER_SIZE_SCALE
            lp.height = screenHeight / LOADER_SIZE_SCALE
            lp.height = lp.height + LOADER_OFFSET_SCALE
            lp.gravity = Gravity.CENTER
        }
        LOADERS.add(dialog)
        return dialog
    }

    /**
     * 展示loading框
     */
    fun showLoading(context: Context?, type: Enum<LoaderStyles>) {
        showLoading(context, type.name)
    }

    fun showLoading(context: Context?, type: String) {
        val indicatorView = AVLoadingIndicatorView(context)
        indicatorView.setIndicator(type)
        createDialog(context, indicatorView).show()
    }

    fun showLoading(context: Context?, indicator:Indicator= DEFAULT_STYLE) {
        val indicatorView = AVLoadingIndicatorView(context)
        indicatorView.indicator = indicator
        createDialog(context, indicatorView).show()
    }

    fun stopLoading(){
        for (loader in LOADERS){
            if(loader.isShowing){
                loader.cancel()
            }
        }
        LOADERS.clear()
    }
}