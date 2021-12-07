package com.surelabsid.lti.dacofa.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseDetailTangakapan(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data_tangkapan")
	val dataTangkapan: List<DataTangkapanItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class DataTangkapanItem(

	@field:SerializedName("id_detail")
	val idDetail: String? = null,

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("id_header")
	val idHeader: String? = null,

	@field:SerializedName("mata_uang")
	val mataUang: String? = null,

	@field:SerializedName("id_ikan")
	val idIkan: String? = null,

	@field:SerializedName("total_tangkapan")
	val totalTangkapan: String? = null,

	@field:SerializedName("peruntukan")
	val peruntukan: String? = null
) : Parcelable
