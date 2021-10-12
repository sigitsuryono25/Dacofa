package com.surelabsid.lti.dacofa.ui.lihatdata

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.lti.dacofa.database.DetailTangkapan
import com.surelabsid.lti.dacofa.databinding.ItemAdapterCatchResultBinding


class AdapterCatchResult(
    private val onEditClick: (DetailTangkapan) -> Unit,
    private val onDeleteClick: (DetailTangkapan) -> Unit
) : RecyclerView.Adapter<AdapterCatchResult.ViewHolder>() {

    private val mList = mutableListOf<DetailTangkapan>()

    inner class ViewHolder(private val itemAdapterCatchResultBinding: ItemAdapterCatchResultBinding) :
        RecyclerView.ViewHolder(itemAdapterCatchResultBinding.root) {

        fun onBindItem(detailTangkapan: DetailTangkapan) {
            itemAdapterCatchResultBinding.matauang.text = detailTangkapan.mataUang
            itemAdapterCatchResultBinding.namaIkan.text = detailTangkapan.idIkan
            itemAdapterCatchResultBinding.totalTangkapan.text =
                "${detailTangkapan.totalTangkapan} KG"
            itemAdapterCatchResultBinding.harga.text = "${detailTangkapan.harga}/KG"

            itemAdapterCatchResultBinding.delete.setOnClickListener {
                onDeleteClick(detailTangkapan)
            }

            itemAdapterCatchResultBinding.edit.setOnClickListener {
                onEditClick(detailTangkapan)
            }
        }
    }

    fun addItem(mListDetail: List<DetailTangkapan>, clearIt: Boolean = false) {
        if (clearIt)
            mList.removeAll(mList)

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