package com.surelabsid.lti.dacofa.ui.isidata.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.databinding.ActivityNegaraBinding
import com.surelabsid.lti.dacofa.network.NetworkModule
import com.surelabsid.lti.dacofa.response.ResponseNegara
import com.surelabsid.lti.dacofa.ui.isidata.adapter.AdapterListNegara
import retrofit2.Call
import retrofit2.Response

class NegaraActivity : AppCompatActivity() {
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


    private fun getListNegara() {
        NetworkModule.getService().getListNegara()
            .enqueue(object : retrofit2.Callback<ResponseNegara> {
                override fun onResponse(
                    call: Call<ResponseNegara>,
                    response: Response<ResponseNegara>
                ) {
                    val data = response.body()?.dataNegara
                    val adapterNegara = AdapterListNegara {
                        val i = Intent()
                        i.putExtra(COUNTRY_CODE, it?.alpha2)
                        i.putExtra(COUNTRY_NAME, it?.name)
                        setResult(Activity.RESULT_OK, i)
                        finish()
                    }

                    data?.let { adapterNegara.addItem(it) }
                    binding.rvNegara.apply {
                        adapter = adapterNegara
                        layoutManager = LinearLayoutManager(this@NegaraActivity)
                    }
                }

                override fun onFailure(call: Call<ResponseNegara>, t: Throwable) {
                    Toast.makeText(this@NegaraActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    companion object {
        const val COUNTRY_CODE = "countryCode"
        const val COUNTRY_NAME = "countryName"
    }
}