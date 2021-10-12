package com.surelabsid.lti.dacofa.ui.lihatdata

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.databinding.ActivityLihatDataBinding
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.ui.isidata.IsiDataActivity
import com.surelabsid.lti.dacofa.ui.lihatdata.adapter.AdapterData
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync

class LihatDataActivity : Baseapp() {

    private lateinit var binding: ActivityLihatDataBinding
    private lateinit var adapterNeedValidate: AdapterData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLihatDataBinding.inflate(layoutInflater)
        setContentView(binding.root)


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

        binding.back.setOnClickListener {
            finish()
        }

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
                adapterNeedValidate.addData(data, true)
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