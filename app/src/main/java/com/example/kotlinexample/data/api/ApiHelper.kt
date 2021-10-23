package com.example.kotlinexample.data.api

import com.example.kotlinexample.data.model.CountryModel
import retrofit2.Response

interface ApiHelper {

    suspend fun apiGetCountries() : Response<List<CountryModel>>

}