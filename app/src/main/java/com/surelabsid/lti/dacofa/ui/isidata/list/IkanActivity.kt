package com.surelabsid.lti.dacofa.ui.isidata.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.databinding.ActivityIkanBinding
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.ui.isidata.adapter.AdapterListIkan
import org.jetbrains.anko.doAsync

class IkanActivity : Baseapp() {
    private lateinit var binding: ActivityIkanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIkanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.choose_fish_name)
            setDisplayHomeAsUpEnabled(true)
        }

        this.getListIkan()
    }


    private fun getListIkan() {
        val adapterIkan = AdapterListIkan {
            val i = Intent()
            i.putExtra(ID_IKAN, it?.id)
            i.putExtra(NAMA_IKAN, it?.nama_ikan)
            setResult(Activity.RESULT_OK, i)
            finish()
        }
        binding.rvIkan.apply {
            adapter = adapterIkan
            layoutManager = LinearLayoutManager(this@IkanActivity)
        }
        doAsync {
            val data = db.daftarIkanDao().getAllFish()
            adapterIkan.addItem(data)
        }
    }

    companion object {
        const val ID_IKAN = "idIkan"
        const val NAMA_IKAN = "namaIkan"
    }
}