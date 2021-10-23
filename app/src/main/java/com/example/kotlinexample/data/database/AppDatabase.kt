package com.example.kotlinexample.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlinexample.data.model.CountryModel

@Database(entities = [CountryModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao() : CountryDao
}