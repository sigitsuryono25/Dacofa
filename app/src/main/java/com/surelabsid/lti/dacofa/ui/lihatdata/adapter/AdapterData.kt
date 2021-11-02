package com.surelabsid.lti.dacofa.ui.lihatdata.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.lti.dacofa.database.HeaderLokasi
import com.surelabsid.lti.dacofa.databinding.ItemAdapterDataBinding


class AdapterData(
    private val onEditClick: (HeaderLokasi) -> Unit,
    private val onHapusClick: (HeaderLokasi) -> Unit,
    private val onValidateClick: (HeaderLokasi) -> Unit,
    private val isValidate : Boolean = false
) :
    RecyclerView.Adapter<AdapterData.ViewHolder>() {

    private val mListData: MutableList<HeaderLokasi> = mutableListOf()

    inner class ViewHolder(private val itemAdapterDataBinding: ItemAdapterDataBinding) :
        RecyclerView.ViewHolder(itemAdapterDataBinding.root) {

        init {
            if(isValidate){
                itemAdapterDataBinding.edit.visibility = View.GONE
                itemAdapterDataBinding.delete.visibility = View.GONE
                itemAdapterDataBinding.validate.visibility = View.GONE
            }
        }

        fun onBindItem(headerLokasi: HeaderLokasi) {
            itemAdapterDataBinding.tanggal.text = headerLokasi.tanggal + "(${headerLokasi.id})"
            itemAdapterDataBinding.alatTangkap.text = headerLokasi.alat_tangkap
            itemAdapterDataBinding.country.text = headerLokasi.id_negara
            itemAdapterDataBinding.provinsi.text = headerLokasi.id_provinsi
            itemAdapterDataBinding.area.text = headerLokasi.area
            itemAdapterDataBinding.lamaOperasi.text = headerLokasi.lama_operasi
            itemAdapterDataBinding.kabupaten.text = headerLokasi.id_kabupaten
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