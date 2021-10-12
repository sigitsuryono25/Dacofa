package com.surelabsid.lti.dacofa.ui.lihatdata

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.database.HeaderLokasi
import com.surelabsid.lti.dacofa.databinding.ActivityLihatDetailBinding
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.ui.isidata.DetailHasilTangkapanActivity
import com.surelabsid.lti.dacofa.ui.isidata.IsiDataActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync

class LihatDetailActivity : Baseapp() {
    private lateinit var binding: ActivityLihatDetailBinding
    private lateinit var adapterCatchResult: AdapterCatchResult
    private var headerLokasi: HeaderLokasi? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLihatDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterCatchResult = AdapterCatchResult({
            //onedit
        }, { detail ->
            //ondelete
            alert {
                message = getString(R.string.delete_data)
                title = getString(R.string.confirm)
                positiveButton("Ok") {
                    hapusData(detail.id_detail)
                }
                negativeButton("Cancel") {
                    it.dismiss()
                }
            }.show()
        })
        binding.rvCatchResult.apply {
            adapter = adapterCatchResult
            layoutManager = LinearLayoutManager(this@LihatDetailActivity)
        }

        headerLokasi = intent.getParcelableExtra("idLokasi")

        binding.country.text = headerLokasi?.negara
        binding.tanggal.text = headerLokasi?.tanggal
        binding.provinsi.text = headerLokasi?.provinsi
        binding.kabupaten.text = headerLokasi?.kabupaten
        binding.lokasi.text = headerLokasi?.lokasi
        binding.area.text = headerLokasi?.fishingArea
        binding.alatTangkap.text = headerLokasi?.alatTangkap
        binding.ukuranJaring.text = headerLokasi?.ukuranJaring
        binding.jumlahAlatTangkap.text = headerLokasi?.jumlaAlatTangkap
        binding.lamaOperasi.text = "${headerLokasi?.lamaOperasi} ${getString(R.string.jam)}"
        binding.delete.setOnClickListener {
            alert {
                message = getString(R.string.delete_data)
                title = getString(R.string.confirm)
                positiveButton("Ok") {
                    deleteData(headerLokasi?.id.toString())
                }
                negativeButton("Cancel") {
                    it.dismiss()
                }
                isCancelable = false
            }.show()
        }

        binding.back.setOnClickListener {
            finish()
        }


        binding.add.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(IsiDataActivity.HEADER_ID, headerLokasi?.id)
            Intent(this, DetailHasilTangkapanActivity::class.java).apply {
                putExtra("data", bundle)
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getCatchResult(headerLokasi?.id.toString())
    }

    private fun deleteData(idHeader: String) {
        doAsync {
            val del = db.headerLokasiDao().deleteHeader(idHeader)
            val delData = db.detailTangkapanDao().deleteByByIdHeader(idHeader)
            if (del > 0 && delData > 0) {
                runOnUiThread {
                    showMessage("data berhasil dihapus")
                    finish()
                }
            } else {
                runOnUiThread {
                    showMessage("data gagal dihapus")
                }
            }
        }
    }

    private fun hapusData(idDetail: String) {
        doAsync {
            val del = db.detailTangkapanDao().deleteWhere(idDetail)
            if (del > 0) {
                runOnUiThread {
                    showMessage("data berhasil dihapus")
                }
            } else {
                runOnUiThread {
                    showMessage("data gagal dihapus")
                }
            }

            runOnUiThread { getCatchResult(headerLokasi?.id.toString()) }
        }
    }

    private fun getCatchResult(idLokasi: String) {
        doAsync {
            val detail = db.detailTangkapanDao().getAllTangakapanByIdHeader(idLokasi)
            runOnUiThread {
                adapterCatchResult.addItem(detail, true)
            }
        }
    }

}