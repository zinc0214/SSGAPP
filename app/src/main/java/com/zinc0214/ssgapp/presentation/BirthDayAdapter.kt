package com.zinc0214.ssgapp.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zinc0214.ssgapp.MemberInfo
import com.zinc0214.ssgapp.databinding.ItemBirthdayBinding

class BirthDayAdapter(private val memberInfoList: ArrayList<MemberInfo>) :
    RecyclerView.Adapter<BirthDayAdapter.BirthDayViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirthDayViewHolder {
        return BirthDayViewHolder(
            ItemBirthdayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BirthDayViewHolder, position: Int) {
        holder.bind(position + 1)
    }

    override fun getItemCount(): Int {
        return 12
    }

    inner class BirthDayViewHolder(
        private val binding: ItemBirthdayBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(month: Int) {
            binding.birthDayMemberTextview.setTextIsSelectable(true)

            Log.e("ayhan ", "${month}")

            val monthMember = memberInfoList.filter {
                it.getBirthDayMonth() == month.toStringMonth()
            } as ArrayList

            monthMember.sortBy { it.birthDay }

            binding.month = "${month}월"
            binding.member = birthDayMemberText(monthMember)

        }
    }

}

fun Int.toStringMonth(): String {
    return if (this < 10) {
        "0$this"
    } else "$this"
}


fun birthDayMemberText(list: ArrayList<MemberInfo>): String {
    var text = ""
    list.forEach {
        text += "${it.nickname} (${it.birthDay})\n"
    }
    return text
}
