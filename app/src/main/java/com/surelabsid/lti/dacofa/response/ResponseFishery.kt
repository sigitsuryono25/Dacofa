package com.surelabsid.lti.dacofa.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseFishery(

	@field:SerializedName("data_fishery")
	val dataFishery: List<DataFisheryItem?>? = null
) : Parcelable

@Parcelize
data class DetailItem(

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

@Parcelize
data class DataFisheryItem(

	@field:SerializedName("area")
	val area: String? = null,

	@field:SerializedName("lama_operasi")
	val lamaOperasi: String? = null,

	@field:SerializedName("userid")
	val userid: String? = null,

	@field:SerializedName("id_provinsi")
	val idProvinsi: String? = null,

	@field:SerializedName("ukuran_jaring")
	val ukuranJaring: String? = null,

	@field:SerializedName("id_negara")
	val idNegara: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("lokasi")
	val lokasi: String? = null,

	@field:SerializedName("jumla_alat")
	val jumlaAlat: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("alat_tangkap")
	val alatTangkap: String? = null,

	@field:SerializedName("detail")
	val detail: List<DetailItem?>? = null,

	@field:SerializedName("lainnya")
	val lainnya: String? = null,

	@field:SerializedName("id_kabupaten")
	val idKabupaten: String? = null
) : Parcelable
