package com.example.malllibrary.global

import android.os.Handler
import com.example.malllibrary.util.MemoryStorage
import com.joanzapata.iconify.IconFontDescriptor
import com.joanzapata.iconify.Iconify
import java.lang.RuntimeException


/**
 * 全局控制类
 * 每次配置时一层一层的添加with函数配置即可
 */
class Configurator private constructor(){

    //存储图标
    private val mIcons=ArrayList<IconFontDescriptor>()

    private object Holder{
        internal val INSTANCE= Configurator()
    }

    companion object{
        //全局存储
        private val mStorage=MemoryStorage.instance
        //handler,避免重复创建
        private val mHandler= Handler()

        internal val instance
                get() = Holder.INSTANCE
    }

    init {
        //是否配置完成
        mStorage.addData(GlobalKey.IS_CONFIGURE_READY,false)
        mStorage.addData(GlobalKey.HANDLER, mHandler)
    }

    private fun initIcons() {
        if (mIcons.size > 0) {
            val initializer = Iconify.with(mIcons[0])
            for (i in 1 until mIcons.size) {
                initializer.with(mIcons[i])
            }
        }
    }

    /**
     * 配置icon
     */
    fun withIcon(icon:IconFontDescriptor):Configurator{
        mIcons.add(icon)
        return this
    }

    /**
     * 访问服务器的api设置
     */
    fun withApiHost(host:String):Configurator{
        mStorage.addData(GlobalKey.API_HOST,host)
        return this
    }

    /**
     * 模拟网络延迟时间设置
     */
    fun withLoadingDelay(delay:Long):Configurator{
        mStorage.addData(GlobalKey.LOADER_DELAY,delay)
        return this
    }

    /**
     * 配置完成
     */
    fun configure(){
        mStorage.addData(GlobalKey.IS_CONFIGURE_READY,true)
        initIcons()
        //回收工作
    }

    /**
     * 检验配置是否完成
     */
    private fun checkConfiguration(){
        val isReady= mStorage.getData<Boolean>(GlobalKey.IS_CONFIGURE_READY)
        if (!isReady){
            throw RuntimeException("The configuration is not ready")
        }
    }

    /**
     * 获取配置
     */
    fun <T> getConfiguration(key:String):T{
        checkConfiguration()
        return mStorage.getData(key)
    }

    fun <T> getConfiguration(key: Enum<*>):T{
        return getConfiguration(key.name)
    }
}