package com.example.application



interface HttpCallbackListener {
    fun onFinish(response: String)
    fun onError(e:java.lang.Exception)
}


