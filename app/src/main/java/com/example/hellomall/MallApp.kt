package com.example.hellomall

import android.app.Application
import com.example.malllibrary.global.Mall
import com.joanzapata.iconify.fonts.FontAwesomeModule

class MallApp : Application(){
    override fun onCreate() {
        super.onCreate()
        Mall.init(this)
            .withIcon(FontAwesomeModule())
                //配置远程服务器
            .withApiHost("http://mock.fulingjie.com/mock/api/")
//            .withLoadingDelay(5000)
            .configure()
    }
}