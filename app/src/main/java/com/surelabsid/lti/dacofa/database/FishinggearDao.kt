package com.surelabsid.lti.dacofa.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface FishinggearDao {

    @Query("SELECT * FROM tb_fishing_gear")
    fun getAllFishingGear(): List<Fishinggear>

    @Insert(onConflict = REPLACE)
    fun insertFishingGear(vararg fishinggear: Fishinggear)

    @Query("DELETE FROM tb_fishing_gear")
    fun nukeTable(): Int
}