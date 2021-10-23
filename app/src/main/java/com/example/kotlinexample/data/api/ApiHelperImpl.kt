package com.example.kotlinexample.data.api

import com.example.kotlinexample.data.model.CountryModel
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun apiGetCountries(): Response<List<CountryModel>> = apiService.apiGetCountries()
}