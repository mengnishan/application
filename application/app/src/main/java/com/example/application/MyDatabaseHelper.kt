package com.example.application

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper (val context: Context,name : String,version:Int):
    SQLiteOpenHelper (context,name,null,version){

    private val createStock = "create table Stock (" +
            "id integer primary key autoincrement,"+
            "dm text,"+
            "mc text,"+
            "jys text)"


    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL(createStock)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}