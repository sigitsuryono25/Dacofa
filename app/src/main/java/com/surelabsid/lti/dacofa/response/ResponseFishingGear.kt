package com.surelabsid.lti.dacofa.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseFishingGear(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data_gear")
	val dataGear: List<DataGearItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class DataGearItem(

	@field:SerializedName("nama_fishing_gear")
	val namaFishingGear: String? = null,

	@field:SerializedName("extras")
	val extras: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("state")
	val state: String? = null
) : Parcelable
