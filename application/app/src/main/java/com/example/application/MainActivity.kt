package com.example.application

import android.os.Bundle
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_show_list.*

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
                        //搜索内容
                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(p0: String?): Boolean {
                                return false
                            }
                            override fun onQueryTextChange(p0: String?): Boolean {
                                //通过首字符筛选内容
                                adapter.filter.filter(p0)
                                onQueryTextSubmit(p0)
                                return false
                            }
                        })
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
