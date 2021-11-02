package com.surelabsid.lti.dacofa.ui.isidata

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.anggastudio.spinnerpickerdialog.SpinnerPickerDialog
import com.google.android.material.snackbar.Snackbar
import com.pixplicity.easyprefs.library.Prefs
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.database.HeaderLokasi
import com.surelabsid.lti.dacofa.databinding.ActivityIsiDataBinding
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.ui.isidata.list.KabupatenActivity
import com.surelabsid.lti.dacofa.ui.isidata.list.NegaraActivity
import com.surelabsid.lti.dacofa.ui.isidata.list.ProvinsiActivity
import com.surelabsid.lti.dacofa.utils.Constant
import org.jetbrains.anko.doAsync

class IsiDataActivity : Baseapp() {
    private lateinit var binding: ActivityIsiDataBinding
    private var idNegara: String? = null
    private var idKab: String? = null
    private var idProv: String? = null
    private var selectedDate: String? = null
    private var idHeader: String? = null
    private var mode = "add"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIsiDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.getFishingGear()

        binding.tanggalSelected.setOnClickListener {
            val dateDialog = SpinnerPickerDialog()
            dateDialog.context = this
            dateDialog.setOnDialogListener(object : SpinnerPickerDialog.OnDialogListener {
                override fun onSetDate(month: Int, day: Int, year: Int) {
                    binding.tanggalSelected.text = String.format(
                        "%d-%02d-%02d",
                        year,
                        month + 1,
                        day
                    )
                    selectedDate = binding.tanggalSelected.text.toString()
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
            if (idNegara?.isNotEmpty() == true) {
                Intent(this, ProvinsiActivity::class.java).apply {
                    putExtra(NEGARA, idNegara)
                    startActivityForResult(this, REQ_PROV)
                }
            } else {
                showMessage("Pilih Negara terlebih dahulu")
            }
        }

        binding.kabupaten.setOnClickListener {
            if (idProv?.isNotEmpty() == true) {
                Intent(this, KabupatenActivity::class.java).apply {
                    putExtra(PROV, idProv)
                    startActivityForResult(this, REQ_KAB)
                }
            } else {
                showMessage("Pilih Provinsi terlebih dahulu")
            }
        }
        binding.bg.back.setOnClickListener {
            finish()
        }

        binding.bg.ok.setOnClickListener {
            if (mode == "add")
                idHeader = System.currentTimeMillis().toString()

            if (idNegara.toString().isEmpty() || idProv.toString().isEmpty() || idKab.toString()
                    .isEmpty() || binding.tanggalSelected.toString().isEmpty()
                || binding.lamaOperasi.text.toString().isEmpty()
            ) {
                showMessage(getString(R.string.please_fill_all))
                return@setOnClickListener
            }

            val headerLokasi = HeaderLokasi(
                id = idHeader.toString(),
                id_negara = binding.spinnerCountry.text.toString(),
                id_provinsi = binding.provinsi.text.toString(),
                id_kabupaten = binding.kabupaten.text.toString(),
                alat_tangkap = binding.alatTangkap.selectedItem.toString(),
                lama_operasi = binding.lamaOperasi.text.toString(),
                userid = Prefs.getString(Constant.USERID),
                area = binding.spinnerArea.selectedItem.toString(),
                lokasi = binding.lokasi.text.toString(),
                tanggal = selectedDate.toString(),
                jumla_alat = binding.jumlahAlatTangkap.text.toString(),
                lainnya = binding.lainnya.text.toString(),
                ukuran_jaring = binding.ukuranJaring.text.toString()
            )

            val ins = insertHeaderLokasi(headerLokasi = headerLokasi)
            if (ins) {
                val bundle = Bundle()
                bundle.putString(HEADER_ID, idHeader)
                showMessage(
                    "Data berhasil disimpan",
                    IsiHasilTangkapanActivity::class.java,
                    "Detail Tangkapan",
                    bundle,
                    Snackbar.LENGTH_INDEFINITE
                )
            }

        }

        binding.bg.logout.setOnClickListener {
            logout()
        }

        val modeData = intent.getStringExtra(MODE)
        val dataEdit = intent.getParcelableExtra<HeaderLokasi>(DATA_EDIT)
        modeData.let {
            if (it == "edit") {
                mode = "edit"
                setToView(dataEdit)
            }
        }

    }

    private fun setToView(dataEdit: HeaderLokasi?) {
        binding.tanggalSelected.text = dataEdit?.tanggal
//        val adapterAlatTangkap = binding.alatTangkap.adapter as ArrayAdapter<String>
//        val selectionAlatTangkap = adapterAlatTangkap.getPosition(dataEdit?.alatTangkap)
//        binding.alatTangkap.setSelection(selectionAlatTangkap)

        binding.lainnya.setText(dataEdit?.lainnya)
        binding.ukuranJaring.setText(dataEdit?.ukuran_jaring)
        binding.jumlahAlatTangkap.setText(dataEdit?.jumla_alat)
        binding.spinnerCountry.text = dataEdit?.id_negara
        binding.provinsi.text = dataEdit?.id_provinsi
        binding.kabupaten.text = dataEdit?.id_kabupaten

        binding.lokasi.setText(dataEdit?.lokasi)
        binding.lamaOperasi.setText(dataEdit?.lama_operasi)

        val adapterArea = binding.spinnerArea.adapter as ArrayAdapter<String>
        val selectionArea = adapterArea.getPosition(dataEdit?.area)
        binding.spinnerArea.setSelection(selectionArea)


        idHeader = dataEdit?.id

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

    fun getFishingGear() {
        doAsync {
            val gear = db.daftarFishingGearDao().getAllFishingGear()
            val gearString = mutableListOf<String>()
            gear.map {
                gearString.add(it.nama_fishing_gear)
            }
            runOnUiThread {
                val adap = ArrayAdapter(
                    this@IsiDataActivity,
                    android.R.layout.simple_list_item_1,
                    gearString
                )
                binding.alatTangkap.adapter = adap
            }
        }
    }

    companion object {
        const val REQ_NEGARA = 1020
        const val REQ_PROV = 1021
        const val REQ_KAB = 1022

        const val NEGARA = "negara"
        const val HEADER_ID = "headerId"
        const val PROV = "prov"
        const val MODE = "mode"
        const val DATA_EDIT = "dataEdit"
    }

}