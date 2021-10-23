package com.example.kotlinexample.data.api

import com.example.kotlinexample.data.model.CountryModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("DevTides/countries/master/countriesV2.json")
    suspend fun apiGetCountries() : Response<List<CountryModel>>

}