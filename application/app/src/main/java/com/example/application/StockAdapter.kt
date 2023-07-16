package com.example.application

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class StockAdapter (activity: Activity, private val resourceId: Int, data: List<Stock>):
    ArrayAdapter<Stock>(activity,resourceId,data){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(resourceId,parent,false)
        val dm : TextView = view.findViewById(R.id.dm)
        val mc : TextView = view.findViewById(R.id.mc)
        val jys : TextView = view.findViewById(R.id.jys)
        val stock = getItem(position)
        if (stock != null){
            dm.text=stock.dm
            mc.text=stock.mc
            jys.text=stock.jys
        }
        return view
    }
}