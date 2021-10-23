package com.example.kotlinexample.data.database

import com.example.kotlinexample.data.model.CountryModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseHelperImpl @Inject constructor(private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun insertCountryAll(listCountry: List<CountryModel>) = appDatabase.countryDao().insertCountryAll(listCountry)

    override suspend fun getCountryAll(countryName : String): List<CountryModel> = appDatabase.countryDao().getCountryAll(countryName)

    override suspend fun delete(countryModel: CountryModel) = appDatabase.countryDao().delete(countryModel)

    override suspend fun deleteAll() = appDatabase.countryDao().deleteAll()
}