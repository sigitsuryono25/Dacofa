package com.surelabsid.lti.dacofa.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseProvinsi(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data_prov")
	val dataProv: List<DataProvItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class DataProvItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("country_code")
	val countryCode: String? = null
) : Parcelable
