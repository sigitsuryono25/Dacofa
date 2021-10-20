package com.surelabsid.lti.dacofa.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "wilayah_kabupaten")
data class WilayahKabupaten(

    @PrimaryKey
    var id: String,

    @ColumnInfo(name = "provinsi_id")
    var provinsi_id: String?,

    @ColumnInfo(name = "nama")
    var nama: String?,
) : Parcelable