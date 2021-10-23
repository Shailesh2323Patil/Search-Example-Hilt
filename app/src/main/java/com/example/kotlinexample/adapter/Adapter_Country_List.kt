package com.example.kotlinexample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinexample.R
import com.example.kotlinexample.data.model.CountryModel
import com.example.kotlinexample.databinding.RowCountryBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Adapter_Country_List @Inject constructor(@ApplicationContext var context : Context)
    : RecyclerView.Adapter<Adapter_Country_List.ViewHolder>() {

    private val callBack = object : DiffUtil.ItemCallback<CountryModel>() {
        override fun areItemsTheSame(oldItem: CountryModel, newItem: CountryModel): Boolean {
            return oldItem.countryName == newItem.countryName
        }

        override fun areContentsTheSame(oldItem: CountryModel, newItem: CountryModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater : LayoutInflater = LayoutInflater.from(parent.context)
        var rowCountryBinding : RowCountryBinding =
            DataBindingUtil.inflate(inflater, R.layout.row_country,parent,false)
        return ViewHolder(rowCountryBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var countryModel = differ.currentList[position]

        holder.bind(countryModel)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder constructor(var binding : RowCountryBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(countryModel: CountryModel) {
            binding.txtCountry.text = countryModel.countryName
            binding.txtCapital.text = countryModel.capital

            Glide.with(context)
                .load(countryModel.flagPNG)
                .into(binding.img)
        }
    }

    private var onItemClickListner : ((CountryModel) -> Unit)? = null
    fun setOnItenClickListner(listner : (CountryModel) -> Unit) {
        onItemClickListner = listner
    }
}