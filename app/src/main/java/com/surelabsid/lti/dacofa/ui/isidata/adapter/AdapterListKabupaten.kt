package com.surelabsid.lti.dacofa.ui.isidata.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.lti.dacofa.database.WilayahKabupaten
import com.surelabsid.lti.dacofa.databinding.ItemAdapterListBinding
import com.surelabsid.lti.dacofa.response.DataKabItem

class AdapterListKabupaten(
    private val onKabClick: (WilayahKabupaten?) -> Unit
) : RecyclerView.Adapter<AdapterListKabupaten.ViewHolder>() {

    private var mListKab: MutableList<WilayahKabupaten?> = mutableListOf()

    inner class ViewHolder(private val itemAdapterListBinding: ItemAdapterListBinding) :
        RecyclerView.ViewHolder(itemAdapterListBinding.root) {

        fun onBindItem(dataKabItem: WilayahKabupaten?) {
            itemAdapterListBinding.nama.text = dataKabItem?.nama
            itemAdapterListBinding.root.setOnClickListener {
                onKabClick(dataKabItem)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(newListKab: List<WilayahKabupaten?>, clearIt: Boolean = false) {
        if (clearIt)
            mListKab.removeAll(mListKab.toSet())
        this.mListKab = newListKab.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAdapterListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(mListKab[position])
    }

    override fun getItemCount(): Int {
        return mListKab.size
    }
}