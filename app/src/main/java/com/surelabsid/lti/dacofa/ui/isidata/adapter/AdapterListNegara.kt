package com.surelabsid.lti.dacofa.ui.isidata.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.lti.dacofa.database.Countries
import com.surelabsid.lti.dacofa.databinding.ItemAdapterListBinding
import com.surelabsid.lti.dacofa.response.DataNegaraItem

class AdapterListNegara(
    private val onNegaraClick: (Countries?) -> Unit
) : RecyclerView.Adapter<AdapterListNegara.ViewHolder>() {

    private var mListNegara: MutableList<Countries?> = mutableListOf()

    inner class ViewHolder(private val itemAdapterListBinding: ItemAdapterListBinding) :
        RecyclerView.ViewHolder(itemAdapterListBinding.root) {

        fun onBindItem(countries: Countries?) {
            itemAdapterListBinding.nama.text = countries?.name
            itemAdapterListBinding.root.setOnClickListener {
                onNegaraClick(countries)
            }
        }
    }

    fun addItem(newListKab: List<Countries?>, clearIt: Boolean = false) {
        if (clearIt)
            mListNegara.removeAll(mListNegara)
        this.mListNegara = newListKab.toMutableList()
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
        holder.onBindItem(mListNegara.get(position))
    }

    override fun getItemCount(): Int {
        return mListNegara.size
    }
}