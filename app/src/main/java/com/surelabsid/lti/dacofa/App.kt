package com.surelabsid.lti.dacofa

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.surelabsid.lti.dacofa.database.DatabaseApp
import com.surelabsid.lti.dacofa.utils.LocalizationUtil
import java.util.*

lateinit var db: DatabaseApp

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(baseContext, DatabaseApp::class.java, "db_dacofa").build()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocalizationUtil.applyLang(newBase, Locale(LANGUAGE)))
    }

    override fun getApplicationContext(): Context {
        val context = super.getApplicationContext()
        return LocalizationUtil.applyLang(context, Locale(LANGUAGE))
    }

    companion object{
        var LANGUAGE = "en"
    }
}