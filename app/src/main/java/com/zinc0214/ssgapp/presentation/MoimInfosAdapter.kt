package com.zinc0214.ssgapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zinc0214.ssgapp.MoimInfo
import com.zinc0214.ssgapp.databinding.ItemMoimInfoBinding


class MoimInfosAdapter(
    private val memberInfoList: ArrayList<MoimInfo>
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
        holder.bind(resultList[position])
    }

    inner class MoimViewHolder(private val binding: ItemMoimInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(info: MoimInfo) {
            binding.moimInfo = info
        }
    }
}



