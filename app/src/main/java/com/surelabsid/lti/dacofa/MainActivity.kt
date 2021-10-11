package com.surelabsid.lti.dacofa

import android.content.Intent
import android.os.Bundle
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.databinding.ActivityMainBinding
import com.surelabsid.lti.dacofa.ui.isidata.IsiDataActivity
import com.surelabsid.lti.dacofa.ui.landing.LandingActivity


class MainActivity : Baseapp() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.indonesia.setOnClickListener {
            reloadActivity("id")
        }

        binding.english.setOnClickListener {
            reloadActivity("en")
        }

        binding.masuk.setOnClickListener {
            with(Intent(this, LandingActivity::class.java)) {
                startActivity(this)
            }
        }
    }


}