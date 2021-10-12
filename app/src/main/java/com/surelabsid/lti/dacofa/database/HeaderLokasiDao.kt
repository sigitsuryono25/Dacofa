package com.surelabsid.lti.dacofa.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HeaderLokasiDao {
    @Query("SELECT * FROM tb_header_lokasi")
    fun getAllHeader(): List<HeaderLokasi>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(vararg headerLokasi: HeaderLokasi)

    @Query("DELETE FROM tb_header_lokasi WHERE id = :idLokasi")
    fun deleteHeader(idLokasi: String): Int

    @Query("DELETE FROM tb_header_lokasi")
    fun clearData()
}