package com.surelabsid.lti.dacofa.ui.lihatdata

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.lti.dacofa.database.DetailTangkapan
import com.surelabsid.lti.dacofa.databinding.ItemAdapterCatchResultBinding
import me.abhinay.input.CurrencySymbols


class AdapterCatchResult(
    private val onEditClick: (DetailTangkapan) -> Unit,
    private val onDeleteClick: (DetailTangkapan) -> Unit
) : RecyclerView.Adapter<AdapterCatchResult.ViewHolder>() {

    private val mList = mutableListOf<DetailTangkapan>()

    inner class ViewHolder(private val itemAdapterCatchResultBinding: ItemAdapterCatchResultBinding) :
        RecyclerView.ViewHolder(itemAdapterCatchResultBinding.root) {

        fun onBindItem(detailTangkapan: DetailTangkapan) {
            itemAdapterCatchResultBinding.matauang.text = detailTangkapan.mata_uang
            itemAdapterCatchResultBinding.namaIkan.text = detailTangkapan.id_ikan
            itemAdapterCatchResultBinding.harga.setCurrency(CurrencySymbols.INDONESIA)
            itemAdapterCatchResultBinding.harga.setDecimals(false)
            itemAdapterCatchResultBinding.harga.setDelimiter(false)

            itemAdapterCatchResultBinding.totalTangkapan.text =
                "${detailTangkapan.total_tangkapan} KG"

            if (detailTangkapan.peruntukan.lowercase() == "pribadi") {
                itemAdapterCatchResultBinding.peruntukan.text = detailTangkapan.peruntukan
                itemAdapterCatchResultBinding.hargaRow.visibility = View.GONE
            } else {
                itemAdapterCatchResultBinding.peruntukan.text = detailTangkapan.peruntukan
                itemAdapterCatchResultBinding.harga.setText(detailTangkapan.harga.toString())
                itemAdapterCatchResultBinding.hargaRow.visibility = View.VISIBLE
            }

            itemAdapterCatchResultBinding.delete.setOnClickListener {
                onDeleteClick(detailTangkapan)
            }

            itemAdapterCatchResultBinding.edit.setOnClickListener {
                onEditClick(detailTangkapan)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(mListDetail: List<DetailTangkapan>, clearIt: Boolean = false) {
        if (clearIt)
            mList.removeAll(mList.toSet())

        mList.addAll(mListDetail)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAdapterCatchResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}