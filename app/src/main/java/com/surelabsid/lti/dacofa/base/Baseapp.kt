package com.surelabsid.lti.dacofa.base

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.lti.dacofa.App
import com.surelabsid.lti.dacofa.database.DetailTangkapan
import com.surelabsid.lti.dacofa.database.HeaderLokasi
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.utils.LocalizationUtil
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import java.util.*

open class Baseapp : AppCompatActivity() {

    fun reloadActivity(lang: String) {
        App.LANGUAGE = lang
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }
        startActivity(intent)
    }

    fun insertHeaderLokasi(headerLokasi: HeaderLokasi): Boolean {
        var status: Boolean
        doAsyncResult({
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            status = false
        }, { db.headerLokasiDao().insertAllData(headerLokasi) })
        status = true
        return status
    }

    fun insertDetailTangkapan(detailTangkapan: DetailTangkapan): Boolean {
        var status: Boolean
        doAsyncResult({
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            status = false
        }, { db.detailTangkapanDao().insertAllData(detailTangkapan) })

        status = true
        return status
    }

    fun deleteAllData() : Boolean{
        var status: Boolean
        doAsync({
            status = false
        }, { db.detailTangkapanDao().clearData() })

        status = true

        return status

    }

    fun showMessage(
        message: String,
        destination: Class<*>? = null,
        actionText: String? = "",
        bundle: Bundle? = null,
        duration: Int = Snackbar.LENGTH_SHORT
    ) {
        val snackbar = Snackbar.make(findViewById(R.id.content), message, duration)
        if (destination != null) {
            snackbar.setAction(actionText) {
                Intent(this, destination).apply {
                    putExtra("data", bundle)
                    startActivity(this)
                    finish()
                }
            }
        }
        snackbar.show()
    }

    override fun getBaseContext(): Context {
        return LocalizationUtil.applyLang(super.getBaseContext(), Locale(App.LANGUAGE))
    }

    override fun getApplicationContext(): Context {
        val context = super.getApplicationContext()
        return LocalizationUtil.applyLang(context, Locale(App.LANGUAGE))
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocalizationUtil.applyLang(newBase, Locale(App.LANGUAGE)))
    }
}