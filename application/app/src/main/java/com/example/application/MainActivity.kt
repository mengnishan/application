package com.example.application

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)
        HttpUtil.sendHttpRequest(object :HttpCallbackListener{
            //用回调函数接受网络请求数据
            override fun onFinish(stringdata:String) {
                val stocklist = parseJSONWithJSONObject(stringdata)//对数据进行处理
                try {
                    runOnUiThread{
                        //在主线程中加载UI界面
                        val adapter = StockAdapter(this@MainActivity, R.layout.stock_item,  stocklist)
                        findViewById<ListView>(R.id.listView).adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }catch (e:java.lang.Exception){
                    e.printStackTrace()
                }
            }
            override fun onError(e: Exception) {
            }
        })
    }
}