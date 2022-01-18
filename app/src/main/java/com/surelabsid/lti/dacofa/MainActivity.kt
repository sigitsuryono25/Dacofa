package com.surelabsid.lti.dacofa

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.pixplicity.easyprefs.library.Prefs
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.databinding.ActivityMainBinding
import com.surelabsid.lti.dacofa.model.User
import com.surelabsid.lti.dacofa.network.NetworkModule
import com.surelabsid.lti.dacofa.response.ResponseUser
import com.surelabsid.lti.dacofa.utils.Constant
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


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

        binding.lupaPassword.setOnClickListener {
            val v = LayoutInflater.from(this).inflate(R.layout.reset_password, null)
            val sendBtn = v.findViewById<Button>(R.id.resetPassword)
            val email = v.findViewById<TextInputEditText>(R.id.emailAdd)

            sendBtn.setOnClickListener {
                if (email.text.toString()
                        .isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString())
                        .matches()
                ) {
                    sendResetLink(email.text.toString())
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.must_a_valid_email),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            AlertDialog.Builder(this).setView(v)
                .setTitle(getString(R.string.reset_your_pass))
                .create().show()
        }

        binding.masuk.setOnClickListener {
            if (binding.idPengguna.text.toString().isEmpty() || binding.kataSandi.text.toString()
                    .isEmpty()
            ) {
                Toasty.warning(this, getString(R.string.please_fill_all)).show()
            } else {
                pd = ProgressDialog.show(this, "", getString(R.string.please_wait), false, false)
                getCrendential(
                    binding.idPengguna.text.toString(),
                    binding.kataSandi.text.toString()
                )
            }

        }
    }

    private fun sendResetLink(email: String) {
        pd = ProgressDialog.show(
            this@MainActivity,
            "",
            getString(R.string.send_link_reset),
            true,
            false
        )
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val res = NetworkModule.getService().resetPassword(email)
                    MainScope().launch {
                        pd.dismiss()
                        if (res.code == 200) {
                            alert {
                                message = res.message.toString()
                                title = getString(R.string.information)
                                okButton { dialog ->
                                    dialog.dismiss()
                                }
                            }.show()
                        } else {
                            alert {
                                message = res.message.toString()
                                title = getString(R.string.information)
                                okButton { dialog ->
                                    dialog.dismiss()
                                }
                            }.show()
                        }
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    MainScope().launch {
                        Toast.makeText(this@MainActivity, e.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                        pd.dismiss()
                    }
                }
            }
        }
    }

    private fun getCrendential(userid: String, passwd: String) {
        val user = User()
        user.passwd = passwd
        user.userid = userid

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val res = NetworkModule.getService().getCredential(user)
                    if(res.code == 200){
                        pd.dismiss()
                        updateUI(res)
                    }else{
                        pd.dismiss()
                        MainScope().launch {
                            showMessage(res.message.toString())
                        }
                    }
                } catch (t: Throwable) {
                    pd.dismiss()
                    val msg = when (t) {
                        is HttpException -> {
                            if (t.code() == 404) {
                                getString(R.string.not_found)
                            } else if (t.code() == 500) {
                                getString(R.string.server_error)
                            } else {
                                t.message()
                            }
                        }
                        is IOException -> {
                            getString(R.string.io_error)
                        }
                        else -> {
                            getString(R.string.unknown_error)
                        }
                    }
                    MainScope().launch {
                        Toasty.error(this@MainActivity, msg).show()
                    }
                }
            }
        }
    }

    private fun updateUI(res: ResponseUser) {
        Prefs.putString(Constant.USERID, res.dataUser?.userid)
        Prefs.putString(Constant.NAMA, res.dataUser?.nama)
        Prefs.putString(Constant.TELP, res.dataUser?.phone)
        with(Intent(this@MainActivity, DownloaderActivity::class.java)) {
            finish()
            startActivity(this)
        }
    }


}