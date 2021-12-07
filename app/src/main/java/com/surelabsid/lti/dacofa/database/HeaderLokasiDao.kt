package com.surelabsid.lti.dacofa.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HeaderLokasiDao {
    @Query("SELECT * FROM tb_header_lokasi ORDER BY tanggal DESC")
    fun getAllHeader(): List<HeaderLokasi>

    @Query("SELECT * FROM tb_header_lokasi WHERE id IN (:id)")
    fun getAllHeaderById(id: String): List<HeaderLokasi>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(vararg headerLokasi: HeaderLokasi)

    @Query("DELETE FROM tb_header_lokasi WHERE id = :idLokasi")
    fun deleteHeader(idLokasi: String): Int

    @Query("DELETE FROM tb_header_lokasi")
    fun clearData()
}