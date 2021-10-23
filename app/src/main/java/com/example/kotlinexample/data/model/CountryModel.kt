package com.example.kotlinexample.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "tblCountry")
data class CountryModel (
	@PrimaryKey(autoGenerate = true)
	val id : Int,
	@SerializedName("area") @ColumnInfo(name = "area") val area : String?,
	@SerializedName("capital") @ColumnInfo(name = "capital") val capital : String?,
	@SerializedName("flagPNG") @ColumnInfo(name = "flagPNG") val flagPNG : String?,
	@SerializedName("name") @ColumnInfo(name = "name") val countryName : String?,
	@SerializedName("nativeName") @ColumnInfo(name = "nativeName") val nativeName : String?,
	@SerializedName("numericCode") @ColumnInfo(name = "numericCode") val numericCode : String?
)