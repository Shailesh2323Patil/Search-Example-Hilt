package com.example.kotlinexample.data.repository

import com.example.kotlinexample.data.api.ApiHelper
import javax.inject.Inject

class RepositoryCountry @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun apiGetCountries() = apiHelper.apiGetCountries()
}