package com.surelabsid.lti.dacofa.ui.lihatdata.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.lti.dacofa.database.HeaderLokasi
import com.surelabsid.lti.dacofa.databinding.ItemAdapterDataBinding


class AdapterData(
    private val onEditClick: (HeaderLokasi) -> Unit,
    private val onHapusClick: (HeaderLokasi) -> Unit,
    private val onValidateClick: (HeaderLokasi) -> Unit
) :
    RecyclerView.Adapter<AdapterData.ViewHolder>() {

    private val mListData: MutableList<HeaderLokasi> = mutableListOf()

    inner class ViewHolder(private val itemAdapterDataBinding: ItemAdapterDataBinding) :
        RecyclerView.ViewHolder(itemAdapterDataBinding.root) {

        fun onBindItem(headerLokasi: HeaderLokasi) {
            itemAdapterDataBinding.tanggal.text = headerLokasi.tanggal
            itemAdapterDataBinding.alatTangkap.text = headerLokasi.alatTangkap
            itemAdapterDataBinding.country.text = headerLokasi.negara
            itemAdapterDataBinding.provinsi.text = headerLokasi.provinsi
            itemAdapterDataBinding.area.text = headerLokasi.fishingArea
            itemAdapterDataBinding.lamaOperasi.text = headerLokasi.lamaOperasi
            itemAdapterDataBinding.kabupaten.text = headerLokasi.kabupaten
            itemAdapterDataBinding.lokasi.text = headerLokasi.lokasi

            itemAdapterDataBinding.delete.setOnClickListener {
                onHapusClick(headerLokasi)
            }
            itemAdapterDataBinding.edit.setOnClickListener {
                onEditClick(headerLokasi)
            }

            itemAdapterDataBinding.validate.setOnClickListener {
                onValidateClick(headerLokasi)
            }
        }
    }

    fun addData(listHeader: List<HeaderLokasi>, clearIt: Boolean = false) {
        if (clearIt)
            mListData.removeAll(mListData)
        mListData.addAll(listHeader)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAdapterDataBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(mListData[position])
    }

    override fun getItemCount(): Int {
        return mListData.size
    }
}