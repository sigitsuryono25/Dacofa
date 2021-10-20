package com.surelabsid.lti.dacofa.ui.lihatdata

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.pixplicity.easyprefs.library.Prefs
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.database.HeaderLokasi
import com.surelabsid.lti.dacofa.databinding.ActivityLihatDataBinding
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.model.SyncModel
import com.surelabsid.lti.dacofa.network.NetworkModule
import com.surelabsid.lti.dacofa.response.GeneralResponse
import com.surelabsid.lti.dacofa.response.ResponseFishery
import com.surelabsid.lti.dacofa.ui.isidata.IsiDataActivity
import com.surelabsid.lti.dacofa.ui.lihatdata.adapter.AdapterData
import com.surelabsid.lti.dacofa.utils.Constant
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.okButton
import retrofit2.Call
import retrofit2.Response

class LihatDataActivity : Baseapp() {

    private lateinit var binding: ActivityLihatDataBinding
    private lateinit var adapterNeedValidate: AdapterData
    private lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLihatDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bg.ok.visibility = View.GONE

        adapterNeedValidate = AdapterData({
            //on edit
            startActivity(
                Intent(this@LihatDataActivity, LihatDetailActivity::class.java)
                    .putExtra("idLokasi", it)
            )
        }, { deleteData ->
            //on hapus
            alert {
                message = getString(R.string.delete_data)
                title = getString(R.string.confirm)
                positiveButton("Ok") {
                    deleteData(deleteData.id)
                }
                negativeButton("Cancel") {
                    it.dismiss()
                }
                isCancelable = false
            }.show()
        }, {
            //on validate
            pd = ProgressDialog.show(this, "", "please wait...", false, false)
            doAsync {
                val detail = db.detailTangkapanDao().getAllTangakapanByIdHeader(it.id)
                val listHeader = mutableListOf<HeaderLokasi>()
                listHeader.add(it)

                val sync = SyncModel()
                sync.detail = detail
                sync.lokasi = listHeader
                sync.userid = Prefs.getString(Constant.USERID)

                syncData(sync, it.id)
            }
        })
        binding.rvNeedValidate.apply {
            adapter = adapterNeedValidate
            layoutManager = LinearLayoutManager(this@LihatDataActivity)
        }

        binding.add.setOnClickListener {
            Intent(this, IsiDataActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.bg.back.setOnClickListener {
            finish()
        }

        binding.bg.logout.setOnClickListener {
            logout()
        }

        this.getData()
    }

    private fun getData() {
        NetworkModule.getService().getDataFishery(Prefs.getString(Constant.USERID))
            .enqueue(object : retrofit2.Callback<ResponseFishery> {
                override fun onResponse(
                    call: Call<ResponseFishery>,
                    response: Response<ResponseFishery>
                ) {

                    val res = response.body()
                    val d = mutableListOf<HeaderLokasi>()
                    if (res?.dataFishery?.isEmpty() == true) {
                        binding.noDataValid.visibility = View.VISIBLE
                    } else {
                        binding.noDataValid.visibility = View.GONE
                        res?.dataFishery?.map {
                            val h = HeaderLokasi(
                                id = it?.id.toString(),
                                tanggal = it?.tanggal.toString(),
                                alat_tangkap = it?.alatTangkap.toString(),
                                lainnya = it?.lainnya.toString(),
                                ukuran_jaring = it?.ukuranJaring.toString(),
                                jumla_alat = it?.jumlaAlat.toString(),
                                id_negara = it?.idNegara.toString(),
                                id_provinsi = it?.idProvinsi.toString(),
                                id_kabupaten = it?.idKabupaten.toString(),
                                area = it?.area.toString(),
                                lokasi = it?.lokasi.toString(),
                                lama_operasi = it?.lamaOperasi.toString(),
                                userid = it?.userid.toString()
                            )
                            d.add(h)
                        }

                        val adp = AdapterData({}, {}, {}, true)
                        adp.addData(d)
                        binding.rvValidData.apply {
                            adapter = adp
                            layoutManager = LinearLayoutManager(this@LihatDataActivity)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseFishery>, t: Throwable) {

                }

            })
    }

    private fun syncData(syncModel: SyncModel, id: String) {
        NetworkModule.getService().syncData(syncModel)
            .enqueue(object : retrofit2.Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    pd.dismiss()
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
                                this@LihatDataActivity.getHeaderData()
                            }
                            isCancelable = false
                        }.show()
                    } else {
                        showMessage(data?.message.toString())
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    pd.dismiss()
                    showMessage(t.message.toString())
                }

            })
    }

    override fun onResume() {
        super.onResume()
        this.getHeaderData()
    }

    private fun getHeaderData() {
        doAsync {
            val data = db.headerLokasiDao().getAllHeader()
            Log.d("getLokasiData", "getLokasiData: $data")
            runOnUiThread {
                if (data.isEmpty()) {
                    binding.noDataNeedValidate.visibility = View.VISIBLE
                } else {
                    binding.noDataNeedValidate.visibility = View.GONE
                    adapterNeedValidate.addData(data, true)
                }
            }
        }

    }

    private fun deleteData(idHeader: String) {
        doAsync {
            val del = db.headerLokasiDao().deleteHeader(idHeader)
            val delData = db.detailTangkapanDao().deleteByByIdHeader(idHeader)
            if (del > 0 && delData > 0) {
                runOnUiThread {
                    showMessage("data berhasil dihapus")
                }
            } else {
                runOnUiThread {
                    showMessage("data gagal dihapus")
                }
            }

            runOnUiThread {
                getHeaderData()
            }
        }
    }


}