package com.surelabsid.lti.dacofa.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseUser(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data_user")
	val dataUser: DataUser? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class DataUser(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("userid")
	val userid: String? = null
) : Parcelable
