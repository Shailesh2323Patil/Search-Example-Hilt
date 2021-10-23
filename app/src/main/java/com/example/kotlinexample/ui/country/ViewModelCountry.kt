package com.example.kotlinexample.ui.country

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinexample.R
import com.example.kotlinexample.data.database.DatabaseHelper
import com.example.kotlinexample.data.model.CountryModel
import com.example.kotlinexample.data.repository.RepositoryCountry
import com.example.kotlinexample.util.NetworkHelper
import com.example.kotlinexample.util.Resource
import com.example.kotlinexample.util.ResourceProvider
import kotlinx.coroutines.launch
import java.lang.Exception

class ViewModelCountry @ViewModelInject constructor(
        private val repositoryCountry: RepositoryCountry,
        private val networkHelper: NetworkHelper,
        private val resourceProvider: ResourceProvider,
        private val databaseHelper: DatabaseHelper
) : ViewModel() {

    private val _mutableCountryList = MutableLiveData<Resource<List<CountryModel>>>()
    val mutableCountryList : MutableLiveData<Resource<List<CountryModel>>> get() = _mutableCountryList

    fun apiGetCountryList() {

        viewModelScope.launch {
            _mutableCountryList.postValue(Resource.loading(null))
            try {
                if(networkHelper.isNetworkConnected()) {
                    repositoryCountry.apiGetCountries().let {
                        if (it.isSuccessful) {
                            var countryModel : List<CountryModel> = it.body() as List<CountryModel>
                            _mutableCountryList.postValue(Resource.success(countryModel))

                            insertCountryList(countryModel)
                        }
                        else {
                            _mutableCountryList.postValue(
                                    Resource.error(
                                            it.errorBody().toString(),null
                                    )
                            )
                        }
                    }
                }
                else {
                    _mutableCountryList.postValue(
                            Resource.error(resourceProvider.getString(R.string.no_internet_connection),null)
                    )
                }
            }
            catch (exception : Exception) {
                exception.printStackTrace()
                _mutableCountryList.postValue(Resource.error(
                        resourceProvider.getString(R.string.something_went_wrong),null
                ))
            }
        }
    }

    public fun fetchCountryList(country: String) {
        viewModelScope.launch {
            val countryList = databaseHelper.getCountryAll(country)
            Log.e("TAG",countryList.toString())
            _mutableCountryList.postValue(Resource.success(countryList))
        }
    }

    private fun insertCountryList(listCountryModel : List<CountryModel>) {
        viewModelScope.launch {
            databaseHelper.deleteAll()
            databaseHelper.insertCountryAll(listCountryModel)
        }
    }

}