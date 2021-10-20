package com.surelabsid.lti.dacofa.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tb_detail_tangkapan")
data class DetailTangkapan(

    @PrimaryKey
    val id_detail: String,

    @ColumnInfo(name = "id_header")
    val id_header: String,

    @ColumnInfo(name = "id_ikan")
    val id_ikan: String,

    @ColumnInfo(name = "harga")
    val harga: Int = 0,

    @ColumnInfo(name = "total_tangkapan")
    val total_tangkapan: String,

    @ColumnInfo(name = "peruntukan")
    val peruntukan: String,

    @ColumnInfo(name = "mata_uang")
    val mata_uang: String
): Parcelable