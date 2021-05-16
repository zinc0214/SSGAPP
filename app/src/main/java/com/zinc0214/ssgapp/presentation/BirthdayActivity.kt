package com.zinc0214.ssgapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zinc0214.ssgapp.MemberInfo
import com.zinc0214.ssgapp.MemberInfoDTO
import com.zinc0214.ssgapp.R
import com.zinc0214.ssgapp.changeDTO
import com.zinc0214.ssgapp.databinding.ActivityBirthdayBinding

class BirthdayActivity : AppCompatActivity() {

    lateinit var binding: ActivityBirthdayBinding
    private var membersInfo = ArrayList<MemberInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val memberInfoDTO = intent.getSerializableExtra("memberInfo") as ArrayList<MemberInfoDTO>
        membersInfo = changeDTO(memberInfoDTO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_birthday)

        setUpViews()
    }

    private fun setUpViews() {
        val birthDayAdapter = BirthDayAdapter(membersInfo)
        binding.recyclerView.adapter = birthDayAdapter
    }

}