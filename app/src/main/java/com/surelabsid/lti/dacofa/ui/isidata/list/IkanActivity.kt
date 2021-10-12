package com.surelabsid.lti.dacofa.ui.isidata.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.databinding.ActivityIkanBinding
import com.surelabsid.lti.dacofa.network.NetworkModule
import com.surelabsid.lti.dacofa.response.ResponseIkan
import com.surelabsid.lti.dacofa.ui.isidata.adapter.AdapterListIkan
import retrofit2.Call
import retrofit2.Response

class IkanActivity : Baseapp() {
    private lateinit var binding: ActivityIkanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIkanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title =  getString(R.string.choose_fish_name)
            setDisplayHomeAsUpEnabled(true)
        }

        this.getListIkan()
    }


    private fun getListIkan() {
        NetworkModule.getService().getListIkan()
            .enqueue(object : retrofit2.Callback<ResponseIkan> {
                override fun onResponse(
                    call: Call<ResponseIkan>,
                    response: Response<ResponseIkan>
                ) {
                    val data = response.body()?.dataIkan
                    val adapterIkan = AdapterListIkan {
                        val i = Intent()
                        i.putExtra(ID_IKAN, it?.id)
                        i.putExtra(NAMA_IKAN, it?.namaIkan)
                        setResult(Activity.RESULT_OK, i)
                        finish()
                    }

                    data?.let { adapterIkan.addItem(it) }
                    binding.rvIkan.apply {
                        adapter = adapterIkan
                        layoutManager = LinearLayoutManager(this@IkanActivity)
                    }
                }

                override fun onFailure(call: Call<ResponseIkan>, t: Throwable) {
                    showMessage(t.message.toString())
                }

            })
    }

    companion object {
        const val ID_IKAN = "idIkan"
        const val NAMA_IKAN = "namaIkan"
    }
}