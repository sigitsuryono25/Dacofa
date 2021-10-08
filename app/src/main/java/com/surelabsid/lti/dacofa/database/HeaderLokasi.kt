package com.surelabsid.lti.dacofa.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_header_lokasi")
data class HeaderLokasi(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "id_negara")
    val negara: String,

    @ColumnInfo(name = "id_provinsi")
    val provinsi: String,

    @ColumnInfo(name = "id_kabupaten")
    val kabupaten: String,

    @ColumnInfo(name = "alat_tangkap")
    val alatTangkap: String,

    @ColumnInfo(name = "lama_operasi")
    val lamaOperasi: String,

    @ColumnInfo(name = "userid")
    val userid: String
)