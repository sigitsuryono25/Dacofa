package com.surelabsid.lti.dacofa.ui.isidata.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.databinding.ActivityNegaraBinding
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.ui.isidata.adapter.AdapterListNegara
import org.jetbrains.anko.doAsync

class NegaraActivity : Baseapp() {
    private lateinit var binding: ActivityNegaraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNegaraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.choose_countries)
            setDisplayHomeAsUpEnabled(true)
        }

        this.getListNegara()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getListNegara() {
        val adapterListNegara = AdapterListNegara {
            val i = Intent()
            i.putExtra(COUNTRY_CODE, it?.alpha_2)
            i.putExtra(COUNTRY_NAME, it?.name)
            setResult(Activity.RESULT_OK, i)
            finish()
        }
        doAsync {
            val l = db.daftarNegaraDao().getAllCountries()
            adapterListNegara.addItem(l)
            runOnUiThread {
                binding.rvNegara.apply {
                    adapter = adapterListNegara
                    layoutManager = LinearLayoutManager(this@NegaraActivity)
                }
            }
        }
    }

    companion object {
        const val COUNTRY_CODE = "countryCode"
        const val COUNTRY_NAME = "countryName"
    }
}