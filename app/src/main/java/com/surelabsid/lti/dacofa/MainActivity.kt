package com.surelabsid.lti.dacofa

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        indonesia.setOnClickListener {
            this.setLocale(this@MainActivity, "en-us")
        }

        english.setOnClickListener {
            this.setLocale(this@MainActivity, "en")
        }
    }

    fun setLocale(activity: Activity, languageCode: String) {
        val languageToLoad = "en-us" // your language
        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        activity.resources.updateConfiguration(
            config,
            activity.resources.displayMetrics
        )
    }
}