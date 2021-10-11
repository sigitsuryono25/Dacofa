package com.surelabsid.lti.dacofa.ui.isidata

import android.content.Intent
import android.os.Bundle
import com.anggastudio.spinnerpickerdialog.SpinnerPickerDialog
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.database.HeaderLokasi
import com.surelabsid.lti.dacofa.databinding.ActivityIsiDataBinding
import com.surelabsid.lti.dacofa.ui.isidata.list.KabupatenActivity
import com.surelabsid.lti.dacofa.ui.isidata.list.NegaraActivity
import com.surelabsid.lti.dacofa.ui.isidata.list.ProvinsiActivity

class IsiDataActivity : Baseapp() {
    private lateinit var binding: ActivityIsiDataBinding
    private var idNegara: String? = null
    private var idKab: String? = null
    private var idProv: String? = null
    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIsiDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tanggalSelected.setOnClickListener {
            val dateDialog = SpinnerPickerDialog()
            dateDialog.context = this
            dateDialog.setOnDialogListener(object : SpinnerPickerDialog.OnDialogListener {
                override fun onSetDate(month: Int, day: Int, year: Int) {
                    binding.tanggalSelected.setText(
                        String.format(
                            "%02d/%02d/%d",
                            day,
                            month + 1,
                            year
                        )
                    )
                    selectedDate = "$year-${month.plus(1)}-$day"
                }

                override fun onCancel() {

                }

                override fun onDismiss() {

                }

            })
            dateDialog.show(supportFragmentManager, "")
        }


        binding.spinnerCountry.setOnClickListener {
            Intent(this, NegaraActivity::class.java).apply {
                startActivityForResult(this, REQ_NEGARA)
            }
        }
        binding.provinsi.setOnClickListener {
            Intent(this, ProvinsiActivity::class.java).apply {
                putExtra(NEGARA, idNegara)
                startActivityForResult(this, REQ_PROV)
            }
        }

        binding.kabupaten.setOnClickListener {
            Intent(this, KabupatenActivity::class.java).apply {
                putExtra(PROV, idProv)
                startActivityForResult(this, REQ_KAB)
            }
        }

        binding.ok.setOnClickListener {
            val idHeader = System.currentTimeMillis().toString()

            if (idNegara.toString().isEmpty() || idProv.toString().isEmpty() || idKab.toString()
                    .isEmpty()
                || binding.alatTangkap.text.toString()
                    .isEmpty() || binding.tanggalSelected.toString().isEmpty()
                || binding.lamaOperasi.text.toString().isEmpty()
            ) {
                showMessage(getString(R.string.please_fill_all))
                return@setOnClickListener
            }

            val headerLokasi = HeaderLokasi(
                id = idHeader,
                negara = idNegara.toString(),
                provinsi = idProv.toString(),
                kabupaten = idKab.toString(),
                alatTangkap = binding.alatTangkap.text.toString(),
                lamaOperasi = binding.lamaOperasi.text.toString(),
                userid = System.currentTimeMillis().toString(),
                fishingArea = binding.spinnerArea.selectedItem.toString()
            )

            val ins = insertHeaderLokasi(headerLokasi = headerLokasi)
            if (ins) {
                val bundle = Bundle()
                bundle.putString(HEADER_ID, idHeader)
                showMessage(
                    "Data berhasil disimpan",
                    DetailHasilTangkapanActivity::class.java,
                    "Detail Tangkapan",
                    bundle,
                    Snackbar.LENGTH_INDEFINITE
                )
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_NEGARA) {
            idNegara = data?.getStringExtra(NegaraActivity.COUNTRY_CODE)
            binding.spinnerCountry.text = data?.getStringExtra(NegaraActivity.COUNTRY_NAME)
        } else if (requestCode == REQ_PROV) {
            idProv = data?.getStringExtra(ProvinsiActivity.PROVINCE_ID)
            binding.provinsi.text = data?.getStringExtra(ProvinsiActivity.PROVINCE_NAME)
        } else if (requestCode == REQ_KAB) {
            idKab = data?.getStringExtra(KabupatenActivity.KAB_ID)
            binding.kabupaten.text = data?.getStringExtra(KabupatenActivity.KAB_NAME)
        }
    }

    companion object {
        const val REQ_NEGARA = 1020
        const val REQ_PROV = 1021
        const val REQ_KAB = 1022

        const val NEGARA = "negara"
        const val HEADER_ID = "headerId"
        const val PROV = "prov"
    }

}