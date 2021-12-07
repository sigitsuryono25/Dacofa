package com.surelabsid.lti.dacofa.ui.lihatdata.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.lti.dacofa.database.DetailTangkapan
import com.surelabsid.lti.dacofa.databinding.ItemAdapterDetailTangakanValidBinding
import com.surelabsid.lti.dacofa.response.DataTangkapanItem
import me.abhinay.input.CurrencySymbols

class AdapterDetailTangkapanValid : RecyclerView.Adapter<AdapterDetailTangkapanValid.ViewHolder>() {

    private val mListData = mutableListOf<DataTangkapanItem?>()

    class ViewHolder(private val itemAdapterDetailTangakanValidBinding: ItemAdapterDetailTangakanValidBinding) :
        RecyclerView.ViewHolder(itemAdapterDetailTangakanValidBinding.root){

            fun onBindItem(dataTangkapanItem: DataTangkapanItem?){
                itemAdapterDetailTangakanValidBinding.harga.setCurrency(CurrencySymbols.INDONESIA)
                itemAdapterDetailTangakanValidBinding.harga.setDecimals(false)
                itemAdapterDetailTangakanValidBinding.harga.setDelimiter(false)

                itemAdapterDetailTangakanValidBinding.namaIkan.text = dataTangkapanItem?.idIkan
                itemAdapterDetailTangakanValidBinding.totalTangkapan.text = dataTangkapanItem?.totalTangkapan
                itemAdapterDetailTangakanValidBinding.peruntukan.text = dataTangkapanItem?.peruntukan
                itemAdapterDetailTangakanValidBinding.matauang.text = dataTangkapanItem?.mataUang

                if (dataTangkapanItem?.peruntukan?.lowercase() == "pribadi") {
                    itemAdapterDetailTangakanValidBinding.peruntukan.text = dataTangkapanItem.peruntukan
                    itemAdapterDetailTangakanValidBinding.hargaRow.visibility = View.GONE
                } else {
                    itemAdapterDetailTangakanValidBinding.peruntukan.text = dataTangkapanItem?.peruntukan
                    itemAdapterDetailTangakanValidBinding.harga.setText(dataTangkapanItem?.harga.toString())
                    itemAdapterDetailTangakanValidBinding.hargaRow.visibility = View.VISIBLE
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAdapterDetailTangakanValidBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun addItem(list: List<DataTangkapanItem?>, clearAll: Boolean = false){
        if(clearAll)
            mListData.removeAll(mListData)


        mListData.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(mListData[position])
    }

    override fun getItemCount(): Int {
        return mListData.size
    }

}