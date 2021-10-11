package com.surelabsid.lti.dacofa.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_detail_tangkapan")
data class DetailTangkapan(

    @PrimaryKey
    val id_detail: String,

    @ColumnInfo(name = "id_header")
    val idHeader: String,

    @ColumnInfo(name = "id_ikan")
    val idIkan: String,

    @ColumnInfo(name = "harga")
    val harga: String,

    @ColumnInfo(name = "total_tangkapan")
    val totalTangkapan: String,

    @ColumnInfo(name = "mata_uang")
    val mataUang: String
)