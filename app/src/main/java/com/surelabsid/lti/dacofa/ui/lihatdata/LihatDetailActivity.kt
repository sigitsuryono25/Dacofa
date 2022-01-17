package com.surelabsid.lti.dacofa.ui.lihatdata

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.pixplicity.easyprefs.library.Prefs
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.database.HeaderLokasi
import com.surelabsid.lti.dacofa.databinding.ActivityLihatDetailBinding
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.model.SyncModel
import com.surelabsid.lti.dacofa.network.NetworkModule
import com.surelabsid.lti.dacofa.response.GeneralResponse
import com.surelabsid.lti.dacofa.ui.isidata.IsiDataActivity
import com.surelabsid.lti.dacofa.ui.isidata.IsiHasilTangkapanActivity
import com.surelabsid.lti.dacofa.utils.Constant
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.okButton
import retrofit2.Call
import retrofit2.Response

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
            startActivity(Intent(this, IsiHasilTangkapanActivity::class.java).apply {
                putExtra(IsiHasilTangkapanActivity.DATA_TANGKAPAN, it)
                putExtra(IsiHasilTangkapanActivity.MODE, "edit")
            })
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
        binding.bg.ok.visibility = View.GONE

        binding.rvCatchResult.apply {
            adapter = adapterCatchResult
            layoutManager = LinearLayoutManager(this@LihatDetailActivity)
        }

        headerLokasi = intent.getParcelableExtra("idLokasi")

        setToView(headerLokasi)


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

        binding.bg.back.setOnClickListener {
            finish()
        }

        binding.edit.setOnClickListener {
            startActivity(Intent(this, IsiDataActivity::class.java).apply {
                putExtra(IsiDataActivity.DATA_EDIT, headerLokasi)
                putExtra(IsiDataActivity.MODE, "edit")
            })
        }

        binding.add.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(IsiDataActivity.HEADER_ID, headerLokasi?.id)
            Intent(this, IsiHasilTangkapanActivity::class.java).apply {
                putExtra("data", bundle)
                startActivity(this)
            }
        }

        binding.validate.setOnClickListener {
            doAsync {
                val detail =
                    db.detailTangkapanDao().getAllTangakapanByIdHeader(headerLokasi?.id.toString())
                val listHeader = mutableListOf<HeaderLokasi>()
                listHeader.add(headerLokasi!!)

                val sync = SyncModel()
                sync.detail = detail
                sync.lokasi = listHeader
                sync.userid = Prefs.getString(Constant.USERID)

                syncData(sync, it.id.toString())
            }
        }
        binding.bg.logout.setOnClickListener {
            logout()
        }
    }

    private fun setToView(h: HeaderLokasi?) {
        doAsync {
            headerLokasi = db.headerLokasiDao().getAllHeaderById(h?.id.toString())[0]
            runOnUiThread {
                binding.country.text = headerLokasi?.id_negara
                binding.tanggal.text = headerLokasi?.tanggal
                binding.provinsi.text = headerLokasi?.id_provinsi
                binding.kabupaten.text = headerLokasi?.id_kabupaten
                binding.lokasi.text = headerLokasi?.lokasi
                binding.area.text = headerLokasi?.area
                binding.alatTangkap.text = headerLokasi?.alat_tangkap
                binding.ukuranJaring.text = headerLokasi?.ukuran_jaring
                binding.jumlahAlatTangkap.text = headerLokasi?.jumla_alat
                binding.lamaOperasi.text = "${headerLokasi?.lama_operasi} ${getString(R.string.jam)}"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getCatchResult(headerLokasi?.id.toString())
        setToView(headerLokasi)
    }

    private fun syncData(syncModel: SyncModel, id: String) {
        NetworkModule.getService().syncData(syncModel)
            .enqueue(object : retrofit2.Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    val data = response.body()
                    if (data?.code == 200) {
                        alert {
                            title = getString(R.string.confirm)
                            message = "data berhasil di sinkronkan"
                            okButton {
                                doAsync {
                                    db.headerLokasiDao().deleteHeader(id)
                                    db.detailTangkapanDao().deleteByByIdHeader(id)
                                }
                                finish()
                            }
                            isCancelable = false
                        }.show()
                    } else {
                        showMessage(data?.message.toString())
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    showMessage(t.message.toString())
                }

            })
    }


    private fun deleteData(idHeader: String) {
        doAsync {
            val del = db.headerLokasiDao().deleteHeader(idHeader)
            val delData = db.detailTangkapanDao().deleteByByIdHeader(idHeader)
            if (del > 0 || delData > 0) {
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