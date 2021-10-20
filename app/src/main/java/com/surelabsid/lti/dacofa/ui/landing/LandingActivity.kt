package com.surelabsid.lti.dacofa.ui.landing

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.databinding.ActivityLandingBinding
import com.surelabsid.lti.dacofa.ui.isidata.IsiDataActivity
import com.surelabsid.lti.dacofa.ui.lihatdata.LihatDataActivity

class LandingActivity : Baseapp() {
    private lateinit var binding: ActivityLandingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bg.ok.visibility = View.GONE
        binding.bg.back.visibility = View.GONE
        binding.isiData.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    IsiDataActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

        binding.lihatData.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    LihatDataActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }

        binding.bg.logout.setOnClickListener {
            logout()
        }
    }
}