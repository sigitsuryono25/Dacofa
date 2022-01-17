package com.surelabsid.lti.dacofa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pixplicity.easyprefs.library.Prefs
import com.surelabsid.lti.dacofa.database.*
import com.surelabsid.lti.dacofa.databinding.ActivityDownloaderBinding
import com.surelabsid.lti.dacofa.network.NetworkModule
import com.surelabsid.lti.dacofa.ui.landing.LandingActivity
import com.surelabsid.lti.dacofa.utils.Constant
import com.surelabsid.lti.dacofa.utils.HourToMillis
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import retrofit2.HttpException
import java.io.IOException

class DownloaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDownloaderBinding
    private var from: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloaderBinding.inflate(layoutInflater)

        setContentView(binding.root)
        from = intent.getStringExtra("from")

        binding.redownload.setOnClickListener {
            binding.redownload.visibility = View.GONE
            binding.bar.visibility = View.VISIBLE
            binding.status.text = getString(R.string.please_wait_nwhile_we_downloading_some_data_nmake_sure_you_ve_an_active_connection)
            getData()
        }

        binding.root.visibility = View.GONE
        checkData()


//        AlertDialog.Builder(this)
//            .setMessage("This processes will be download data from server. Expired Date of the data is 5 days." +
//                    "After that you have to re-download the data again.")
//            .setPositiveButton("Understand") { d, _ ->
//                d.dismiss()
//            }.create().show()


    }

    private fun getData() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val provinsi = NetworkModule.getService().getAllProvinsi()
            val kab = NetworkModule.getService().getAllKabupaten()
            val countries = NetworkModule.getService().getAllCountries()
            val fishingGear = NetworkModule.getService().getFishingGear()
            val fish = NetworkModule.getService().getListIkan()

            kab.dataKab?.map {
                val wilayahKabupaten = WilayahKabupaten(
                    id = it?.id.toString(),
                    nama = it?.nama,
                    provinsi_id = it?.provinsiId
                )
                db.daftarKabupatenDao().insertKabupaten(wilayahKabupaten)
            }

            provinsi.dataProv?.map {
                val wilayahProvinsi = WilayahProvinsi(
                    id = it?.id.toString(),
                    nama = it?.nama,
                    country_code = it?.countryCode
                )
                db.daftarProvinsiDao().insertProvinsi(wilayahProvinsi)
            }

            countries.dataNegara?.map {
                val countrie = Countries(
                    alpha_2 = it?.alpha2.toString(),
                    alpha_3 = it?.alpha3,
                    name = it?.name,
                    currencies = it?.currencies,
                    calling_code = it?.callingCode
                )
                db.daftarNegaraDao().insertCountries(countrie)
            }
            fishingGear.dataGear?.map {
                val fishingGears = Fishinggear(
                    id = it?.id.toString(),
                    nama_fishing_gear = it?.namaFishingGear.toString(),
                    extras = it?.extras.toString()
                )
                db.daftarFishingGearDao().insertFishingGear(fishingGears)
            }

            fish.dataIkan?.map {
                val fishes = Fish(
                    id = it?.id.toString(),
                    nama_ikan = it?.namaIkan.toString(),
                    state = it?.state.toString(),
                    added_on = it?.addedOn.toString()
                )
                db.daftarIkanDao().insertFish(fishes)
            }

            Prefs.putString(
                Constant.LAST_RUN,
                HourToMillis.millisToCustomFormat(HourToMillis.millis(), "yyyy-MM-dd HH:mm:ss")
            )

            Intent(this@DownloaderActivity, LandingActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        } catch (e: Throwable) {
            runOnUiThread {
                binding.redownload.visibility = View.VISIBLE
                binding.bar.visibility = View.GONE
                var message = ""
                message = when (e) {
                    is HttpException -> {
                        e.localizedMessage.toString()
                    }
                    is IOException -> {
                        e.message.toString()
                    }
                    else -> {
                        "Unknown Error occured"
                    }
                }
                binding.status.text = message
            }
        }
    }

    private fun checkData() = CoroutineScope(Dispatchers.IO).launch {
        val provinsi = db.daftarProvinsiDao().getAllProvinsi()
        val kab = db.daftarKabupatenDao().getAllKabupaten()
        val countries = db.daftarNegaraDao().getAllCountries()
        val fishingGear = db.daftarFishingGearDao().getAllFishingGear()
        val fish = db.daftarIkanDao().getAllFish()

        //get last run
        val lastRun = Prefs.getString(Constant.LAST_RUN)
        val days = HourToMillis.dateDiff(lastRun)
        if (days > 5) {
            binding.root.visibility = View.VISIBLE
            runOnUiThread {
                binding.status.text = getString(R.string.data_too_old)
                binding.redownload.visibility = View.VISIBLE
                binding.bar.visibility = View.GONE
            }
        } else if (provinsi.isNotEmpty() && kab.isNotEmpty() && countries.isNotEmpty() && fishingGear.isNotEmpty() && fish.isNotEmpty()) {
            if (from?.equals("landing") == true) {
                MainScope().launch {
                    alert {
                        message = getString(R.string.data_up_to_date)
                        title = getString(R.string.confirm)
                        okButton {
                            finish()
                        }
                    }.show()
                }
            } else {
                Intent(this@DownloaderActivity, LandingActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
        } else {
            MainScope().launch {
                binding.root.visibility = View.VISIBLE
            }
            getData()
        }
    }
}