package com.surelabsid.lti.dacofa

import android.app.Application
import android.content.res.Configuration
import androidx.room.Room
import com.surelabsid.lti.dacofa.database.DatabaseApp

lateinit var db: DatabaseApp

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(baseContext, DatabaseApp::class.java, "db_dacofa").build()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

    }
}