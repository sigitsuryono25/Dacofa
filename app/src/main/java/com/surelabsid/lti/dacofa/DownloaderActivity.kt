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
import retrofit2.HttpException
import java.io.IOException

class DownloaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDownloaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloaderBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.redownload.setOnClickListener {
            binding.redownload.visibility = View.GONE
            binding.bar.visibility = View.VISIBLE
            binding.status.text = "Please wait..\nWhile we downloading some data"
            getData()
        }



        checkData()

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
        Log.d("checkData", "checkData: $days")
        if (days > 5) {
            runOnUiThread {
                binding.status.text = "Your data is too old. Please re-download the data"
                binding.redownload.visibility = View.VISIBLE
                binding.bar.visibility = View.GONE
            }
        } else if (provinsi.isNotEmpty() && kab.isNotEmpty() && countries.isNotEmpty() && fishingGear.isNotEmpty() && fish.isNotEmpty()) {
            Intent(this@DownloaderActivity, LandingActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        } else {
            getData()
        }
    }
}