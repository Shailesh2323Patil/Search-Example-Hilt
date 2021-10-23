package com.example.kotlinexample.ui.country


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinexample.R
import com.example.kotlinexample.R.id.btn_search
import com.example.kotlinexample.adapter.Adapter_Country_List
import com.example.kotlinexample.data.model.CountryModel
import com.example.kotlinexample.databinding.ActivityCountryBinding
import com.example.kotlinexample.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_country.*
import javax.inject.Inject

@AndroidEntryPoint
class ActivityCountry : AppCompatActivity(), View.OnClickListener {

    lateinit var binding : ActivityCountryBinding

    val viewModel : ViewModelCountry by viewModels()

    lateinit var arrayList: ArrayList<CountryModel>
    @Inject lateinit var adapter : Adapter_Country_List

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_country)

        onSetObservables()
        setRecyclerView()
        onClickListner()
        viewModel.apiGetCountryList()
    }

    private fun onSetObservables() {
        viewModel.mutableCountryList.observe(this, Observer {
            when(it.status) {
                Status.LOADING -> {
                    binding.loadingView.visibility = View.VISIBLE
                    binding.txtError.visibility = View.GONE
                    binding.recyleCountryName.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.loadingView.visibility = View.GONE
                    binding.txtError.visibility = View.GONE
                    binding.recyleCountryName.visibility = View.VISIBLE

                    var listCountryModel = it.data as ArrayList<CountryModel>

                    arrayList = listCountryModel
                    adapter.differ.submitList(arrayList)
                }
                Status.ERROR -> {
                    binding.loadingView.visibility = View.GONE
                    binding.txtError.visibility = View.VISIBLE
                    binding.recyleCountryName.visibility = View.GONE
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
    }

    fun setRecyclerView() {
        arrayList = ArrayList<CountryModel>()

        binding.recyleCountryName.adapter = adapter
        var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.recyleCountryName.layoutManager = layoutManager
        adapter.differ.submitList(arrayList)

        adapter.setOnItenClickListner {
            var countryModel = it as CountryModel
            Toast.makeText(this,"Hello",Toast.LENGTH_LONG).show()
        }
    }

    private fun onClickListner() {
        binding.btnSearch.setOnClickListener(this)
    }

    override fun onClick(view : View?) {
        when(view?.id) {
            (R.id.btn_search) -> {
                viewModel.fetchCountryList(edit_search.text.toString())
            }
        }
    }
}