package com.surelabsid.lti.dacofa.ui.isidata.list

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.base.Baseapp
import com.surelabsid.lti.dacofa.database.Fish
import com.surelabsid.lti.dacofa.databinding.ActivityIkanBinding
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.ui.isidata.adapter.AdapterListIkan
import org.jetbrains.anko.doAsync

class IkanActivity : Baseapp() {
    private lateinit var binding: ActivityIkanBinding
    private lateinit var adapterListIkan: AdapterListIkan
    private var mListFish: List<Fish?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIkanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.choose_fish_name)
            setDisplayHomeAsUpEnabled(true)
        }

        adapterListIkan = AdapterListIkan {
            val i = Intent()
            i.putExtra(ID_IKAN, it?.id)
            i.putExtra(NAMA_IKAN, it?.nama_ikan)
            setResult(Activity.RESULT_OK, i)
            finish()
        }

        this.getListIkan()
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
                        filterIkan(it)
                    } else {
                        adapterListIkan.addItem(mListFish!!, true)
                    }
                }
                return false
            }
        })
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterIkan(query: String?) {
        val filteredData = mListFish?.filter {
            it?.nama_ikan?.contains(query.toString(), true) == true
        }
        adapterListIkan.addItem(filteredData!!)
        adapterListIkan.notifyDataSetChanged()
    }


    private fun getListIkan() {

        binding.rvIkan.apply {
            adapter = adapterListIkan
            layoutManager = LinearLayoutManager(this@IkanActivity)
        }
        doAsync {
            mListFish = db.daftarIkanDao().getAllFish()
            mListFish?.let {
                adapterListIkan.addItem(it)
            }
        }
    }

    companion object {
        const val ID_IKAN = "idIkan"
        const val NAMA_IKAN = "namaIkan"
    }
}