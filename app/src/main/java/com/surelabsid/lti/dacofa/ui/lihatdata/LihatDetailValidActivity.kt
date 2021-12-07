package com.surelabsid.lti.dacofa.ui.lihatdata

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.database.HeaderLokasi
import com.surelabsid.lti.dacofa.databinding.ActivityLihatDetailValidBinding
import com.surelabsid.lti.dacofa.network.NetworkModule
import com.surelabsid.lti.dacofa.response.ResponseDetailTangakapan
import com.surelabsid.lti.dacofa.ui.lihatdata.adapter.AdapterDetailTangkapanValid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LihatDetailValidActivity : Baseapp() {
    private lateinit var binding: ActivityLihatDetailValidBinding
    private lateinit var adapterValid: AdapterDetailTangkapanValid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLihatDetailValidBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val headerData = intent.getParcelableExtra<HeaderLokasi>(HEADERDATA)
        getDetailTangakan(headerData)

        binding.bg.back.setOnClickListener {
            finish()
        }

        binding.bg.ok.visibility = View.GONE
        binding.bg.logout.setOnClickListener {
            logout()
        }

        adapterValid = AdapterDetailTangkapanValid()
        binding.rvDetailTangkapan.apply {
            adapter = adapterValid
            layoutManager = LinearLayoutManager(this@LihatDetailValidActivity)
        }
    }


    private fun getDetailTangakan(header: HeaderLokasi?) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val data = NetworkModule.getService().getHasilTangkapan(header?.id)
                    withContext(Dispatchers.Main) {
                        updateUI(data)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun updateUI(data: ResponseDetailTangakapan) {
        data.dataTangkapan?.let { adapterValid.addItem(it) }
    }

    companion object {
        const val HEADERDATA = "headerdata"
    }
}