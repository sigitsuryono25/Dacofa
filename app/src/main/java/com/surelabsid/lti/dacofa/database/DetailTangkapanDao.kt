package com.surelabsid.lti.dacofa.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DetailTangkapanDao {
    @Query("SELECT * FROM tb_detail_tangkapan WHERE id_header = :idHeader")
    fun getAllTangakapanByIdHeader(idHeader: String): List<DetailTangkapan>


    @Query("SELECT * FROM tb_detail_tangkapan WHERE id_detail = :idDetail")
    fun getAllTangakapanByIdDetail(idDetail: String): List<DetailTangkapan>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(vararg headerLokasi: DetailTangkapan)

    @Query("DELETE FROM tb_detail_tangkapan")
    fun clearData()
}
