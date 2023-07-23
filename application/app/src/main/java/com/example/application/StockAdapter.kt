package com.example.application

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*

class StockAdapter(activity: Activity, private val resourceId: Int, data: List<Stock>) :
    ArrayAdapter<Stock>(activity, resourceId, data), Filterable {

    private val originalData: List<Stock> = data
    private val newData = originalData.toList()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(resourceId, parent, false)
        val dm: TextView = view.findViewById(R.id.dm)
        val mc: TextView = view.findViewById(R.id.mc)
        val jys: TextView = view.findViewById(R.id.jys)
        val stock = getItem(position)
        if (stock != null) {
            dm.text = stock.dm
            mc.text = stock.mc
            jys.text = stock.jys
        }
        return view
    }

    //过滤器配置
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredData = mutableListOf<Stock>()
                if (constraint.isNullOrBlank()) {
                    filteredData.addAll(newData)
                } else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()
                    for (stock in newData) {
                        if (stock.mc.toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                            filteredData.add(stock)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredData
                results.count = filteredData.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clear()
                if (results != null && results.count > 0) {
                    addAll(results.values as List<Stock>)
                } else {
                    addAll(newData)

                }
                notifyDataSetChanged()
            }
        }
    }
}
