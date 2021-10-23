package com.example.kotlinexample.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlinexample.data.model.CountryModel

@Dao
interface CountryDao {
    @Insert
    suspend fun insertCountryAll(listCountry : List<CountryModel>)

    @Query("SELECT * FROM tblCountry where name LIKE :countryName || '%' ")
    suspend fun getCountryAll(countryName : String) : List<CountryModel>

    @Delete
    suspend fun delete(countryModel: CountryModel)

    @Query("DELETE FROM tblCountry")
    suspend fun deleteAll()
}