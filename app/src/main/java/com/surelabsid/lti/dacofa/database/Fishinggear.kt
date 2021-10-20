package com.surelabsid.lti.dacofa.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tb_fishing_gear")
data class Fishinggear(

    @PrimaryKey
    var id: String,

    @ColumnInfo(name = "nama_fishing_gear")
    var nama_fishing_gear: String,

    @ColumnInfo(name = "extras")
    var extras: String,

) : Parcelable