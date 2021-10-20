package com.surelabsid.lti.dacofa.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WilayahProvinsiDao {

    @Query("SELECT * FROM wilayah_provinsi")
    fun getAllProvinsi(): List<WilayahProvinsi>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProvinsi(vararg wilayahProvinsi: WilayahProvinsi)

    @Query("SELECT * FROM wilayah_provinsi WHERE country_code IN(:id)")
    fun getProvinsiByProvinsi(id: String?): List<WilayahProvinsi>

    @Query("DELETE FROM wilayah_Provinsi")
    fun nukeTable(): Int
}