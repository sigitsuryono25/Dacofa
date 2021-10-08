package com.surelabsid.lti.dacofa.ui.isidata.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.response.DataKabItem
import kotlinx.android.synthetic.main.item_adapter_list.view.*

class AdapterListKabupaten(
    private val onKabClick: (DataKabItem?) -> Unit
) : RecyclerView.Adapter<AdapterListKabupaten.ViewHolder>() {

    private var mListKab: MutableList<DataKabItem?> = mutableListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nama = itemView.nama

        fun onBindItem(dataKabItem: DataKabItem?) {
            nama.text = dataKabItem?.nama
            itemView.setOnClickListener {
                onKabClick(dataKabItem)
            }
        }
    }

    fun addItem(newListKab: List<DataKabItem?>, clearIt: Boolean = false) {
        if (clearIt)
            mListKab.removeAll(mListKab)
        this.mListKab = newListKab.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_adapter_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(mListKab?.get(position))
    }

    override fun getItemCount(): Int {
        return mListKab?.size ?: 0
    }
}