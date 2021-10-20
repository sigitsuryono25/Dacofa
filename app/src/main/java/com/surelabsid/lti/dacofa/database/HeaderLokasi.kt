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
    val alat_tangkap: String,

    @ColumnInfo(name = "lainnya")
    val lainnya: String,

    @ColumnInfo(name = "ukuran_jaring")
    val ukuran_jaring: String,

    @ColumnInfo(name = "jumla_alat")
    val jumla_alat: String,

    @ColumnInfo(name = "id_negara")
    val id_negara: String,

    @ColumnInfo(name = "id_provinsi")
    val id_provinsi: String,

    @ColumnInfo(name = "id_kabupaten")
    val id_kabupaten: String,

    @ColumnInfo(name = "area")
    val area: String,

    @ColumnInfo(name = "lokasi")
    val lokasi: String,

    @ColumnInfo(name = "lama_operasi")
    val lama_operasi: String,

    @ColumnInfo(name = "userid")
    val userid: String
) : Parcelable