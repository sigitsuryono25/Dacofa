package com.surelabsid.lti.dacofa.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [HeaderLokasi::class, DetailTangkapan::class,
        Countries::class, WilayahProvinsi::class, Fish::class,
        WilayahKabupaten::class, Fishinggear::class]
)
abstract class DatabaseApp : RoomDatabase() {
    abstract fun headerLokasiDao(): HeaderLokasiDao
    abstract fun detailTangkapanDao(): DetailTangkapanDao
    abstract fun daftarNegaraDao(): CountriesDao
    abstract fun daftarProvinsiDao(): WilayahProvinsiDao
    abstract fun daftarKabupatenDao(): WilayahKabupatenDao
    abstract fun daftarFishingGearDao(): FishinggearDao
    abstract fun daftarIkanDao(): FishDao
}