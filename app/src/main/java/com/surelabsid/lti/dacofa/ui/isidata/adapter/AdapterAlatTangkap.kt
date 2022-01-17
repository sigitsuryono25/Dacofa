package com.surelabsid.lti.dacofa.ui.isidata.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.lti.dacofa.database.Fish
import com.surelabsid.lti.dacofa.database.Fishinggear
import com.surelabsid.lti.dacofa.databinding.ItemAdapterListBinding
import com.surelabsid.lti.dacofa.response.DataIkanItem

class AdapterAlatTangkap(
    private val onIkanClick: (Fishinggear?) -> Unit
) : RecyclerView.Adapter<AdapterAlatTangkap.ViewHolder>() {

    private var mListIkan: MutableList<Fishinggear?> = mutableListOf()

    inner class ViewHolder(private val itemAdapterListBinding: ItemAdapterListBinding) :
        RecyclerView.ViewHolder(itemAdapterListBinding.root) {

        fun onBindItem(dataIkanItem: Fishinggear?) {
            itemAdapterListBinding.nama.text = dataIkanItem?.nama_fishing_gear
            itemAdapterListBinding.root.setOnClickListener {
                onIkanClick(dataIkanItem)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(newListKab: List<Fishinggear?>, clearIt: Boolean = false) {
        if (clearIt)
            mListIkan.removeAll(mListIkan.toSet())
        this.mListIkan = newListKab.toMutableList()
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
        holder.onBindItem(mListIkan[position])
    }

    override fun getItemCount(): Int {
        return mListIkan.size
    }
}