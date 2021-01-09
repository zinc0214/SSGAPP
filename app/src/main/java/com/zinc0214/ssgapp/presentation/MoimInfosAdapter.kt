package com.zinc0214.ssgapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zinc0214.ssgapp.MoimInfo
import com.zinc0214.ssgapp.databinding.ItemMoimInfoBinding


class MoimInfosAdapter(
    private val memberInfoList: ArrayList<MoimInfo>,
    private val optionClickInterface: MoimItemOptionClickInterface
) : RecyclerView.Adapter<MoimInfosAdapter.MoimViewHolder>() {

    private var resultList: ArrayList<MoimInfo> = memberInfoList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoimViewHolder {
        return MoimViewHolder(
            ItemMoimInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = resultList.size

    override fun onBindViewHolder(holder: MoimViewHolder, position: Int) {
        val info = resultList[position]
        holder.bind(info, {
            optionClickInterface.edit(info)
        }, {
            optionClickInterface.delete(info)
        })

    }

    fun deleteMoim(id: String) {
        resultList.filter { it.id == id }.apply {
            resultList.removeAll(this)
        }
        notifyDataSetChanged()
    }

    inner class MoimViewHolder(
        private val binding: ItemMoimInfoBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            info: MoimInfo,
            edit: (MoimInfo) -> Unit,
            delete: (MoimInfo) -> Unit
        ) {
            binding.moimInfo = info
            binding.editClickListener = View.OnClickListener { edit(info) }
            binding.deleteClickListener = View.OnClickListener { delete(info) }
        }
    }
}


interface MoimItemOptionClickInterface {
    fun edit(info: MoimInfo)
    fun delete(info: MoimInfo)
}
