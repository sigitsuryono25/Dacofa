package com.surelabsid.lti.dacofa.ui.isidata.list

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.database.WilayahProvinsi
import com.surelabsid.lti.dacofa.databinding.ActivityProvinsiBinding
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.ui.isidata.IsiDataActivity
import com.surelabsid.lti.dacofa.ui.isidata.adapter.AdapterListProvinsi
import org.jetbrains.anko.doAsync

class ProvinsiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProvinsiBinding
    private var listProv: List<WilayahProvinsi?>? = null
    private lateinit var adapter: AdapterListProvinsi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProvinsiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.choose_pro)
            setDisplayHomeAsUpEnabled(true)
        }

        adapter = AdapterListProvinsi {
            val i = Intent()
            i.putExtra(PROVINCE_ID, it?.id)
            i.putExtra(PROVINCE_NAME, it?.nama)
            setResult(Activity.RESULT_OK, i)
            finish()
        }
        listProv?.let { adapter.addItem(it) }
        binding.rvProvinsi.adapter = this.adapter
        binding.rvProvinsi.layoutManager = LinearLayoutManager(
            this
        )

        val countryCode = intent.getStringExtra(IsiDataActivity.NEGARA)
        this.getProvinsi(countryCode)
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
                        filterProvinsi(it)
                    } else {
                        adapter.addItem(listProv!!, true)
                    }
                }
                return false
            }
        })
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterProvinsi(query: String?) {
        val filteredData = listProv?.filter {
            it?.nama?.contains(query.toString(), true) == true
        }
        adapter.addItem(filteredData!!)
        adapter.notifyDataSetChanged()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.app_bar_search -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getProvinsi(countryCode: String?) {
        doAsync {
            listProv = db.daftarProvinsiDao().getProvinsiByProvinsi(countryCode)
            adapter.addItem(listProv!!)
        }
//        NetworkModule.getService().getListProvinsi(countryCode)
//            .enqueue(object : retrofit2.Callback<ResponseProvinsi> {
//                override fun onResponse(
//                    call: Call<ResponseProvinsi>,
//                    response: Response<ResponseProvinsi>
//                ) {
//                    listProv = response.body()?.dataProv
//                    adapter.addItem(listProv!!)
//                    adapter.notifyDataSetChanged()
//                }
//
//                override fun onFailure(call: Call<ResponseProvinsi>, t: Throwable) {
//                    Toast.makeText(this@ProvinsiActivity, t.message, Toast.LENGTH_SHORT).show()
//                }
//            })
    }

    companion object {
        const val PROVINCE_ID = "provinceId"
        const val PROVINCE_NAME = "provinceName"
    }
}