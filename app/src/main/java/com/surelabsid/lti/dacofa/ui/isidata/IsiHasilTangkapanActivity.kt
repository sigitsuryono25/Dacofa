package com.surelabsid.lti.dacofa.ui.isidata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.database.DetailTangkapan
import com.surelabsid.lti.dacofa.databinding.ActivityDetailHasilTangkapanBinding
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.ui.isidata.list.IkanActivity
import com.surelabsid.lti.dacofa.ui.lihatdata.AdapterCatchResult
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.toast

class IsiHasilTangkapanActivity : Baseapp() {
    private lateinit var binding: ActivityDetailHasilTangkapanBinding
    private var namaIkan: String? = null
    private var listIdDetail = mutableListOf<String>()
    private var initIndex = 0
    private var isViewer = false
    private lateinit var adapter: ArrayAdapter<String>
    private var mode = "add"
    private var headerId: String? = null
    private var idDetail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHasilTangkapanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("data")
        headerId = bundle?.getString(IsiDataActivity.HEADER_ID)
        val dataTangkapan = intent.getParcelableExtra<DetailTangkapan>(DATA_TANGKAPAN)
        val modeData = intent.getStringExtra(MODE)

        binding.previousData.setOnClickListener {
            this.getPreviousData()
        }
        binding.entryNextData.setOnClickListener {
            if (!isViewer)
                insertData(headerId)
            else
                this.getNextData()
        }

        binding.namaIkan.setOnClickListener {
            Intent(this, IkanActivity::class.java).apply {
                startActivityForResult(this, IKAN_REQ)
            }
        }

        binding.bg.ok.text = "Selesai"
        binding.bg.ok.setOnClickListener {
            finish()
        }

        binding.bg.logout.setOnClickListener {
            logout()
        }

        binding.peruntukan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 1) {
                    binding.hargaRow.visibility = View.VISIBLE
                } else {
                    binding.hargaRow.visibility = View.GONE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        binding.resetForm.setOnClickListener {
            resetForm()
            mode = "add"
        }
        modeData.let {
            if (it == "edit") {
                mode = "edit"
                setDataToView(dataTangkapan)
            }
        }

        getData()
    }

    private fun setDataToView(dataTangkapan: DetailTangkapan?) {
        binding.namaIkan.text = dataTangkapan?.id_ikan
        binding.totalTangkapan.setText(dataTangkapan?.total_tangkapan)
        binding.harga.setText(dataTangkapan?.harga.toString())
        val adp = binding.peruntukan.adapter as ArrayAdapter<String>
        val sl = adp.getPosition(dataTangkapan?.peruntukan)
        binding.peruntukan.setSelection(sl)
        adapter = binding.spinnerCountry.adapter as ArrayAdapter<String>
        val selection = adapter.getPosition(dataTangkapan?.mata_uang)
        binding.spinnerCountry.setSelection(selection)

        headerId = dataTangkapan?.id_header
        idDetail = dataTangkapan?.id_detail
    }

    private fun getPreviousData() {
        if (initIndex != 0) {
            var detail: List<DetailTangkapan>
            val idDetail = listIdDetail[--initIndex]
            toast(idDetail)
            doAsyncResult({
                showMessage(it.message.toString())
            }, {
                detail = db.detailTangkapanDao().getAllTangakapanByIdDetail(idDetail)
                runOnUiThread {
                    detail.map {
                        binding.namaIkan.text = it.id_ikan
                        binding.totalTangkapan.setText(it.total_tangkapan)
//                        binding.harga.setText(it.harga)
                    }
                }
            })
        } else {
            showMessage("Tidak ada data yang ditampilkan")
        }
        isViewer = true
    }

    private fun getNextData() {
        toast((initIndex++).toString())
        if (initIndex >= 0 && initIndex < listIdDetail.size) {
            var detail: List<DetailTangkapan>
            val idDetail = listIdDetail[initIndex]
            doAsyncResult({
                showMessage(it.message.toString())
            }, {
                detail = db.detailTangkapanDao().getAllTangakapanByIdDetail(idDetail)
                runOnUiThread {
                    detail.map {
                        binding.namaIkan.text = it.id_ikan
                        binding.totalTangkapan.setText(it.total_tangkapan)
                        binding.harga.setText(it.harga)
                    }
                }
            })
        } else {
            showMessage("Tidak ada data yang ditampilkan")
            this.resetForm()
            isViewer = false
        }
    }

    private fun insertData(headerId: String?) {
        if (binding.namaIkan.text.toString().isEmpty() || binding.totalTangkapan.text.toString()
                .isEmpty()
            || (binding.peruntukan.selectedItem.toString()
                .equals("Dijual") && binding.harga.text.toString().isEmpty())
        ) {
            showMessage(getString(R.string.please_fill_all))
            return
        }
        if (mode != "edit") {
            idDetail = System.currentTimeMillis().toString()
        }
        val detailTangkapan = DetailTangkapan(
            id_detail = idDetail.toString(),
            id_header = headerId.toString(),
            id_ikan = binding.namaIkan.text.toString(),
            total_tangkapan = binding.totalTangkapan.text.toString(),
            mata_uang = binding.spinnerCountry.selectedItem.toString(),
            harga = if (binding.harga.text.toString() == "") {
                0
            } else {
                binding.harga.text.toString().toInt()
            },
            peruntukan = binding.peruntukan.selectedItem.toString()
        )

        val ins = insertDetailTangkapan(detailTangkapan)
        if (ins) {
            listIdDetail.add(idDetail.toString())
            initIndex++
            showMessage("Data berhasil disimpan")
            this.resetForm()
        }
    }


    private fun resetForm() {
        binding.namaIkan.text = ""
        binding.totalTangkapan.setText("")
        binding.harga.setText("")
        this.getData()
    }

    private fun getData() {
        val adapterCatchResult = AdapterCatchResult({
            mode = "edit"
            setDataToView(it)
        }, {detail->
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

        binding.rvCatchResult.adapter = adapterCatchResult
        binding.rvCatchResult.layoutManager = LinearLayoutManager(this)
        doAsync {
            val listCatch = db.detailTangkapanDao().getAllTangakapanByIdHeader(headerId.toString())
            runOnUiThread {
                adapterCatchResult.addItem(listCatch)
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

            runOnUiThread { getData() }
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
        const val DATA_TANGKAPAN = "dataTangkapan"
        const val MODE = "mode"
    }
}