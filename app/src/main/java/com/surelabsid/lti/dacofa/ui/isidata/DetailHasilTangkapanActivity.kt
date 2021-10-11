package com.surelabsid.lti.dacofa.ui.isidata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.database.DetailTangkapan
import com.surelabsid.lti.dacofa.databinding.ActivityDetailHasilTangkapanBinding
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.ui.isidata.list.IkanActivity
import org.jetbrains.anko.doAsyncResult

class DetailHasilTangkapanActivity : Baseapp() {
    private lateinit var binding: ActivityDetailHasilTangkapanBinding
    private var namaIkan: String? = null
    private var listIdDetail = mutableListOf<String>()
    private var initIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHasilTangkapanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("data")
        val headerId = bundle?.getString(IsiDataActivity.HEADER_ID)

        binding.previousData.setOnClickListener {
            this.getPreviousData()
        }
        binding.entryNextData.setOnClickListener {
//            val getNextData = this.getNextData()
//            if (!getNextData)
                insertData(headerId)

        }

        binding.namaIkan.setOnClickListener {
            Intent(this, IkanActivity::class.java).apply {
                startActivityForResult(this, IKAN_REQ)
            }
        }
    }

    private fun getPreviousData() {
        if (initIndex != 0) {
            var detail: List<DetailTangkapan> = listOf()
            val idDetail = listIdDetail[--initIndex]
            doAsyncResult({
                showMessage(it.message.toString())
            }, {
                detail = db.detailTangkapanDao().getAllTangakapanByIdDetail(idDetail)
            })

            detail.map {
                binding.namaIkan.text = it.idIkan
                binding.totalTangkapan.setText(it.totalTangkapan)
                binding.harga.setText(it.harga)
            }
        } else {
            showMessage("Tidak ada data yang ditampilkan")
        }
    }

    private fun getNextData(): Boolean {
        if (initIndex != 0) {
            var detail: List<DetailTangkapan> = listOf()
            val idDetail = listIdDetail[initIndex++]
            doAsyncResult({
                showMessage(it.message.toString())
            }, {
                detail = db.detailTangkapanDao().getAllTangakapanByIdDetail(idDetail)
            })

            detail.map {
                binding.namaIkan.text = it.idIkan
                binding.totalTangkapan.setText(it.totalTangkapan)
                binding.harga.setText(it.harga)
            }
            return true
        } else {
            showMessage("Tidak ada data yang ditampilkan")
            return false
        }
    }

    private fun insertData(headerId: String?) {
        if (binding.namaIkan.text.toString().isEmpty() || binding.totalTangkapan.text.toString()
                .isEmpty()
            || binding.harga.text.toString().isEmpty()
        ) {
            showMessage(getString(R.string.please_fill_all))
            return
        }

        val idDetail = System.currentTimeMillis().toString()

        val detailTangkapan = DetailTangkapan(
            id_detail = idDetail,
            idHeader = headerId.toString(),
            idIkan = namaIkan.toString(),
            totalTangkapan = binding.totalTangkapan.text.toString(),
            mataUang = binding.spinnerCountry.selectedItem.toString(),
            harga = binding.harga.text.toString()
        )

        val ins = insertDetailTangkapan(detailTangkapan)
        if (ins) {
            listIdDetail.add(idDetail)
            initIndex++
            showMessage("Data berhasil disimpan")

            binding.namaIkan.text = ""
            binding.totalTangkapan.setText("")
            binding.harga.setText("")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IKAN_REQ && resultCode == Activity.RESULT_OK) {
            namaIkan = data?.getStringExtra(IkanActivity.NAMA_IKAN)
            binding.namaIkan.text = namaIkan
        }
    }

    companion object {
        const val IKAN_REQ = 1025
    }
}