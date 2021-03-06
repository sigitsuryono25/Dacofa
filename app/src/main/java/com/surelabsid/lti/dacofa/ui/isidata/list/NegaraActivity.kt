package com.surelabsid.lti.dacofa.ui.isidata.list

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.database.Countries
import com.surelabsid.lti.dacofa.database.WilayahKabupaten
import com.surelabsid.lti.dacofa.databinding.ActivityNegaraBinding
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.ui.isidata.adapter.AdapterListNegara
import org.jetbrains.anko.doAsync

class NegaraActivity : Baseapp() {
    private lateinit var binding: ActivityNegaraBinding
    private var mListCountries: List<Countries?>? = null
    private lateinit var adapterListNegara : AdapterListNegara
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNegaraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.choose_countries)
            setDisplayHomeAsUpEnabled(true)
        }

        adapterListNegara = AdapterListNegara {
            val i = Intent()
            i.putExtra(COUNTRY_CODE, it?.alpha_2)
            i.putExtra(COUNTRY_NAME, it?.name)
            setResult(Activity.RESULT_OK, i)
            finish()
        }

        this.getListNegara()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.app_bar_search)
        var searchView: SearchView? = null
        searchItem?.let {
            searchView = MenuItemCompat.getActionView(it) as SearchView
        }


        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.length > 3) {
                        filterNegara(it)
                    } else {
                        adapterListNegara.addItem(mListCountries!!, true)
                    }
                }
                return false
            }
        })
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterNegara(query: String?) {
        val filteredData = mListCountries?.filter {
            it?.name?.contains(query.toString(), true) == true
        }
        adapterListNegara.addItem(filteredData!!)
        adapterListNegara.notifyDataSetChanged()
    }


    private fun getListNegara() {
        doAsync {
            mListCountries = db.daftarNegaraDao().getAllCountries()
            mListCountries?.let { adapterListNegara.addItem(it) }
            runOnUiThread {
                binding.rvNegara.apply {
                    adapter = adapterListNegara
                    layoutManager = LinearLayoutManager(this@NegaraActivity)
                }
            }
        }
    }

    companion object {
        const val COUNTRY_CODE = "countryCode"
        const val COUNTRY_NAME = "countryName"
    }
}