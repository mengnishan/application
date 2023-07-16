package com.example.application

import org.json.JSONArray

fun parseJSONWithJSONObject(jsonData:String):ArrayList<Stock>{
    val stockList  = ArrayList<Stock>()
    try {
        val jsonArray = JSONArray(jsonData)
        for (i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            val dm = jsonObject.getString("dm")
            val mc = jsonObject.getString("mc")
            val jys = jsonObject.getString("jys")
            stockList.add(Stock(dm,mc,jys))
            //将数据以Stock实例保存在List中
        }
    }catch (e:java.lang.Exception){
        e.printStackTrace()
    }
    return stockList
}