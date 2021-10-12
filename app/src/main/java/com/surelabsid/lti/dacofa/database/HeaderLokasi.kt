package com.surelabsid.lti.dacofa.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tb_header_lokasi")
data class HeaderLokasi(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "tanggal")
    val tanggal: String,

    @ColumnInfo(name = "alat_tangkap")
    val alatTangkap: String,

    @ColumnInfo(name = "lainnya")
    val lainnya: String,

    @ColumnInfo(name = "ukuran_jaring")
    val ukuranJaring: String,

    @ColumnInfo(name = "jumla_alat")
    val jumlaAlatTangkap: String,

    @ColumnInfo(name = "id_negara")
    val negara: String,

    @ColumnInfo(name = "id_provinsi")
    val provinsi: String,

    @ColumnInfo(name = "id_kabupaten")
    val kabupaten: String,

    @ColumnInfo(name = "area")
    val fishingArea: String,

    @ColumnInfo(name = "lokasi")
    val lokasi: String,

    @ColumnInfo(name = "lama_operasi")
    val lamaOperasi: String,

    @ColumnInfo(name = "userid")
    val userid: String
) : Parcelable