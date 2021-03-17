package com.example.malllibrary.global

import android.content.Context
import com.example.malllibrary.util.MemoryStorage

object Mall {

    val configurator: Configurator
        get() = Configurator.instance

    fun init(context: Context): Configurator {
        MemoryStorage.instance.addData(GlobalKey.APPLICATION_CONTEXT, context.applicationContext)
        return Configurator.instance
    }

    fun <T> getConfiguration(key: String): T {
        return configurator.getConfiguration(key)
    }

    fun <T> getConfiguration(key: Enum<GlobalKey>): T {
        return getConfiguration(key.name)
    }
}