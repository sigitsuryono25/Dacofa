package com.surelabsid.lti.dacofa.ui.isidata.list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.database.Fishinggear
import com.surelabsid.lti.dacofa.databinding.ActivityNegaraBinding
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.ui.isidata.adapter.AdapterAlatTangkap
import org.jetbrains.anko.doAsync

class AlatTangkapActivity : Baseapp() {
    private lateinit var binding: ActivityNegaraBinding
    private var mListFishinggear: List<Fishinggear?>? = null
    private lateinit var adapterListAlatTangkap: AdapterAlatTangkap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNegaraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.choose_countries)
            setDisplayHomeAsUpEnabled(true)
        }

        adapterListAlatTangkap = AdapterAlatTangkap {
            val i = Intent()
            i.putExtra(FISHINGGEAR_NAME, it?.nama_fishing_gear)
            i.putExtra(FISHINGGEAR_ID, it?.id)
            setResult(RESULT_OK, i)
            finish()
        }

        this.getListAlatTangkap()
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
                        filterAlatTangkap(it)
                    } else {
                        adapterListAlatTangkap.addItem(mListFishinggear!!, true)
                    }
                }
                return false
            }
        })
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterAlatTangkap(query: String?) {
        val filteredData = mListFishinggear?.filter {
            it?.nama_fishing_gear?.contains(query.toString(), true) == true
        }
        adapterListAlatTangkap.addItem(filteredData!!)
        adapterListAlatTangkap.notifyDataSetChanged()
    }


    private fun getListAlatTangkap() {
        doAsync {
            mListFishinggear = db.daftarFishingGearDao().getAllFishingGear()
            mListFishinggear?.let { adapterListAlatTangkap.addItem(it) }
            runOnUiThread {
                binding.rvNegara.apply {
                    adapter = adapterListAlatTangkap
                    layoutManager = LinearLayoutManager(this@AlatTangkapActivity)
                }
            }
        }
    }

    companion object {
        const val FISHINGGEAR_NAME = "fishinggearName"
        const val FISHINGGEAR_ID = "fishingGearId"
    }
}