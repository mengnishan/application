package com.example.application

import android.database.Cursor

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
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

                val dbHelper = MyDatabaseHelper(this@MainActivity,"Stock.db",1)
                dbHelper.writableDatabase
                val db = dbHelper.writableDatabase
                //判断数据库中是否有数据
                val cursor = db.query("Stock",null,null,null,null,null,null)
                if (cursor.moveToFirst() ) {
                    // 数据库中有数据，不需要从网络获取数据
                }else{
                    //Cursor 为空，即数据库中没有数据，从网络获取数据存入数据库
                    for (i in stocklist){
                        val values = ContentValues().apply {
                            put("dm",i.dm)
                            put("mc",i.mc)
                            put("jys",i.jys)
                        }
                        db.insert("Stock",null,values)
                    }
                }
                
                try {
                    runOnUiThread{
                        var stockList = ArrayList<Stock>()
                        //获取数据库里的内容装进stockList中
                        val cursor = db.query("Stock",null,null,null,null,null,null)
                        if (cursor.moveToFirst()){
                            do{
                                var dm : String = ""
                                var mc : String = ""
                                var jys : String = ""
                                var columnIndex = cursor.getColumnIndex("dm")
                                if (columnIndex >= 0) {
                                     dm = cursor.getString(columnIndex)
                                }
                                 columnIndex = cursor.getColumnIndex("mc")
                                if (columnIndex >= 0) {
                                   mc = cursor.getString(columnIndex)
                                }
                                 columnIndex = cursor.getColumnIndex("jys")
                                if (columnIndex >= 0) {
                                     jys = cursor.getString(columnIndex)
                                }
                                var tem : Stock = Stock(dm,mc,jys)
                                stockList.add(tem)
                            }while (cursor.moveToNext())
                            cursor.close()
                        }
                        db.close()

                        //在主线程中加载UI界面
                        val adapter = StockAdapter(this@MainActivity, R.layout.stock_item, stockList )
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
