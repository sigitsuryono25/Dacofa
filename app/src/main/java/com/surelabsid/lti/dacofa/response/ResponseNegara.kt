package com.surelabsid.lti.dacofa.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseNegara(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data_negara")
	val dataNegara: List<DataNegaraItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class DataNegaraItem(

	@field:SerializedName("alpha_3")
	val alpha3: String? = null,

	@field:SerializedName("calling_code")
	val callingCode: String? = null,

	@field:SerializedName("alpha_2")
	val alpha2: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("currencies")
	val currencies: String? = null
) : Parcelable
