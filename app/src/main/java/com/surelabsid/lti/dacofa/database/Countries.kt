package com.surelabsid.lti.dacofa.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tb_countries")
data class Countries(

    @PrimaryKey
    var alpha_2: String,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "calling_code")
    var calling_code: String?,

    @ColumnInfo(name = "alpha_3")
    var alpha_3: String?,

    @ColumnInfo(name = "currencies")
    var currencies: String?

) : Parcelable