package com.surelabsid.lti.dacofa.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountriesDao {

    @Query("SELECT * FROM tb_countries")
    fun getAllCountries(): List<Countries>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(vararg wilayahCountries: Countries)

    @Query("SELECT * FROM tb_countries WHERE alpha_2 IN(:alpha2)")
    fun getCountriesByProvinsi(alpha2: String): List<Countries>

    @Query("DELETE FROM tb_countries")
    fun nukeTable(): Int
}