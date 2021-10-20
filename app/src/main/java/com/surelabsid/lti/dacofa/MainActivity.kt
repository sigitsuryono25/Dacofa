package com.surelabsid.lti.dacofa

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pixplicity.easyprefs.library.Prefs
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.databinding.ActivityMainBinding
import com.surelabsid.lti.dacofa.model.User
import com.surelabsid.lti.dacofa.network.NetworkModule
import com.surelabsid.lti.dacofa.response.ResponseUser
import com.surelabsid.lti.dacofa.utils.Constant
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Response


class MainActivity : Baseapp() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.bg.actionContainer.visibility = View.GONE

        binding.indonesia.setOnClickListener {
            reloadActivity("id")
        }

        binding.english.setOnClickListener {
            reloadActivity("en")
        }

        binding.masuk.setOnClickListener {
            if (binding.idPengguna.text.toString().isEmpty() || binding.kataSandi.text.toString()
                    .isEmpty()
            ) {
                Toasty.warning(this, "Silahkan isi semua Kolom").show()
            } else {
                pd = ProgressDialog.show(this, "", "Please Wait...", false, false)
                getCrendential(
                    binding.idPengguna.text.toString(),
                    binding.kataSandi.text.toString()
                )
            }

        }
    }

    private fun getCrendential(userid: String, passwd: String) {
        val user = User()
        user.passwd = passwd
        user.userid = userid

        NetworkModule.getService().getCredential(user)
            .enqueue(object : retrofit2.Callback<ResponseUser> {
                override fun onResponse(
                    call: Call<ResponseUser>,
                    response: Response<ResponseUser>
                ) {
                    pd.dismiss()
                    val res = response.body()
                    if (res?.code == 200) {
                        Prefs.putString(Constant.USERID, res.dataUser?.userid)
                        Prefs.putString(Constant.NAMA, res.dataUser?.nama)
                        Prefs.putString(Constant.TELP, res.dataUser?.phone)
                        with(Intent(this@MainActivity, DownloaderActivity::class.java)) {
                            finish()
                            startActivity(this)
                        }
                    } else {
                        Toasty.error(this@MainActivity, res?.message.toString()).show()
                    }
                }

                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    pd.dismiss()
                    showMessage(t.message.toString())
                }

            })
    }


}