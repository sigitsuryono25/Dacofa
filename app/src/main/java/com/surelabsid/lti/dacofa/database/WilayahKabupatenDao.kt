package com.surelabsid.lti.dacofa.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface WilayahKabupatenDao {

    @Query("SELECT * FROM wilayah_kabupaten")
    fun getAllKabupaten(): List<WilayahKabupaten>

    @Insert(onConflict = REPLACE)
    fun insertKabupaten(vararg wilayahKabupaten: WilayahKabupaten)

    @Query("SELECT * FROM wilayah_kabupaten WHERE provinsi_id IN(:provinsiId)")
    fun getKabupatenByProvinsi(provinsiId: String): List<WilayahKabupaten>

    @Query("DELETE FROM wilayah_kabupaten")
    fun nukeTable(): Int
}