package com.surelabsid.lti.dacofa.ui.isidata.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.lti.dacofa.databinding.ItemAdapterListBinding
import com.surelabsid.lti.dacofa.response.DataProvItem

class AdapterListProvinsi(
    private val onProvClick: (DataProvItem?) -> Unit
) : RecyclerView.Adapter<AdapterListProvinsi.ViewHolder>() {
    private var mListProv: MutableList<DataProvItem?> = mutableListOf()

    inner class ViewHolder(private val itemAdapterListBinding: ItemAdapterListBinding) :
        RecyclerView.ViewHolder(itemAdapterListBinding.root) {

        fun onBindItem(dataProvItem: DataProvItem?) {
            itemAdapterListBinding.nama.text = dataProvItem?.nama
            itemView.setOnClickListener {
                onProvClick(dataProvItem)
            }
        }
    }

    fun addItem(listProv: List<DataProvItem?>, clearit: Boolean = false) {
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