package com.surelabsid.lti.dacofa.ui.isidata.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.lti.dacofa.R
import com.surelabsid.lti.dacofa.response.DataProvItem
import kotlinx.android.synthetic.main.item_adapter_list.view.*

class AdapterListProvinsi(
    private val onProvClick: (DataProvItem?) -> Unit
) : RecyclerView.Adapter<AdapterListProvinsi.ViewHolder>() {
    private var mListProv: MutableList<DataProvItem?> = mutableListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nama = itemView.nama

        fun onBindItem(dataProvItem: DataProvItem?) {
            nama.text = dataProvItem?.nama
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
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_adapter_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(mListProv[position])
    }

    override fun getItemCount(): Int {
        return mListProv.size
    }
}