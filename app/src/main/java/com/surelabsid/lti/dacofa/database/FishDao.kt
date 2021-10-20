package com.surelabsid.lti.dacofa.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FishDao {

    @Query("SELECT * FROM tb_ikan")
    fun getAllFish(): List<Fish>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFish(vararg fish: Fish)


    @Query("DELETE FROM tb_ikan")
    fun nukeTable(): Int
}