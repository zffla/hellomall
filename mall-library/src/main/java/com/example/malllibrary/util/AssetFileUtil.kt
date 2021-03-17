package com.example.malllibrary.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * 获取Asset目录下的资源文件
 */
class AssetFileUtil {

    companion object{
        fun getAssetFileStr(fileName:String,context: Context):String{
            val stringBuilder=StringBuilder()
            try {
                val inputStream=context.assets.open(fileName)
                val br=BufferedReader(InputStreamReader(inputStream,"UTF-8"))
                var line:String
                while (br.readLine().also { line=it }!=null){
                    stringBuilder.append(line)
                }
                inputStream.close()
                br.close()
            }catch (e:Exception){
                e.printStackTrace()
            }
            return stringBuilder.toString()
        }
    }
}