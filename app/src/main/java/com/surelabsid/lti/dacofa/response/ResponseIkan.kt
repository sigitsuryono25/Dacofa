package com.surelabsid.lti.dacofa.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseIkan(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data_ikan")
	val dataIkan: List<DataIkanItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class DataIkanItem(

	@field:SerializedName("nama_ikan")
	val namaIkan: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("added_on")
	val addedOn: String? = null
) : Parcelable
