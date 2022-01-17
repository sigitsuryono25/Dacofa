package com.surelabsid.lti.dacofa.ui.isidata.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.lti.dacofa.database.WilayahProvinsi
import com.surelabsid.lti.dacofa.databinding.ItemAdapterListBinding
import com.surelabsid.lti.dacofa.response.DataProvItem

class AdapterListProvinsi(
    private val onProvClick: (WilayahProvinsi?) -> Unit
) : RecyclerView.Adapter<AdapterListProvinsi.ViewHolder>() {
    private var mListProv: MutableList<WilayahProvinsi?> = mutableListOf()

    inner class ViewHolder(private val itemAdapterListBinding: ItemAdapterListBinding) :
        RecyclerView.ViewHolder(itemAdapterListBinding.root) {

        fun onBindItem(dataProvItem: WilayahProvinsi?) {
            itemAdapterListBinding.nama.text = dataProvItem?.nama
            itemView.setOnClickListener {
                onProvClick(dataProvItem)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(listProv: List<WilayahProvinsi?>, clearit: Boolean = false) {
//        if (clearit)
//            mListProv.removeAll(mListProv)

        mListProv = listProv.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAdapterListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(mListProv[position])
    }

    override fun getItemCount(): Int {
        return mListProv.size
    }
}