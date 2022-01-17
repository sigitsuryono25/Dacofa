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
import com.surelabsid.lti.dacofa.database.WilayahKabupaten
import com.surelabsid.lti.dacofa.databinding.ActivityKabupatenBinding
import com.surelabsid.lti.dacofa.db
import com.surelabsid.lti.dacofa.ui.isidata.IsiDataActivity
import com.surelabsid.lti.dacofa.ui.isidata.adapter.AdapterListKabupaten
import org.jetbrains.anko.doAsync

class KabupatenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKabupatenBinding
    private lateinit var adapter: AdapterListKabupaten
    private var mListKabupaten: List<WilayahKabupaten?>? = null
    private var idProvinsi: String? = null
    private var idKabupaten: String? = null
    private var kabupatenName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKabupatenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.choose_district)
            setDisplayHomeAsUpEnabled(true)
        }

        adapter = AdapterListKabupaten {
            idKabupaten = it?.id
            kabupatenName = it?.nama
            getData()
        }
        binding.rvKabupaten.adapter = this.adapter
        binding.rvKabupaten.layoutManager = LinearLayoutManager(this)

        idProvinsi = intent.getStringExtra(IsiDataActivity.PROV)
        idProvinsi?.let {
            getKab(it)
        }
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
                        filterKab(it)
                    } else {
                        adapter.addItem(mListKabupaten!!, true)
                    }
                }
                return false
            }
        })
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterKab(query: String?) {
        val filteredData = mListKabupaten?.filter {
            it?.nama?.contains(query.toString(), true) == true
        }
        adapter.addItem(filteredData!!)
        adapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                getData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData() {
        val intentData = Intent()
        intentData.putExtra(KAB_ID, idKabupaten)
        intentData.putExtra(KAB_NAME, kabupatenName)
        setResult(Activity.RESULT_OK, intentData)
        finish()
    }


    private fun getKab(idProvinsi: String) {
        doAsync {
            mListKabupaten = db.daftarKabupatenDao().getKabupatenByProvinsi(idProvinsi)
            mListKabupaten?.let { adapter.addItem(it) }
        }
//        NetworkModule.getService().getListKabupaten(idProvinsi)
//            .enqueue(object : retrofit2.Callback<ResponseKabupaten> {
//                override fun onResponse(
//                    call: Call<ResponseKabupaten>,
//                    response: Response<ResponseKabupaten>
//                ) {
//                    mListKabupaten = response.body()?.dataKab
//                    mListKabupaten?.let {
//                        adapter.addItem(it)
//                    }
//                }
//
//                override fun onFailure(call: Call<ResponseKabupaten>, t: Throwable) {
//                    Toast.makeText(this@KabupatenActivity, t.message, Toast.LENGTH_SHORT).show()
//                }
//
//            })
    }

    companion object {
        const val KAB_ID = "kabupatenId"
        const val KAB_NAME = "kabupatenName"
    }
}