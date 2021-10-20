package com.surelabsid.lti.dacofa.ui.isidata.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.lti.dacofa.database.Fish
import com.surelabsid.lti.dacofa.databinding.ItemAdapterListBinding
import com.surelabsid.lti.dacofa.response.DataIkanItem

class AdapterListIkan(
    private val onIkanClick: (Fish?) -> Unit
) : RecyclerView.Adapter<AdapterListIkan.ViewHolder>() {

    private var mListIkan: MutableList<Fish?> = mutableListOf()

    inner class ViewHolder(private val itemAdapterListBinding: ItemAdapterListBinding) :
        RecyclerView.ViewHolder(itemAdapterListBinding.root) {

        fun onBindItem(dataIkanItem: Fish?) {
            itemAdapterListBinding.nama.text = dataIkanItem?.nama_ikan
            itemAdapterListBinding.root.setOnClickListener {
                onIkanClick(dataIkanItem)
            }
        }
    }

    fun addItem(newListKab: List<Fish?>, clearIt: Boolean = false) {
        if (clearIt)
            mListIkan.removeAll(mListIkan)
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
        holder.onBindItem(mListIkan.get(position))
    }

    override fun getItemCount(): Int {
        return mListIkan.size
    }
}