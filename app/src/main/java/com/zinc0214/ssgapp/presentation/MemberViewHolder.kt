package com.zinc0214.ssgapp.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zinc0214.ssgapp.MemberInfo
import com.zinc0214.ssgapp.databinding.ItemMemberParentBinding


class MemberViewHolder(private val binding: ItemMemberParentBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        info: MemberInfo,
        click: (Boolean) -> Unit,
        edit: (String) -> Unit,
        delete: (String) -> Unit
    ) {
        binding.apply {
            memberInfo = info
            detailGroup.visibility = if (info.isChecked) View.VISIBLE else View.GONE

            layout.setOnClickListener {
                click(!info.isChecked)
                detailGroup.visibility = if (info.isChecked) View.VISIBLE else View.GONE
            }

            editClickListener = View.OnClickListener { edit(info.nickname) }
            deleteClickListener = View.OnClickListener { delete(info.nickname) }

            executePendingBindings()
        }
    }
}