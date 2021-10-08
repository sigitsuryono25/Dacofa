package com.surelabsid.lti.dacofa.ui.isidata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.network.NetworkModule
import com.surelabsid.lti.dacofa.response.DataKabItem
import com.surelabsid.lti.dacofa.response.ResponseKabupaten
import com.surelabsid.lti.dacofa.ui.isidata.adapter.AdapterListKabupaten
import kotlinx.android.synthetic.main.activity_kabupaten.*
import retrofit2.Call
import retrofit2.Response

class KabupatenActivity : AppCompatActivity() {

    private lateinit var adapter: AdapterListKabupaten
    private var mListKabupaten: List<DataKabItem?>? = null
    private var idProvinsi: String? = null
    private var idKabupaten: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kabupaten)

        supportActionBar?.apply {
            title = "Pilih Kabupaten"
            setDisplayHomeAsUpEnabled(true)
        }

        adapter = AdapterListKabupaten {
            idKabupaten = it?.id
            getData()
        }
        rvKabupaten.adapter = this.adapter
        rvKabupaten.layoutManager = LinearLayoutManager(this)

        idProvinsi = intent.getStringExtra(ID_PROVINSI)
        idProvinsi?.let {
            getKab(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                getData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData() {
        val intentData = Intent()
        intentData.putExtra(SELECTED_PROV, idProvinsi)
        intentData.putExtra(SELECTED_KAB, idKabupaten)
        setResult(Activity.RESULT_OK, intentData)
        finish()
    }


    private fun getKab(idProvinsi: String) {
        NetworkModule.getService().getListKabupaten(idProvinsi)
            .enqueue(object : retrofit2.Callback<ResponseKabupaten> {
                override fun onResponse(
                    call: Call<ResponseKabupaten>,
                    response: Response<ResponseKabupaten>
                ) {
                    mListKabupaten = response.body()?.dataKab
                    mListKabupaten?.let {
                        adapter.addItem(it)
                    }
                }

                override fun onFailure(call: Call<ResponseKabupaten>, t: Throwable) {
                    Toast.makeText(this@KabupatenActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    companion object {
        const val ID_PROVINSI = "idProvinsi"
        const val SELECTED_PROV = "selectedProv"
        const val SELECTED_KAB = "selectedKab"
    }
}