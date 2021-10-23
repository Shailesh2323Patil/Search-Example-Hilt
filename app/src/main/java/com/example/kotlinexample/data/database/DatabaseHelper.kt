package com.example.kotlinexample.data.database

import com.example.kotlinexample.data.model.CountryModel
import javax.inject.Singleton

interface DatabaseHelper {
    suspend fun insertCountryAll(listCountry : List<CountryModel>)

    suspend fun getCountryAll(countryName : String) : List<CountryModel>

    suspend fun delete(countryModel: CountryModel)

    suspend fun deleteAll()
}
