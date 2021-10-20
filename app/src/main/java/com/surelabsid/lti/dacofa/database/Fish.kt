package com.surelabsid.lti.dacofa.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tb_ikan")
data class Fish(

    @PrimaryKey
    var id: String,

    @ColumnInfo(name = "nama_ikan")
    var nama_ikan: String,

    @ColumnInfo(name = "state")
    var state: String,

    @ColumnInfo(name = "added_on")
    var added_on: String

): Parcelable