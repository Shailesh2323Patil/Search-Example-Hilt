package com.example.kotlinexample.data.database

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context) : AppDatabase {
        if(INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoom(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoom(context: Context)  =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "KotlinPractise"
        ).build()
}