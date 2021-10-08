package com.surelabsid.lti.dacofa.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [HeaderLokasi::class, DetailTangkapan::class])
abstract class DatabaseApp : RoomDatabase() {
    abstract fun headerLokasiDao(): HeaderLokasiDao
    abstract fun detailTangkapanDao(): DetailTangkapanDao
}