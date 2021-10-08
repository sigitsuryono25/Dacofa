package com.surelabsid.lti.dacofa.ui.isidata

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.network.NetworkModule
import com.surelabsid.lti.dacofa.response.DataProvItem
import com.surelabsid.lti.dacofa.response.ResponseProvinsi
import com.surelabsid.lti.dacofa.ui.isidata.adapter.AdapterListProvinsi
import kotlinx.android.synthetic.main.activity_provinsi.*
import retrofit2.Call
import retrofit2.Response

class ProvinsiActivity : AppCompatActivity() {
    private var listProv: List<DataProvItem?>? = null
    private lateinit var adapter: AdapterListProvinsi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provinsi)

        supportActionBar?.apply {
            title = "Pilih Provinsi"
            setDisplayHomeAsUpEnabled(true)
        }

        adapter = AdapterListProvinsi {
            with(Intent(this@ProvinsiActivity, KabupatenActivity::class.java)) {
                putExtra(KabupatenActivity.ID_PROVINSI, it?.id)
                startActivity(this)
            }
        }
        listProv?.let { adapter.addItem(it) }
        rvProvinsi.adapter = this.adapter
        rvProvinsi.layoutManager = LinearLayoutManager(
            this
        )

        this.getProvinsi()
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

    private fun getProvinsi() {
        NetworkModule.getService().getListProvinsi()
            .enqueue(object : retrofit2.Callback<ResponseProvinsi> {
                override fun onResponse(
                    call: Call<ResponseProvinsi>,
                    response: Response<ResponseProvinsi>
                ) {
                    listProv = response.body()?.dataProv
                    adapter.addItem(listProv!!)
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<ResponseProvinsi>, t: Throwable) {
                    Toast.makeText(this@ProvinsiActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }
}