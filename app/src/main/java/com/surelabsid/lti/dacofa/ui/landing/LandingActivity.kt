package com.surelabsid.lti.dacofa.ui.landing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.surelabsid.lti.dacofa.databinding.ActivityLandingBinding
import com.surelabsid.lti.dacofa.ui.isidata.IsiDataActivity

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.isiData.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    IsiDataActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }
}