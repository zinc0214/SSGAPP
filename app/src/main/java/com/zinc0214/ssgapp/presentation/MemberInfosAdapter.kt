package com.zinc0214.ssgapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.zinc0214.ssgapp.MemberInfo
import com.zinc0214.ssgapp.databinding.ItemMemberParentBinding


class MemberInfosAdapter(
    private val memberInfoList: ArrayList<MemberInfo>,
    private val optionClickInterface: ItemOptionClickInterface
) :
    RecyclerView.Adapter<MemberViewHolder>(), Filterable {

    private var resultList: ArrayList<MemberInfo> = memberInfoList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        return MemberViewHolder(
            ItemMemberParentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = resultList.size

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(resultList[position],
            { resultList[position].isChecked = it },
            { optionClickInterface.edit(it) },
            { optionClickInterface.delete(it) }
        )
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                resultList = if (charString.isEmpty()) {
                    memberInfoList
                } else {
                    val filteredList = memberInfoList.filter { it.nickname.contains(charString) }
                    filteredList as ArrayList<MemberInfo>
                }
                val filterResults = FilterResults()
                filterResults.values = resultList

                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                resultList = filterResults.values as ArrayList<MemberInfo>
                notifyDataSetChanged()
            }
        }
    }

    fun editMember(editInfo: MemberInfo) {
        resultList.filter { it.nickname == editInfo.nickname }.apply {
            this[0].apply {
                addr = editInfo.addr
                age = editInfo.age
                realName = editInfo.realName
            }
        }
        notifyDataSetChanged()
    }

    fun deleteMember(nickname: String) {
        resultList.filter { it.nickname == nickname }.apply {
            resultList.removeAll(this)
        }
        notifyDataSetChanged()
    }
}


interface ItemOptionClickInterface {
    fun edit(string: String)
    fun delete(string: String)
}


