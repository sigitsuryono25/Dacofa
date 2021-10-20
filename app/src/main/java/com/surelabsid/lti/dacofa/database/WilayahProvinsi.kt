package com.surelabsid.lti.dacofa.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "wilayah_provinsi")
data class WilayahProvinsi(
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = "nama")
    var nama: String?,

    @ColumnInfo(name = "country_code")
    var country_code: String?
) : Parcelable