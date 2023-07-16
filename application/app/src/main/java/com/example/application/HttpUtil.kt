package com.example.application

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


object HttpUtil {
    fun sendHttpRequest(listener:HttpCallbackListener){
       thread {
            var connection :HttpURLConnection? = null
            try {
                val response = StringBuffer()
                val url = URL("http://api.mairui.club/hslt/list/b2b0a8353913f554c")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 16000
                connection.readTimeout = 16000
                val input = connection.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                    listener.onFinish(response.toString())
                //将获得的数据传入回调函数
            }catch (e:Exception){
                e.printStackTrace()
                listener.onError(e)
            }finally {
                connection?.disconnect()
            }
        }
    }
}